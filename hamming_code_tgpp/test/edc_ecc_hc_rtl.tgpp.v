``
tgpp::source [file join [file dirname [info script]] .. hamming_code.tgpp]

# entry point prototype look like that:
# proc regFaultDetectorModule { moduleName inputWidth edcInputWidth {extended 0} {edcOutputWidth 0} } 

# extended can be 0, normal, 1, extended or ecc
# if it is ecc, error correction is also supported

# Trade off parameters:

# edcInputWidth: impact how many EDC groups do we get. the bigger edcInputWidth, the smaller the number of EDC groups.

# edcOutputWidth: impact how deep is the EDC logic. the bigger edcOutputWidth, the smaller the logic depth (but more flipflops needed to store the EDC code).
# fastest circuit possible: set edcOutputWidth=edcInputWidth
# then only one xor per syndrom bit

set input_widths [list 4 8 12 16 20 24 32 36 40 48 64 96 128]
``
/*
Hamming code EDC/ECC modules library
*/
/////////////////////////////////////////////////////////////////////////////////////////////////
// *_min_width modules: use the least possible EDC bits
/////////////////////////////////////////////////////////////////////////////////////////////////
``
foreach width $input_widths { puts "$width"``
`hammingCodeVerilogModule edc_hc_${width}_min_width $width normal 0`
`hammingCodeVerilogModule edc_ehc_${width}_min_width $width extended 0`
`hammingCodeVerilogModule ecc_ehc_${width}_min_width $width ecc 0`

``}``

/////////////////////////////////////////////////////////////////////////////////////////////////
// *_min_delay modules: use the least logic gates between input and outputs (keep logic shallow)
/////////////////////////////////////////////////////////////////////////////////////////////////
``
foreach width $input_widths { puts "$width"``
`hammingCodeVerilogModule edc_hc_${width}_min_delay $width normal $width`
`hammingCodeVerilogModule edc_ehc_${width}_min_delay $width extended $width`
`hammingCodeVerilogModule ecc_ehc_${width}_min_delay $width ecc $width`

``}``

/////////////////////////////////////////////////////////////////////////////////////////////////
// *_balanced modules: balanced trade-off between logic depth and EDC width.
//EDC width is half of data's width or the minimum width + 1 for small input sizes.
/////////////////////////////////////////////////////////////////////////////////////////////////
``
foreach width $input_widths {
	set out_width [expr $width / 2]
	set min_width [hammingCodeMinWidth $width 0]
	if {$out_width<$min_width} {set out_width [expr $min_width+1]}
	set out_width_extended $out_width
	set min_width_extended [hammingCodeMinWidth $width 1]
	if {$out_width_extended<$min_width_extended} {set out_width_extended [expr $min_width_extended+1]}
puts "$width"``
`hammingCodeVerilogModule edc_hc_${width}_balanced $width normal $out_width`
`hammingCodeVerilogModule edc_ehc_${width}_balanced $width extended $out_width_extended`
`hammingCodeVerilogModule ecc_ehc_${width}_balanced $width ecc $out_width_extended`

``}``


