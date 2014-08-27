``
#this file is not meant to be used directly, the entry point is hamming_code.tgpp

proc hammingCodeTclProc { inputWidth {extended 0} {userOutputWidth 0} } {
	set ecc 0
	set extendedArg [string tolower $extended]
	switch $extendedArg {
		0 { set extended 0 }
		1 -
		"extended" { set extended 1 }
		"ecc" {
			set extended 1
			set ecc 1
		}
		default {
			puts "unsupported value for extended parameter: $extendedArg"
			exit -1
		}
	}
	puts "ecc=$ecc"
    set hammingCodeDict [hammingCode $inputWidth $extended $userOutputWidth]
	if {$ecc} {
		#compute the corrector part
		hammingCodeCorrectionPattern hammingCodeDict
	}
	return [hammingCodeTclProc2 $hammingCodeDict $ecc]
}

proc hammingCodeTclProc2 { hammingCodeDict {ecc 0} } {
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
``if {$ecc} {``
proc `$eccFuncName` { syndromBitList } {
    set out [dict create]
	dict set out correctable_error 1
	dict set out uncorrectable_error 0
	dict set out correction_pattern `string map {" " ""} [valToBits 0 $k]`
	set syndrom [string map {" " ""} $syndromBitList]
    switch $syndrom {
		`string map {" " ""} [valToBits 0 $r]` { dict set out correctable_error 0 }
``for {set inputBit 0} {$inputBit<[llength [dict keys $eccPatterns]]} {incr inputBit} {``
		`dict get $eccPatterns $inputBit` { dict set out correction_pattern `string map {" " ""} [valToBits [expr 1<<$inputBit] $k]` }
``}``   
		default {
			dict set out correctable_error 0
			dict set out uncorrectable_error 1
		}
	}
	return $out
}
``}
    #return all except the last new line character
    return [string range [::tgpp::getProcOutput] 0 end-1]
} 
#end of hammingCodeFunction


proc hammingCodeTclProcNumbers { hammingCodeDict {ecc 0} } {
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
``
    set syndrom [expr $syndrom | ((`listToString $inBits "^"`)<<`$syndromBit`)]
``}``
	return $syndrom
}  
``
if {$ecc} {``
proc `$eccFuncName` { syndrom } {
    set out [dict create]
	dict set out correctable_error 1
	dict set out uncorrectable_error 0
	dict set out correction_pattern 0
	switch $syndrom {
		0 { dict set out correctable_error 0 }
``set max [llength [dict keys $eccPatterns]]
for {set inputBit 0} {$inputBit<$max} {incr inputBit} {``
		`bitsStrToVal [dict get $eccPatterns $inputBit]` { dict set out correction_pattern `expr 1<<$inputBit` }
``}``   
		default {
			dict set out correctable_error 0
			dict set out uncorrectable_error 1
		}
	}
	return $out
}
``}
    #return all except the last new line character
    return [string range [::tgpp::getProcOutput] 0 end-1]
} 
#end of hammingCodeTclProcNumbers
``