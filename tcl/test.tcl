lappend ::auto_path [file normalize [file dirname [info script]]]

package ifneeded aes 1.2.1 [list source [file join dependency aes.tcl]]
package ifneeded math::bignum 3.1.1 [list source [file join dependency bignum.tcl]]

package require nimp::aes

nimp::self_test
nimp::aes::self_test
