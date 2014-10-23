
#find out the directory of this script
#that allow us to find the input files no matter what is the current directory
set scriptDir [file dirname [info script]]

source [file join $scriptDir .. hamming_code_tgpp misc_utils.tcl]

set testBenchDir [file join $scriptDir songpa.srcs sim_1 new]
set srcDir [file join $scriptDir songpa.srcs sources_1 new]

proc computeKeyWidth { nUnits } {
	set remaining [expr $nUnits-1]
    set kSize 0
	for {set i 0} {$i<[expr $nUnits-1]} {incr i} {
		incr ksize [binWidth $remaining]
		incr remaining -1
	}        
	return $ksize
}

proc setVars { {NUNITS 16} {COMPACT_KEY 1} } {
	set INDEX_WIDTH [binWidth [expr $NUNITS-1]]
	pp set NUNITS $NUNITS
	pp set INDEX_WIDTH $INDEX_WIDTH
	pp set COMPACT_KEY $COMPACT_KEY
	if {$COMPACT_KEY} {
		pp set KEY_WIDTH [computeKeyWidth $NUNITS]
	} else {
		pp set KEY_WIDTH [expr $NUNITS*$INDEX_WIDTH]
	}
}

#create a tgppInterp
tgppInterp create pp

setVars
#test bench stuff
pp setBaseDir $testBenchDir
pp file [file join $testBenchDir keyed_permutation_tb.tgpp.v] ""
#implementation
pp setBaseDir $srcDir
pp str "``source [file join $scriptDir .. hamming_code_tgpp misc_utils.tcl]``"
pp file [file join $srcDir keyed_permutation.tgpp.v] ""
#5 bit permutation  for DES sbox
setVars 32
pp setBaseDir [file join $scriptDir .. .. des Xilinx des des.srcs sources_1 new]
pp file [file join $srcDir keyed_permutation.tgpp.v] ""

	



