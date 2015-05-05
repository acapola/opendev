``
#this file is not meant to be used directly, the entry point is hamming_code.tgpp

proc hammingCodeVerilogFunction { inputWidth {code_extention 0} {userOutputWidth 0} } {
	set parameters [hammingCodeFunctionParams $inputWidth $code_extention $userOutputWidth]
    dictToVars $parameters

    set hammingCodeDict [hammingCode $inputWidth $extended $userOutputWidth]
	if {$ecc} {
		#compute the corrector part
		hammingCodeCorrectionPattern hammingCodeDict
	}
	
	#do a bit of self checking
	checkHammingCode $hammingCodeDict
	return [hammingCodeVerilogFunction2 $hammingCodeDict $ecc]
}

proc hammingCodeVerilogModule { name inputWidth {code_extention 0} {userOutputWidth 0} } {
	set parameters [hammingCodeFunctionParams $inputWidth $code_extention $userOutputWidth]
    dictToVars $parameters

	set hammingCodeDict [hammingCode $inputWidth $code_extention $userOutputWidth]
	if {$ecc} {
		#compute the corrector part
		hammingCodeCorrectionPattern hammingCodeDict
	}
	
	#do a bit of self checking
	checkHammingCode $hammingCodeDict	
``
module `$name` (``
	input wire [`$inputWidth`-1:0] i_write_data, // Data to write to storage
	output reg [`$r`-1:0] o_write_edc, // EDC bits to write to storage
	input wire [`$inputWidth`-1:0] i_stored_data, // Data read from storage, may contain error(s)
	input wire [`$r`-1:0] i_stored_edc, // EDC bits read from storage, may conatin error(s)
if {$ecc} {``
	output reg [`$inputWidth`-1:0] o_read_data, // Error free read data (as long as error was correctable)
	output reg o_correction, //indication that an error is corrected
``
	output reg o_detection //indication that an error is detected``if {$ecc} {``, this happens only if the error is not correctable``}``
);
`hammingCodeVerilogFunction2 $hammingCodeDict $ecc`
wire [`$r`-1:0] stored_data_edc = `$funcName`(i_stored_data);
wire [`$r`-1:0] syndroms = i_stored_edc ^ stored_data_edc;
``if {$ecc} {``
reg uncorrectable_error;
reg [`$inputWidth`-1:0] correction_pattern;
always @* begin
	{o_detection,o_correction,correction_pattern} = `$eccFuncName`(syndroms,CORRECT_EDC_SINGLE_BIT_ERRORS);     
	o_read_data = i_stored_data ^ correction_pattern;
end
``} else {``
always @* o_detection = |syndroms;
``}``
endmodule
``
    #return all except the last new line character
    return [string range [::tgpp::getProcOutput] 0 end-1]
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