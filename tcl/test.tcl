#source aes.tcl
#source nimp.tcl
#source nimp_aes.tcl

lappend ::auto_path [file normalize [file dirname [info script]]]

package ifneeded aes 1.2.1 [list source aes.tcl]

package require nimp::aes

nimp::self_test
nimp::aes::self_test
