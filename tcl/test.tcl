lappend ::auto_path [file normalize [file dirname [info script]]]

package ifneeded aes 1.2.1 [list source [file join dependency aes.tcl]]
package ifneeded des 1.1.0 [list source [file join dependency des.tcl]]
package ifneeded tclDES 1.0.0 [list source [file join dependency tcldes.tcl]]
package ifneeded math::bignum 3.1.1 [list source [file join dependency bignum.tcl]]

package require nimp::aes
package require nimp::des

#nimp::self_test
#nimp::aes::self_test
nimp::des::self_test
