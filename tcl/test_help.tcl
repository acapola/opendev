#! /bin/sh
# \
exec tclsh "$0" ${1+"$@@"}


#lappend ::auto_path [file normalize [file dirname [info script]]]
#package require ::nimp
source "nimp.tcl"

#simple option
#default value is 0
#value if present is 1
#do not consume any extra argument
::nimp::arg_proc clean 0 "delete output and intermediate files from previous runs" 1

#simple option with single argument
#no checking done on the argument
::nimp::arg_proc period 50000 "set period in ps" {[lindex $arg_list 0]} 1

#option with two arguments
#help text is customize depending on the calling command
::nimp::arg_proc range [list 0 10000] {= #put "=" as first character when you need to execute code rather than a value
    if {"::test::cmd2"==$context} {
        #can use return
        return "set range in mV"
    } else {
        #or just set the last value to the desired one.
        set short_help "set range in ps"
    }
} {[list [lindex $arg_list 0] [lindex $arg_list 1]]} 2


proc var_dbg_str { varname {level 1} } {
    upvar $level $varname var
    return "$varname=$var"
}

namespace eval ::test {
    namespace export cmd_name cmd2
}

proc ::test::cmd_name { args } {
    set arg_procs [list arg_clean arg_period arg_range]
    puts [::nimp::process_args $arg_procs $args]
    unset args
    unset arg_procs
    foreach var [info var] {
        puts [var_dbg_str $var]
    }
}  

proc ::test::cmd2 { args } {
    set arg_procs [list arg_period arg_range]
    puts [::nimp::process_args $arg_procs {*}$args]
    unset args
    unset arg_procs
    foreach var [info var] {
        puts [var_dbg_str $var]
    }
}   

::test::cmd_name
::test::cmd_name -range 2 3 -clean
::test::cmd2 -help
::test::cmd_name -help

foreach help [info procs ::nimp::*_proc_help] {
    set p [string range $help 0 end-[string length "_proc_help"]]
    puts "$p [info args $p]"
    puts [$help]
}


foreach test [info procs ::nimp::*_proc_test] {
    puts "run ${test}"    
    $test
}

