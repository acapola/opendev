``
#this file is not meant to be used directly, the entry point is hamming_code.tgpp

proc hammingCodeTclProc { inputWidth {extended 0} {userOutputWidth 0} } {
    set hammingCodeDict [hammingCode $inputWidth $extended $userOutputWidth]
	return [hammingCodeTclProc2 $hammingCodeDict]
}

proc hammingCodeTclProc2 { hammingCodeDict } {
	dictToVars $hammingCodeDict
``
proc `$funcName` { bitList } {
	set syndrom [list]
``for {set syndromBit 0} {$syndromBit<[llength [dict keys $bits]]} {incr syndromBit} {
    set inBits [list]
    foreach b [dict get $bits $syndromBit] {
        lappend inBits "\[lindex \$bitList [format %${indexesWidth}s $b]\]"
    }
``
    lappend syndrom [expr `listToString $inBits "^"`]
``}``
	return $syndrom
}  
``
    #return all except the last new line character
    return [string range [::tgpp::getProcOutput] 0 end-1]
} 
#end of hammingCodeFunction


proc hammingCodeTclProcNumbers { hammingCodeDict } {
	
    if {0} {
    set funcName [dict get $hammingCodeDict parameters funcName]
    set r [dict get $hammingCodeDict parameters r]
	set k [dict get $hammingCodeDict parameters k]
	set nValidWords [dict get $hammingCodeDict parameters nValidWords]
	set nTotalWords [dict get $hammingCodeDict parameters nTotalWords]
	
	set codeDescription [dict get $hammingCodeDict codeDescription]
	set syndromIndexesWidth [dict get $hammingCodeDict syndromIndexesWidth]
	set indexesWidth [dict get $hammingCodeDict indexesWidth]
	set dots [dict get $hammingCodeDict dots]
	set loads [dict get $hammingCodeDict loads]
	set bitsInputs [dict get $hammingCodeDict bitsInputs]
	set bits [dict get $hammingCodeDict bits]
	}
	dictToVars $hammingCodeDict
	
``
proc `$funcName` { vector } {
	set syndrom 0
``for {set syndromBit 0} {$syndromBit<[llength [dict keys $bits]]} {incr syndromBit} {
    set bitMask [onesToVal [dict get $bits $syndromBit]]
    set inBits [list]
    foreach b [dict get $bits $syndromBit] {
        lappend inBits "((\$vector >> [format %${indexesWidth}s $b]) & 1)"
    }
	#set syndrom [expr $syndrom |(($vector ^ `$bitMask`)<<`$syndromBit`)]
``
    set syndrom [expr $syndrom | ((`listToString $inBits "^"`)<<`$syndromBit`)]
``}``
	return $syndrom
}  
``
    #return all except the last new line character
    return [string range [::tgpp::getProcOutput] 0 end-1]
} 
#end of hammingCodeTclProcNumbers
``