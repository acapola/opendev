
source [file join [file dirname [info script]] random.tcl]
package require nimpRandom
##############################################################
# D E B U G
##############################################################

#comment the body of this proc to disable all debug output
proc dbgPutsPlain { msg } {
#    puts $msg
}
proc dbgStr { varName {level 1}} {
    upvar $level $varName var
    return "$varName=$var"
}
proc dbgPuts { msg {relLevel 0} {level -1}} {
    if {$level==-1} {set level [expr [info level]-1]}
    set level [expr $level + $relLevel]
    dbgPutsPlain [string repeat "   " $level]$msg
}
proc dbgPutsVar { varName {relLevel 0} } {
    dbgPuts [dbgStr $varName 2] $relLevel [expr [info level]-1]
}
proc putsVar { varName {relLevel 0} } {
    puts [dbgStr $varName 2]
}
#assume keys are integer
proc printIntDict { inDict {formatting "%2d"} args } {
	set out ""
	#set inDict [sortDictByValue $inDict]
	dict for {k v} $inDict {
		if {[llength $args]} {set v [dict get $v {*}$args]}
		append out [format "$formatting ->" $k]
		foreach e $v {
			append out " [format $formatting $e]"
		}
		append out "\n"
	}
	puts [string range $out 0 end-1]
}

proc assertEqual { a b } {
    if {$a!=$b} {
        puts "INTERNAL ERROR: equal assertion failed."
        puts "$a"
        puts "$b"
        exit -1
    }
}

##############################################################
# L I S T
##############################################################
proc filter {list script} {
   set res {}
   foreach e $list {if {[uplevel 1 $script [list $e]]} {lappend res $e}}
   set res
}
proc lremove {listVariable value} {
    upvar 1 $listVariable var
    set idx [lsearch -exact $var $value]
    set var [lreplace $var $idx $idx]
}

proc lcontains {listValue value} {
	return [expr [lsearch -exact $listValue $value]!=-1]
}
proc lshuffle {listVariable rngVariable} {
	upvar 1 $listVariable list
    upvar 1 $rngVariable rng
    set len [llength $list]
	#::nimp::random create rng
	#rng setSeed $seed
    while {$len} {
        set n [expr [$rng getBits [binWidth $len]] % $len]
        set tmp [lindex $list $n]
        lset list $n [lindex $list [incr len -1]]
        lset list $len $tmp
    }
}
#return 1 if the list contains at least one duplicated value
proc lHasDuplicates { inputList } {
	if {[llength $inputList]<2} {return 0}
	set sortedList [lsort $inputList]
	set last [lindex $sortedList 0]
	for {set i 1} {$i<[llength $sortedList]} {incr i} {
		set tmp [lindex $sortedList $i]
		if {$last == $tmp} {return 1}
		set last $tmp
	}
	return 0
}
proc listToString {aList {separator ""} } {
    set out ""
    if {[llength $aList]} {
        for {set i 0} {$i<[llength $aList]-1} {incr i} {
            append out "[lindex $aList $i]$separator"
        }
        append out [lindex $aList end]
    }
    return $out
}

##############################################################
# D I C T
##############################################################

#return 1 if the dict contains at least one duplicated value
proc dictHasDuplicates { inputDict } {
	set dictSize [dict size $inputDict]
	if { $dictSize <2 } { return 0 }
	set sorted [ sortDictByValue $inputDict ]
	set last [lindex $sorted 0]
	set keys [dict keys $sorted]
	for {set i 1} {$i<[llength $keys]} {incr i} {
		set key [lindex $keys $i]
		set tmp [dict get $sorted $key]
		if {$last == $tmp} {return 1}
		set last $tmp
	}
	return 0
}


proc sortDictByValue {dict args} {
    set lst {}
    dict for {k v} $dict {lappend lst [list $k $v]}
    return [concat {*}[lsort -index 1 {*}$args $lst]]
}
proc dict_sort {dict args} {
    set res [dict create]
    foreach key [lsort {*}$args [dict keys $dict]] {
        dict set res $key [dict get $dict $key] 
    }
    return $res
}

proc dictMinMax { inDict } {
	set size [dict size $inDict]
	set minLoad [dict get $inDict 0]
	set maxLoad $minLoad
	set minLoadBits [list 0]
	set maxLoadBits [list 0]
	for {set syndromBit 1} {$syndromBit<$size} {incr syndromBit} {
		set load [dict get $inDict $syndromBit]
		if {$load<$minLoad} {
			set minLoad $load
			set minLoadBits [list $syndromBit]
		} elseif {$load>$maxLoad} {
			set maxLoad $load
			set maxLoadBits [list $syndromBit]
		} else {
			if {$load==$minLoad} {lappend minLoadBits $syndromBit}
			if {$load==$maxLoad} {lappend maxLoadBits $syndromBit}
		}
	}
	set out [dict create minValue $minLoad minIndexes $minLoadBits maxValue $maxLoad maxIndexes $maxLoadBits]
	return $out
}

proc dictToVars { varDefs {levelOut 1} } {
    dict for {varName value} $varDefs {
        #create a tcl variable in the caller for each key-value pair
        upvar $levelOut $varName var
        set var $value
    }
}

proc varsToDict { args } {
	set out [dict create]
	foreach arg $args {
		upvar $arg argVal
		dict set out $arg $argVal
	}
	return $out
}

proc dictTransposeIndexes { inDict } {
	set out [dict create]
	foreach key [dict keys $inDict] {
		foreach val [dict get $inDict $key] {
			dict lappend out $val $key
		}
	}
	return $out
}

##############################################################
# M A T H
##############################################################

proc log2 {a} {expr log($a)/log(2)}
proc log10 {a} {expr log($a)/log(10)}

proc divideRoundUp { in divider } {
	set out [expr int(($in + $divider -1)/$divider)]
	return $out
}

proc divideRoundDown { in divider } {
	set out [expr int($in/$divider)]
	return $out
}

#return number of bits to encode a in binary. a must be >=0.
proc binWidth {a} {
    if {$a} {
        return [expr int(floor([log2 $a])) + 1]
    } else {
        return 1
    }
}

#return number of digit to write a in decimal. a must be >=0.
proc decWidth {a} {
    if {$a} {
        return [expr int(floor([log10 $a])) + 1]
    } else {
        return 1
    }
}

proc random { bitWidth } {
	set out 0
	for {set i 0} {$i<$bitWidth} {incr i} {
		set out [expr $out | (int(rand()*2)<<$i)]
	}
	return $out
}
#return the list of bits which are set in input
#5 --> {0 2}
proc bitsSet { a } {
    set len [binWidth $a]
    set out [list]
    for {set i 0} {$i<$len} {incr i} {
        if {$a & 1} {
            lappend out $i
        }
        set a [expr $a>>1]
    }
    return $out
}

proc valToBits { val width} {
    set out [list]
    for {set i 0} {$i<$width} {incr i} {
        if {$val & (1<<$i)} {
            lappend out "1"
        } else {
			lappend out "0"
		}
    }
    return $out
}
#return the integer value which is coded by the input bit list
proc bitsToVal { bitsList } {
    set out 0
    for {set i 0} {$i<[llength $bitsList]} {incr i} {
		set bit [lindex $bitsList $i]
		if {$bit} {
			set out [expr $out + (1<<$i)]
		}
    }
    return $out
}
#return the integer value which is coded by the input bit list
proc bitsStrToVal { bitsStr } {
    set out 0
    for {set i 0} {$i<[string length $bitsStr]} {incr i} {
		set bit [string index $bitsStr $i]
		if {$bit} {
			set out [expr $out + (1<<$i)]
		}
    }
    return $out
}
#return a list of bit index set to 1 in the binary representation of val
proc valToOnes { val } {
    set out [list]
    set width [binWidth $val]
    for {set i 0} {$i<$width} {incr i} {
        if {$val & (1<<$i)} {
            lappend out $i
        }
    }
    return $out
}
#return the integer value which contain a 1 at each bit position specified in ones
proc onesToVal { ones } {
    set out 0
    foreach bitIndex $ones {
        set out [expr $out + (1<<$bitIndex)]
    }
    return $out
}


proc hammingWeight { a } {
    set len [binWidth $a]
    set out 0
    for {set i 0} {$i<$len} {incr i} {
        set out [expr $out + ($a & 1)]
        set a [expr $a>>1]
    }
    return $out
}
proc hammingDistance { a b } {
	set delta [expr $a ^ $b]
	return [hammingWeight $delta]
}

