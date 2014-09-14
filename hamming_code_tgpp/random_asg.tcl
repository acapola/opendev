package provide nimpRandomAsg 1.0
namespace eval ::nimp::random_asg {
    #Pseudo Random Number Generator using the "alternative step generator" structure
    #See Algorithm 6.57 of Handbook of applied Crypto by Menezes, Oorschot and Vandstone
    
    # Define a set of commands that will be used to create an
    # ensemble for random.
    variable randomDef {
        # variables maintained in each counter are grouped inside a single dict to ease maintainance
        variable context [dict create seed 0] 
	::nimp::random_asg::reset_cmd context
        # Commands are taken from counter namespace
        namespace ensemble create -map [list \
        setSeed "::nimp::random_asg::setSeed_cmd  [namespace current]::context" \
        reset   "::nimp::random_asg::reset_cmd    [namespace current]::context" \
        step    "::nimp::random_asg::step_cmd     [namespace current]::context" \
        getBits "::nimp::random_asg::getBits_cmd  [namespace current]::context"]
    }
    # Make the createRandom procedure an ensemble command
    namespace ensemble create -map {
        create "::nimp::random_asg::createRandom"
    }
    proc createRandom { {name ""} } {
        variable randomDef
	set uiid "nimp_random_asg_[incr ::nimp::random_asg::instanceCnt]_$name"
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
		dict set context stateR1 [expr $seed^0xB8A213CD]
		dict set context stateR2 [expr $seed^0xDA123456]
		dict set context stateR3 [expr $seed^0xECE6B896]
			
		#dict set context stateR1 [expr 0x7]
		#dict set context stateR2 [expr 0x6]
		#dict set context stateR3 [expr 0x6]
		
		return $seed
    }
    proc step_cmd {name} {
        upvar 1 $name context
        dictToVars [dict get $context]
		set stateR1 [lfsrStep $stateR1 $::nimp::random_asg::tapsMask1]
		#puts "step R1: [format "%016X" [dict get $context stateR1]] [format "%016X" $stateR1]"
			
		dict set context stateR1 $stateR1
		if {$stateR1 & 1} {
			set stateR2 [lfsrStep $stateR2 $::nimp::random_asg::tapsMask2]
			#puts "step R2: [format "%016X" [dict get $context stateR2]] [format "%016X" $stateR2]"
			dict set context stateR2 $stateR2
		} else {
			set stateR3 [lfsrStep $stateR3 $::nimp::random_asg::tapsMask3]
			#puts "step R3: [format "%016X" [dict get $context stateR3]] [format "%016X" $stateR3]"
			dict set context stateR3 $stateR3
		}
		set out [expr ($stateR2 & 1) ^ ($stateR3 & 1)]
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

    proc testLfsr {} {
		set tapsMask1 [::nimp::random_asg::lfsrExponentsToMask 5 4 3 1 0]
		set stateR1 0x6
		set out ""
		for {set i 0} {$i<31} {incr i} {
			set stateR1 [::nimp::random_asg::lfsrStep $stateR1 $tapsMask1]
			append out [expr $stateR1 & 1]
		}
		puts $out
    }
    proc testRng {} {
		set rng [::nimp::random_asg create]
		set out ""
		for {set i 0} {$i<600} {incr i} {
			append out [format "%01X" [$rng step]]
		}
		puts $out
		for {set i 0} {$i<100} {incr i} {
			puts [format "%08X%08X" [$rng getBits 32] [$rng getBits 32]]
		}
    }
    #static variables (common to all ::nimp::random objects)
    variable instanceCnt 0
    #final static variables 
    variable tapsMask1 [lfsrExponentsToMask 64  4  3 1 0]
    variable tapsMask2 [lfsrExponentsToMask 61 16 15 1 0]
    variable tapsMask3 [lfsrExponentsToMask 63       1 0]
	if {0} {
		variable tapsMask1 [lfsrExponentsToMask 3 2 0]
		variable tapsMask2 [lfsrExponentsToMask 4 3 0]
		variable tapsMask3 [lfsrExponentsToMask 5 4 3 1 0]
	}
}

::nimp::random_asg::testRng

