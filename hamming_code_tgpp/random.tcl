package provide nimpRandom 1.0
namespace eval ::nimp::random {
    #Pseudo Random Number Generator using the "alternative step generator" structure
    #See Algorithm 6.57 of Handbook of applied Crypto by Menezes, Oorschot and Vandstone
    
    # Define a set of commands that will be used to create an
    # ensemble for random.
    variable randomDef {
        # variables maintained in each counter are grouped inside a single dict to ease maintainance
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
		dict set context stateR1 [expr $seed+0xB8A213CD7]
		dict set context stateR2 [expr $seed+0xDA1234567]
		dict set context stateR3 [expr $seed+0xECE6B8962]
		return $seed
    }
    proc step_cmd {name} {
        upvar 1 $name context
        dictToVars [dict get $context]
	set stateR1 [lfsrStep $stateR1 $::nimp::random::tapsMask1]
	if {$stateR1 & 1} {
	    set stateR2 [lfsrStep $stateR2 $::nimp::random::tapsMask1]
	    set out [expr $stateR2 & 1]
	    dict set context stateR2 $stateR2
	} else {
	    set stateR3 [lfsrStep $stateR3 $::nimp::random::tapsMask1]
	    set out [expr $stateR3 & 1]
	    dict set context stateR3 $stateR3
	}
	dict set context stateR1 $stateR1
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
	for {set i 0} {$i<100} {incr i} {
		puts [format "%08X%08X" [$rng getBits 32] [$rng getBits 32]]
	}
    }
    #static variables (common to all ::nimp::random objects)
    variable instanceCnt 0
    #final static variables 
    variable tapsMask1 [lfsrExponentsToMask 64  4  3 1 0]
    variable tapsMask2 [lfsrExponentsToMask 61 16 15 1 0]
    variable tapsMask3 [lfsrExponentsToMask 62 57 56 1 0]
}



