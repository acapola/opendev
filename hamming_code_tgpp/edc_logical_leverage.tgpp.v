``
#this file is not meant to be used directly, it only declare tcl procs.

proc edcLogicalLeverageVerilogFunction { name logicalGroups } {
	set inputWidth 0
	dict for {k v} $logicalGroups {
		set inputWidth [expr $inputWidth + [llength $v]]
	}
	set funcName "${name}_to_leveraged_edc_groups"
	set invFuncName "${name}_from_leveraged_edc_groups"
	
	set indexesWidth [decWidth $inputWidth]
``
function [`$inputWidth`-1:0] `$funcName`;
    input [`$inputWidth`-1:0] in;
	reg [`$inputWidth`-1:0] out;
    begin
``	set base 0
	dict for {group groupBits} $logicalGroups {
		for {set i 0} {$i<[llength $groupBits]} {incr i} {
			lset groupBits $i [format %${indexesWidth}s [lindex $groupBits $i]]
		}
``
		out[`format %${indexesWidth}s $base`+:`format %${indexesWidth}s [llength $groupBits]`] = {in[`listToString $groupBits {],in[}`]};
``
	set base [expr $base + [llength $groupBits]]
	}
``
        `$funcName` = out;
    end
endfunction
function [`$inputWidth`-1:0] `$invFuncName`;
    input [`$inputWidth`-1:0] in;
	reg [`$inputWidth`-1:0] out;
    begin
``	set base 0
	dict for {group groupBits} $logicalGroups {
		for {set i 0} {$i<[llength $groupBits]} {incr i} {
			lset groupBits $i [format %${indexesWidth}s [lindex $groupBits $i]]
		}
``
		{out[`listToString $groupBits {],out[}`]} = in[`format %${indexesWidth}s $base`+:`format %${indexesWidth}s [llength $groupBits]`];
``
	set base [expr $base + [llength $groupBits]]
	}
``
        `$invFuncName` = out;
    end
endfunction
``
    #return all except the last new line character
    return [string range [::tgpp::getProcOutput] 0 end-1]
} 
#end of edcLogicalLeverageVerilogFunction
``