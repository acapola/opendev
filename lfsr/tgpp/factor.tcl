

proc factor { num } {
    set res [exec yafu factor($num)]
    set res [string range $res [string first "\nP" $res] end]
    set res [string range $res 0 [string first "\nans" $res]]
    #puts $res
    set lines [split $res "\n"]
    set factors [dict create]
    foreach line $lines {
        set tokens [split $line "="]
        set factor [string trim [lindex $tokens 1]]
        if {[string length $factor]} {
            #puts $factor
            if {[dict exists $factors $factor]} {
                dict incr factors $factor
            } else {
                dict set factors $factor 1
            }
        }
    }
    return $factors
}

#TODO: optimmize
proc pow {base exp} {
    set out $base
    for {set i 1} {$i<$exp} {incr i} {
        set out [expr $out * $base]
    }
    return $out
}

proc test {} {
    for {set i 192} {$i < 200} {incr i} {
        set n [expr [pow 2 $i] -1]
        puts "$i -> $n"
        set factors [factor $n]
        dict for {k v} $factors {
            puts "$k ^ $v"
        }
        flush stdout
    }
}
#test