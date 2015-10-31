
namespace path {::tcl::mathop ::tcl::mathfunc}
proc gcd {p q} {
    while {$q != 0} {
        lassign [list $q [% $p $q]] p q
    }
    abs $p
}

proc lcm_base {a b} {
	return [expr $b*($a/[gcd $a $b])]
}

proc lcm { args } {
	set out [lindex $args 0]
	foreach n [lrange $args 1 end] {
		set out [lcm_base $out $n]
	}
	return $out
}

proc lfsrMaxLengths { maxBits } {
	set out [list]
	for { set i 1 } {$i<$maxBits} {incr i} {
		lappend out [expr (1<<$i) - 1]
	}	
	return $out
}

set periods [lfsrMaxLengths 8]

for {set i 0} {$i<8


