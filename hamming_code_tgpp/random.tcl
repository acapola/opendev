package provide nimpRandom 1.0
namespace eval ::nimp::random {
    #Pseudo Random Number Generator using home made structure
    
    # Define a set of commands that will be used to create an
    # ensemble for random.
    variable randomDef {
        # variables maintained in each counter are grouped inside a single dict to ease maintenance
        variable context [dict create seed 0] 
	::nimp::random::reset_cmd context
        # Commands are taken from counter namespace
        namespace ensemble create -map [list \
        setSeed "::nimp::random::setSeed_cmd  [namespace current]::context" \
        reset   "::nimp::random::reset_cmd    [namespace current]::context" \
        step    "::nimp::random::step_cmd     [namespace current]::context" \
        getBits "::nimp::random::getBits_cmd  [namespace current]::context"]
    }
    # Make the createRandom procedure an ensemble command
    namespace ensemble create -map {
        create "::nimp::random::createRandom"
    }
    proc createRandom { {name ""} } {
        variable randomDef
	set uiid "nimp_random_[incr ::nimp::random::instanceCnt]_$name"
        uplevel 1 [list namespace eval $uiid $randomDef]
	return $uiid
    }
    proc setSeed_cmd {name seed} {
        upvar 1 $name context
		if {[string length $seed]==0} {set seed 0}
        dict set context seed $seed
		return [reset_cmd context]
    }
    proc reset_cmd {name} {
        upvar 1 $name context
		set seed [dict get $context seed]
		set stateR1 [expr $::nimp::random::seed1BitMask & ($seed^$::nimp::random::seedMask1)]
		if {0==$stateR1} {set stateR1 $::nimp::random::seedMask1}
		dict set context stateR1 $stateR1
		set stateR2 [expr $::nimp::random::seed1BitMask & ($seed^$::nimp::random::seedMask2)]
		if {0==$stateR2} {set stateR2 $::nimp::random::seedMask2}
		dict set context stateR2 $stateR2
		dict set context buf 0
		dict set context cnt 0
		return $seed
    }
	proc fillBuf {name} {
		upvar 1 $name context
        dictToVars [dict get $context]
		
		set step1 [expr (1 & ($stateR2 >> ($::nimp::random::tapsMask2Width-1))) ^ (1 & ($stateR2 >> ($::nimp::random::tapsMask2Width-2)))]
		set step2 [expr (1 & ($stateR1 >> ($::nimp::random::tapsMask1Width-1))) ^ (1 & ($stateR1 >> ($::nimp::random::tapsMask1Width-2)))]
		
		if {!($step1 || $step2)} {
			set step1 1
			set step2 1
		}
		for {set i 0} {$i<$::nimp::random::stepSize} {incr i} {
			if {$step1} { set stateR1 [lfsrStep $stateR1 $::nimp::random::tapsMask1]}
			if {$step2} { set stateR2 [lfsrStep $stateR2 $::nimp::random::tapsMask2]}
		}
		dict set context stateR1 $stateR1
		dict set context stateR2 $stateR2
		
		set invR2 [reverseBits [expr $stateR2 & $::nimp::random::stepMask] $::nimp::random::stepSize]
		set out [expr $stateR1 ^ $invR2]
		return $out
	}
    proc step_cmd {name} {
        upvar 1 $name context
        dictToVars [dict get $context]
		if {!$cnt} {
			dict set context cnt [expr $::nimp::random::stepSize - 1]
			set buf [fillBuf context]
		} else {
			dict incr context cnt -1
			set buf [dict get $context buf]
		}
		set out [expr ($buf>>($::nimp::random::stepSize-1)) & 1]
		dict set context buf [expr $buf<<1]
		return $out
    }
    proc getBits_cmd {name {width 1} } {
        upvar 1 $name context
        set out 0
	for {set i 0} {$i<$width} {incr i} {	
	    set out [expr ($out << 1) | [step_cmd  context]]
	}
	return $out
    }
    ############# P R I V A T E   P R O C S ###############
    proc lfsrExponentsToMask { args } {
	set out 0
	foreach tap $args {
	    incr tap -1
	    if {$tap>=0} {
		set out [expr $out + (1<<$tap)]
	    }
	}
	return $out
    }
    #Galois LFSR step
    proc lfsrStep {state tapsMask} {
	set out [expr ($state>>1)]
	if {$state & 1} {
	    set out [expr $out ^ $tapsMask]
	}
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
	proc log2 {a} {expr log($a)/log(2)}

	#return number of bits to encode a in binary. a must be >=0.
	proc binWidth {a} {
		if {$a} {
			return [expr int(floor([log2 $a])) + 1]
		} else {
			return 1
		}
	}
	proc reverseBits { bits {width 0} } {
		set out 0
		if {!$width} { set width [binWidth $bits] }
		for {set i 0} {$i<$width} {incr i} {
			set out [expr ($out<<1) | (($bits>>$i) & 1)]
			#puts [format "%X" $out]
		}
		return $out
	}

    proc testLfsr {} {
		set tapsMask1 [::nimp::random::lfsrExponentsToMask 64  4  3 1 0]
		set stateR1 1
		set out ""
		for {set i 0} {$i<150} {incr i} {
			set stateR1 [::nimp::random::lfsrStep $stateR1 $tapsMask1]
			append out [expr $stateR1 & 1]
		}
		puts $out
    }
    proc testRng {} {
		set rng [::nimp::random create]
		for {set seed 0} {$seed<1} {incr seed} {
			$rng setSeed $seed
			$rng getBits 1024
			set out ""
			for {set i 0} {$i<400} {incr i} {
				append out [format "%01X" [$rng step]]
			}
			puts $out
			puts [exec lfsrTool -seqBinStr $out]
			flush stdout
		}
		for {set i 0} {$i<100} {incr i} {
			puts [format "%08X%08X" [$rng getBits 32] [$rng getBits 32]]
		}
    }
    #static variables (common to all ::nimp::random objects)
    variable instanceCnt 0
    #final static variables
	variable taps1 [list 64  4  3 1 0]
	variable taps2 [list 63       1 0]
	variable seedMask1 0xEC6754ABB8A213CD
	variable seedMask2 0x2A1234568304FC34
	variable stepSize 32
	
	#toy size parameters
	#variable taps1 [list 5 4 3 1 0]
	#variable taps2 [list 7 1 0]
	#variable seedMask1 0x7
	#variable seedMask2 0xE
	#variable stepSize 1
	
    variable tapsMask1 [lfsrExponentsToMask {*}$taps1]
    variable tapsMask2 [lfsrExponentsToMask {*}$taps2]
	variable tapsMask1Width [lindex $taps1 0]
    variable tapsMask2Width [lindex $taps2 0]
	variable seed1BitMask [expr (1<<$tapsMask1Width)-1]
	variable seed2BitMask [expr (1<<$tapsMask2Width)-1]
	variable stepMask [expr (1<<$stepSize)-1]
}

#::nimp::random::testRng

