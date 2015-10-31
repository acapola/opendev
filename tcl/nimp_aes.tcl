source aes.tcl
source nimp.tcl

package require aes


namespace eval nimp_aes {
    set version 0.1
}

::nimp::proc+ ::nimp::aes::hexstr_to_bin { hexstr } {
	Convert an hexadecimal string like "12F4" to binary
} {
	binary format H* $hexstr
} test {
	::nimp::assert_equal "efg" [hexstr_to_bin "656667"]
}

proc ::nimp::aes::ecb_enc { key data } {
	set key [hexstr_to_bin $key]
	set data [hexstr_to_bin $data]
	string toupper [aes::aes -hex -mode ecb -dir encrypt -key $key $data]
}

proc ::nimp::aes::ecb_dec { key data } {
	set key [hexstr_to_bin $key]
	set data [hexstr_to_bin $data]
	string toupper [aes::aes -hex -mode ecb -dir decrypt -key $key $data]
}

proc ::nimp::aes::cbc_enc { key data } {
	set key [hexstr_to_bin $key]
	set data [hexstr_to_bin $data]
	string toupper [aes::aes -hex -mode cbc -dir encrypt -key $key $data]
}

proc ::nimp::aes::cbc_dec { key data } {
	set key [hexstr_to_bin $key]
	set data [hexstr_to_bin $data]
	string toupper [aes::aes -hex -mode cbc -dir decrypt -key $key $data]
}


package provide ::nimp::aes $::nimp::aes::version


puts [::nimp::aes::ecb_enc 00000000000000000000000000000000 00000000000000000000000000000000]
puts [::nimp::aes::ecb_dec 00000000000000000000000000000000 00000000000000000000000000000000]


proc show_help_and_test { package_name } {
	foreach help [info procs "${package_name}::*_proc_help"] {
		set p [string range $help 0 end-[string length "_proc_help"]]
		puts "$p [info args $p]"
		puts [$help]
		set ptest "${p}_proc_test"
		if { [string length [info procs $ptest]] > 0 } {
			puts "run ${ptest}"    
			$ptest
		}
	}
}

show_help_and_test ::nimp::aes
