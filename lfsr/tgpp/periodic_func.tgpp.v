``
# Generate a step function with a fix period
# The hardware generated is either an LFSR or an NLFSR, 
# This is actually specifed by the calling proc which sets the argument "periodic_func_spec"
proc periodic_func { periodic_func_spec } {
	# read parameters and check them
	set period [dict get $periodic_func_spec period]
	set taps [dict get $periodic_func_spec taps]
    set init [string reverse [dict get $periodic_func_spec init]]
    set width [dict size $taps]
	set init_len [string length $init]
	if {[expr $width != $init_len]} {
		error "Invalid parameters: width=$width and init=$init. Length of init is $init_len but must be equal to width (number of taps)"
	}
    set msb [expr $width - 1]
	
	# check if names have been already set by caller, otherwise set default names.
	if {[dict exist $periodic_func_spec name_base]} {
		set name_base [dict get $periodic_func_spec name_base]
	} else {
		set name_base "period_${period}_${width}_bits"   
	}
	if {[dict exist $periodic_func_spec name_next]} {
		set name_next [dict get $periodic_func_spec name_next]
	} else {
		set name_next "${name_base}_next_f"   
	}
	if {[dict exist $periodic_func_spec name_val]} {
		set name_val [dict get $periodic_func_spec name_val]
	} else {
		set name_val "${name_base}_val_f"   
	}
``
// Functions for low power/high frequency periodic signal
// Period:         `$period`
// Number of bits: `$width`
// 
// Typical usage:
// reg [`$width`-1:0] state;
// always @(posedge i_clk, posedge i_reset) begin
//     if(i_reset) state <= `$name_val`(0);
//     else        state <= `$name_next`(state);
// end
//
// To generate a trigger at a particular cycle, use `$name_val`
// For example at the 3rd cycle:
// wire third_cycle = state == `$name_val`(3);
function automatic [`$msb`:0] `$name_val`;
	input integer target_step;
	reg [`$msb`:0] tmp;
	integer i;
	begin
		tmp = `$width`'b`$init`;
		for(i=0;i<target_step;i=i+1'b1) tmp = `$name_next`(tmp);
		`$name_val` = tmp;
	end
endfunction
function automatic [`$msb`:0] `$name_next`;
    input [`$msb`:0] in;
    reg [`$msb`:0] out;
    begin
``	dict for {pos tap_spec} $taps {
		set func_spec [dict create input_name in func $tap_spec]``
		out[`$pos`] = `gen_boolean_func $func_spec`;
``	}``
        `$name_next` = out;
    end
endfunction
``
    #return all except the last new line character
    return [string range [::tgpp::getProcOutput] 0 end-1]
}

# sourcing a pure TCL file to get the mapping of period to boolean function
source [file join [file dirname [info script]] load_periodic_func_defs.tcl]

##################################
# command line arguments parsing #
##################################
#parse command line only if this file is executed as a stand alone generator (ie. not included in another file)
if {[info ex argv0] && [file tail [info script]] == [file tail $argv0]} {
    if { ($argc < 1) || ($argc >2) } {
        puts "ERROR: $argc argument provided, need 1 or 2."
        puts "argc=$argc, argv=$argv"
        puts "arguments: period \[name_base\]"
		puts "supported value for period: 3 to 255
        exit -1
    }
	set spec [dict create period [lindex $argv 0]]
	if {[llength $argv]==2} {
		dict set spec name_base [lindex $argv 1]
	}
``
`periodic_func [periodic_func_defs::get_func_spec $spec]`
``
}

