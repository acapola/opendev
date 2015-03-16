#generic procs

#return a dict with all keys from $d which:
# - are not present in ref_dict
# - have a different values in ref_dict
proc dict_diff { ref_dict d } {
	set out [dict create]
	foreach key [dict keys $d] {
		if {[dict exists $ref_dict $key]} {#key present in ref
			set v [dict get $d $key]
			if {$v!=[dict get $ref_dict $key]} {#value is different, add to output
				dict set out $key [dict get $d $key]
			}
		} else {#key not present in ref, add to output
			dict set out $key [dict get $d $key]
		}
	}
	return $out
}

#domain specific procs

#unit is always 1 bit

set default_props [dict create]
#mandatory props
dict set default_props name "" 
dict set default_props width 0
#optional
dict set default_props offset ""
dict set default_props bitfields [dict create]
dict set default_props display_unit_width 1 	#for display of offsets in bitfields. 8->bytes, 32->DWORDS...
#optional, ignored if bitfields is not empty
dict set default_props not_implemented 0
dict set default_props read_only 0
dict set default_props write_only 0
#used if write_only=1
dict set default_props cst_read_value 0 	
#"" means no reset	
dict set default_props reset_value 0 			
dict set default_props set_by_hardware 0
dict set default_props cleared_by_hardware 0
dict set default_props read_sensitive 0
dict set default_props write_sensitive 0
#automatically maintained props
dict set default_props bitfields_width 0

set typical_read_only [dict create read_only 1 set_by_hardware 1 cleared_by_hardware 1]
set typical_read_sensitive [dict create read_sensitive 1 set_by_hardware 1 cleared_by_hardware 1]
set typical_sensitive_read_only [dict create read_only 1 read_sensitive 1 set_by_hardware 1 cleared_by_hardware 1]

proc is_container { bitfield } {
	return [expr 0!=[dict size [dict get $bitfield bitfields]]]
}

proc is_updated_by_hardware { bitfield } {
	return [expr 0!=([dict get $bitfield set_by_hardware]|[dict get $bitfield cleared_by_hardware])]
}

proc bitfield_to_string { bitfield {indent_incr "   "} {indent ""} } {
	set out "${indent}"
	set offset [dict get $bitfield offset]
	if { $offset==""} {
		append out "?"
	} else {
		append out "$offset"
	}
	append out " [dict get $bitfield width]"
	append out " [dict get $bitfield name]"
	if {[is_container $bitfield]} {
		dict for {name props} [dict get $bitfield bitfields] {	
			append out "\n"
			append out [bitfield_to_string $props $indent_incr "${indent}$indent_incr"]
		}
	} else {
		dict unset bitfield name
		dict unset bitfield width
		dict unset bitfield offset
		set diff [dict_diff $::default_props $bitfield]
		dict for {prop_name prop_val} $diff {
			append out " \{$prop_name $prop_val\}"
		}
	}
	return $out
}

proc get_next_offset { bitfield } {
	dict with bitfield {
		if {$offset==""} {
			set out $width
		} else {
			set out [expr $width + $offset]
		}
	}
	return $out
}

proc is_sane { bitfield {look_deep 0} } {
	dict with bitfield {
		
	}
	return 1
}

proc assert_sane { props } {
	if {![is_sane $props]} {error "$name is not sane"}
	return $props
}

proc create_bitfield { name {width 1} args } {
	switch [llength $args] {
		0 {	set props $::default_props }
		1 { #args is a dict in brackets
			set dict_props {*}$args
			set props [dict merge $::default_props $dict_props]
		}
		default { #args is an expanded dict
			set props [dict merge $::default_props $args]
		}
	}
	dict set props name $name
	dict set props width $width
	return [assert_sane $props]
	#return $props
}
proc add_bitfields { addr_space_var args } {
	upvar 1 $addr_space_var addr_space
	foreach bitfield $args {
		set offset [dict get $bitfield offset]
		if {$offset==""} {
			set offset [dict get $addr_space width]
			dict set bitfield offset $offset
		}
		dict set addr_space bitfields [dict get $bitfield name] $bitfield
		set bf_next [get_next_offset $bitfield]
		if { $bf_next>[get_next_offset $addr_space] } {
			dict set addr_space width $bf_next
		}
		if { $bf_next>[dict get $addr_space bitfields_width] } {
			dict set addr_space bitfields_width $bf_next
		}
	}
	assert_sane $addr_space
}

proc create_container { name width args } {
	set props $::default_props
	dict set props name $name
	dict set props width 0
	add_bitfields props {*}$args
	dict set props width $width
	return [assert_sane $props]
}


set trng_sfrs [create_bitfield trng 0]
add_bitfields trng_sfrs \
	[create_container CTRL 32 \
		[create_bitfield ENABLE] \
		[create_bitfield READY 1 $::typical_read_only] \
		[create_bitfield PP] \
		[create_bitfield FIFO] \
	] \
	[create_bitfield RNDDAT 32 $::typical_sensitive_read_only] \
	[create_bitfield PPCONF 32] \
	[create_bitfield SRCCONF 32] \
	[create_bitfield SRCINIT 160] \
	[create_bitfield SPYSEL 32]
puts [bitfield_to_string $trng_sfrs]
