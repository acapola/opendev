#!/usr/bin/tclsh

proc lFilterEmpty {alist} {
    set out [list]
    foreach arg $alist {
        if {""!=$arg} { lappend out $arg }    
    }
    return $out
}

set nlfsrDb [dict create]

set f [open NlfsrDb4.csv r]

while {[gets $f line] > -1} { #for each line
    set items [split $line ,]
    set len [lindex $items 1]
    set taps [lFilterEmpty [lrange $items 2 end]]
    puts [llength $taps]
    set nTaps [expr [llength $taps] / 2]
    for {set i 0} {$i<$nTaps} {incr i} {
        dict set nlfsrDb $len $i taps [lindex $taps [expr 2*$i]]
        dict set nlfsrDb $len $i state [lindex $taps [expr 2*$i+1]]  
    }
}
close $f

dict for {len taps} $nlfsrDb {
    puts "$len: [dict size $taps] taps"
    puts $taps
}
