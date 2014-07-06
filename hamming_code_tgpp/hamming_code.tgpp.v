``
#this file is not meant to be used directly, the entry point is hamming_code.tgpp

proc hammingCodeVerilogFunction { inputWidth {extended 0} {userOutputWidth 0} } {
    set hammingCodeDict [hammingCode $inputWidth $extended $userOutputWidth]
	#do a bit of self checking
	checkHammingCode $hammingCodeDict
	return [hammingCodeVerilogFunction2 $hammingCodeDict]
}

proc hammingCodeVerilogFunction2 { hammingCodeDict } {
	dictToVars $hammingCodeDict
``
`hammingCodeCommentsC $hammingCodeDict`
function [`$r`-1:0] `$funcName`;
    input [`$k`-1:0] in;
    reg [`$r`-1:0] syndrom;
    begin
``for {set syndromBit 0} {$syndromBit<[llength [dict keys $bits]]} {incr syndromBit} {
    set inBits [list]
    foreach b [dict get $bits $syndromBit] {
        lappend inBits in\[[format %${indexesWidth}s $b]\]
        dict lappend loads $b $syndromBit
    }
``
        syndrom[`format %${syndromIndexesWidth}s $syndromBit`] = `listToString $inBits "^"`;//`dict get $bitsInputs $syndromBit` inputs
``}``   
        `$funcName` = syndrom;
    end
endfunction
``
    #return all except the last new line character
    return [string range [::tgpp::getProcOutput] 0 end-1]
} 
#end of hammingCodeFunction
``