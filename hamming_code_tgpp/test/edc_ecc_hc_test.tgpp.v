``
tgpp::source [file join [file dirname [info script]] .. hamming_code.tgpp]

``

/////////////////////////////////////////////////////////////////////////////////////////////////
// test module: instantiate all declared modules
/////////////////////////////////////////////////////////////////////////////////////////////////
``
set input_widths [list 4 8 12 16 20 24 32 36 40 48 64 96 128]

set edc_width_sum 0
set corrected_data_width_sum 0
set status_outputs_width_sum 0
set edc_hc_modules_widths [list]
set edc_ehc_modules_widths [list]
set n_modules [expr [llength $input_widths]*3]
foreach width $input_widths {
	set out_width [expr $width / 2]
	set min_width [hammingCodeMinWidth $width 0]
	if {$out_width<$min_width} {set out_width [expr $min_width+1]}
	set out_width_extended $out_width
	set min_width_extended [hammingCodeMinWidth $width 1]
	if {$out_width_extended<$min_width_extended} {set out_width_extended [expr $min_width_extended+1]}
	
	lappend edc_hc_modules_widths $min_width
	lappend edc_ehc_modules_widths $min_width_extended
	
	lappend edc_hc_modules_widths $width
	lappend edc_ehc_modules_widths $width
	
	lappend edc_hc_modules_widths $out_width
	lappend edc_ehc_modules_widths $out_width_extended
	
	incr edc_width_sum [expr $min_width + 2*$min_width_extended]
	incr edc_width_sum [expr 3*$width]
	incr edc_width_sum [expr $out_width + 2*$out_width_extended]

	incr corrected_data_width_sum [expr $width*3]
	
	incr status_outputs_width_sum [expr 3*2*1+3*1*2]
}
``
module edc_ecc_hc_test(
	input wire i_reset,
	input wire i_clk,
	input wire [384-1:0] i_dat,
	input wire [384*2-1:0] i_fault,
	output wire [`$n_modules`-1:0] o_edc_hc_detection,
	output wire [`$n_modules`-1:0] o_edc_ehc_detection
	output wire [`$corrected_data_width_sum`-1:0] o_corrected_data,
	output wire [`$n_modules`-1:0] o_correction,
	output wire [`$n_modules`-1:0] o_ecc_detection
);
reg [384-1:0] data_storage;
reg [`$edc_width_sum`-1:0] edc_storage;
wire [`$edc_width_sum`-1:0] edc_write;

always @(posedge i_clk, posedge i_reset) begin
	if(i_reset) {data_storage,edc_storage} <= {384+`$edc_width_sum`{1'b0}};
	else {data_storage,edc_storage} <= {i_dat,edc_write};
end

``
set mod_types [list min_width min_delay balanced]
set edc_offset 0
set corrected_data_offset 0
for {set i 0} {$i<[llength $input_widths]} {incr i} {
	set width [lindex $input_widths $i]
	set edc_hc_widths [lrange $edc_hc_modules_widths [expr 3*$i] [expr 3*$i+2]]  
	set edc_ehc_widths [lrange $edc_ehc_modules_widths [expr 3*$i] [expr 3*$i+2]]  
	for {set j 0} {$j<3} {incr j} {
		set mod_type [lindex $mod_types $j]
		set edc_hc_width [lindex $edc_hc_widths $j]
		set edc_ehc_width [lindex $edc_ehc_widths $j]
		set status_output_offset [expr 3*$i+$j]
	``
edc_hc_`$width`_`$mod_type` u_edc_hc_`$width`_`$mod_type`(
	.i_write_data(i_dat[`$width`-1:0]),
	.o_write_edc(edc_write[`$edc_offset`+:`$edc_hc_width`]),
	.i_stored_data(data_storage[`$width`-1:0]),
	.i_stored_edc(edc_storage[`$edc_offset`+:`$edc_hc_width`]),
	.o_detection(o_edc_hc_detection[`$status_output_offset`])
);
``		incr edc_offset $edc_hc_width``
edc_ehc_`$width`_`$mod_type` u_edc_ehc_`$width`_`$mod_type`(
	.i_write_data(i_dat[`$width`-1:0]),
	.o_write_edc(edc_write[`$edc_offset`+:`$edc_ehc_width`]),
	.i_stored_data(data_storage[`$width`-1:0]),
	.i_stored_edc(edc_storage[`$edc_offset`+:`$edc_ehc_width`]),
	.o_detection(o_edc_ehc_detection[`$status_output_offset`])
);
``		incr edc_offset $edc_ehc_width``
ecc_ehc_`$width`_`$mod_type` u_ecc_ehc_`$width`_`$mod_type`(
	.i_write_data(i_dat[`$width`-1:0]),
	.o_write_edc(edc_write[`$edc_offset`+:`$edc_ehc_width`]),
	.i_stored_data(data_storage[`$width`-1:0]),
	.i_stored_edc(edc_storage[`$edc_offset`+:`$edc_ehc_width`]),
	.o_read_data(o_corrected_data[`$corrected_data_offset`+:`$width`]),
	.o_correction(o_correction[`$status_output_offset`]),
	.o_detection(o_ecc_detection[`$status_output_offset`])
);
``		incr edc_offset $edc_ehc_width
		incr corrected_data_offset  $width
	}
}``
endmodule
