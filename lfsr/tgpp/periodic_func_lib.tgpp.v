``
tgpp::source periodic_func.tgpp.v
for {set i 3} {$i<256} {incr i} {
	set spec [dict create period $i]
	set func_spec [periodic_func_defs::get_func_spec $spec]
``
`periodic_func $func_spec`
``
}

