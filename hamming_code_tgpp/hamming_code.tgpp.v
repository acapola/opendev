``
#this file is not meant to be used directly, the entry point is hamming_code.tgpp

proc hammingCodeVerilogFunction { inputWidth {extended 0} {userOutputWidth 0} } {
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
    set hammingCodeDict [hammingCode $inputWidth $extended $userOutputWidth]
	if {$ecc} {
		#compute the corrector part
		hammingCodeCorrectionPattern hammingCodeDict
	}
	
	#do a bit of self checking
	checkHammingCode $hammingCodeDict
	return [hammingCodeVerilogFunction2 $hammingCodeDict $ecc]
}

proc hammingCodeVerilogFunction2 { hammingCodeDict {ecc 0} } {
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
``if {$ecc} {``
function [2+`$k`-1:0] `$eccFuncName`;
    input [`$r`-1:0] syndrom;
	input correctEdcSingleBitErrors;
	reg uncorrectable_error;
	reg correctable_error;
	reg [`$k`-1:0] correction_pattern;
	reg edcSingleBitErrorFillValue;
    begin
		edcSingleBitErrorFillValue = correctEdcSingleBitErrors ? 1'b0 : 1'bx;
		correction_pattern = {`$k`{1'bx}};
		correctable_error = 1'b1;
        uncorrectable_error = 1'b0;
		case(syndrom)
			`$r`'b`string map {" " ""} [valToBits 0 $r]`: begin
				correctable_error = 1'b0;
				correction_pattern = {`$k`{1'b0}};
			end	
``for {set inputBit 0} {$inputBit<[llength [dict keys $eccPatterns]]} {incr inputBit} {``
			`$r`'b`string reverse [dict get $eccPatterns $inputBit]`: begin
				correction_pattern = {`$k`{1'b0}};correction_pattern[`format %${indexesWidth}s $inputBit`]=1'b1;
			end
``}``
``for {set inputBit 0} {$inputBit<[llength [dict keys $eccEdcSingleBitErrorPatterns]]} {incr inputBit} {``
			`$r`'b`string reverse [dict get $eccEdcSingleBitErrorPatterns $inputBit]`: begin
				uncorrectable_error = ~correctEdcSingleBitErrors;//if we don't correct it, count it as a detected error
				correction_pattern = {`$k`{edcSingleBitErrorFillValue}};//single bit error on EDC bits -> nothing to correct!
			end
``}``   
			default: begin
				uncorrectable_error = 1'b1;
				correctable_error = 1'b0;
			end
		endcase
		`$eccFuncName` = {uncorrectable_error,correctable_error,correction_pattern};
    end
endfunction

``}
    #return all except the last new line character
    return [string range [::tgpp::getProcOutput] 0 end-1]
} 
#end of hammingCodeFunction
``