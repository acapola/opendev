
tgppInterp create pp

#all reference files are in utf-8 encoding (without BOM)
pp setOutEncoding utf-8

set setupCode {
``
	lappend ::auto_path [file normalize [file dirname [info script]]]

	package ifneeded aes 1.2.1 [list source [file join dependency aes.tcl]]
	package ifneeded des 1.1.0 [list source [file join dependency des.tcl]]
	package ifneeded tclDES 1.0.0 [list source [file join dependency tcldes.tcl]]
	package ifneeded math::bignum 3.1.1 [list source [file join dependency bignum.tcl]]
	package ifneeded nimpRandom 1.0 [list source [file join .. hamming_code_tgpp random.tcl]]
``
}
#package require nimp::aes
#package require nimp::des

pp str $setupCode

#pp setTclOut 1
array set r [pp file crypto2_lib.tgpp.c ""]


