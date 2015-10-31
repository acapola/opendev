
package require Tcl 8.4 

namespace eval nimp {
    set version 0.2
    #TODO: add error counter for test procs and assert calls
}

proc ::nimp::proc+_proc_help {} {
    return {
Helper function to declare simple procs with associated help message and unit test.
This create the proc and another one postfixed with "_proc_help".
This avoid any runtime penalty and allow easy automatic collection of help messages.
Similarly it creates a test proc postfixed with "_proc_test". 
    }
}

proc ::nimp::proc+ { name args help body {test_mark ""} {test ""} } {
    if {$test_mark=="test"} {
        if {$test!=""} {  
            #create the test proc only if it contain some code (which may be just a comment)
            set a [proc ${name}_proc_test {} $test]         
        } 
    } elseif {$test_mark==""} {
        if {$test!=""} {
            error "\nERROR: test_mark must be 'test', but it is an empty string\n"
        }    
    } else {
        error "\nERROR: test_mark must be 'test', found '${test_mark}'\n"
    }
    
    set a [proc ${name} $args $body]
    if {[string range $help 0 0]!="\""} {
        set help "\{${help}\}"    
    }
    set help_body "return $help"
    set a [proc ${name}_proc_help {} $help_body]
}

proc ::nimp::var_dbg_str { varname {level 1} } {
    upvar $level $varname var
    return "$varname=$var"
}

proc ::nimp::dbg_puts { varname {level 1} } {
    upvar $level $varname var
    puts "$varname=$var"
}

::nimp::proc+ ::nimp::report_fatal_error { msg } {
    Format the error message and throw the error
} {
    set msg "\nERROR: ${msg}\n"
    error $msg
}

::nimp::proc+ ::nimp::assert_equal { expected actual {msg ""} args } {
    Check if expected equals actual. If not, throw an error with the specified message.
    By default a dump of the expected and actual values is appended to the error message.    
    This can be overriden by setting args to "-no_dump".
} {
    if {$expected!=$actual} {
        if {$msg==""} {
            set msg "assertion failed"
        }
        if {-1==[lsearch $args "-no_dump"]} { 
            append msg "\n"
            append msg "\texpected: $expected\n"
            append msg "\tactual:   $actual\n"
        }        
        ::nimp::report_fatal_error $msg        
    }
}

proc ::nimp::show_help_and_test { package_name } {
	set sep [string repeat "-" 80]
	foreach help [info procs "${package_name}::*_proc_help"] {
		set p [string range $help 0 end-[string length "_proc_help"]]
		puts $sep
		puts "$p [info args $p]"
		puts [$help]
		set ptest "${p}_proc_test"
		if { [string length [info procs $ptest]] > 0 } {
			puts "run ${ptest}"    
			$ptest
			puts "test passed"
		}
		puts ""
	}
}

::nimp::proc+ ::nimp::get_caller_proc_name { {level 1} } {
    get the name of a calling proc.
    level control how far to climb up the calling stack.
    0 returns the current proc (as it is the caller of this proc)    
    1 returns the caller of the current proc and so on. 
    It returns an empty string if the top of the stack is reached.
} {
    return [lindex [get_caller [expr 1+$level]] 0]
} test {
    assert_equal "::nimp::get_caller_proc_name_proc_test" [::nimp::get_caller_proc_name 0]
}

::nimp::proc+ ::nimp::get_caller { {level 1} } {
    get the code of a call in the call stack (name of the proc + its arguments)
    level control how far to climb up the calling stack.
    0 returns the current proc (as it is the caller of this proc)    
    1 returns the caller of the current proc and so on. 
    It returns an empty string if the top of the stack is reached.
} {
    set r [catch {info level [expr [info level] - 1 - $level]} e]
    if {$r} {
        return ""
    } {
        return $e
    }
}

::nimp::proc+ ::nimp::dict_to_vars { dict_value {level 1} } {
    Declare all keys in a dict as variable in the current scope or higher up in the stack.
    By default variables are declared in the current scope, this is control by level:
    1 declare in the current scope
    2 declare in the caller and so on.
} {
    dict for {name value} $dict_value {
        upvar $level $name var
        set var $value    
    } 
}

proc ::nimp::K {a b} {set a}

proc ::nimp::get_value { varname {level 1} } {
    upvar $level $varname var
    return $var
}

proc ::nimp::arg_proc { name default short_help value {consumed 0} {help ""} } {
    if {[llength $help]==0} {
        set help $short_help    
    }
    foreach arg [list default short_help help value consumed] {
        set val [get_value $arg]
        if {[string range $val 0 0]=="="} { #code
            set $arg [string range $val 1 end]                    
        } else { #data
            set $arg "set a \"$val\""
        }     
    }
    set script {
        switch $req {
            "default"       {set out [eval ${default}]}
            "short_help"    {set out [eval ${short_help}]}
            "help"          {set out [eval ${help}]}
            "parse"         {set out [dict create value [eval ${value}] consumed [eval ${consumed}]]}
        }
    }
    set script [string map [list \${default} [list $default] \${short_help} [list $short_help] \${help} [list $help] \${value} [list ${value}] \${consumed} [list ${consumed}]] $script]
    #puts $script
    set a [proc arg_${name} { context req arg_list } $script]
}

proc ::nimp::process_args { arg_procs arg_list } {
    set n_args [llength $arg_list]
    set out [dict create]
    set context [get_caller_proc_name]

    #set default values
    foreach proc_name $arg_procs {
        dict set out [string range $proc_name 4 end] [$proc_name $context default [list]]                
    }

    #check for empty list of args
    #if {$args=="{}"} {
    #    return $out
    #}

    #process args
    set help 0
    set i 1
    while {[llength $arg_list]>0} {
        #puts "$i [llength $args] [lindex $args 0] $args"
        #fetch current argument
        set arg [lindex $arg_list 0]
        #check standard args
        switch $arg {
            "--" { #explicit end of arg list
                set args [lrange $arg_list 1 end]
                break
            }
            "-help" {
                set help 1
                #clear any previous processing
                set out [dict create] 
                foreach proc_name $arg_procs { #for each proc, fill default value and help text
                    dict set out [string range $proc_name 4 end] default [$proc_name $context default [list]]
                    dict set out [string range $proc_name 4 end] help    [$proc_name $context help [list]]                
                }
                break
            }
        }
        
        #check it starts with "-"
        if {"-"!=[string range $arg 0 0]} {
            report_fatal_error "argument $i does not start with '-': $arg"        
        }                
        #remove leading "-"
        set arg_name [string range $arg 1 end]
        set proc_name "arg_$arg_name"        
        set proc_index [lsearch $arg_procs $proc_name]
        #check if arg is supported
        if {-1==$proc_index} {
            report_fatal_error "argument $i is not supported: $arg"        
        }
        #invoke the arg proc
        set arg_list [lrange $arg_list 1 end]
        set res [$proc_name $context parse $arg_list]
        set consumed [dict get $res consumed]
        #puts $res
        dict set out $arg_name [dict get $res value]
        if {""!=$consumed} {
            set i [expr $i+1+$consumed]        
            set arg_list [lrange $arg_list $consumed end]
        } else {
            incr i
        } 
    }
    if {!$help} {#push args as variable in the caller's scope
        dict_to_vars $out 2
    }
    return $out
}


### STAT #######################################################################

proc ::nimp::initStat {} {
  return [dict create cnt 0]
}

proc ::nimp::updateStat { stats newValue } {
  set cnt [dict get $stats cnt]
  if {0==$cnt} {
    set min $newValue
    set max $newValue
    set sum $newValue
  } else {
    set min [dict get $stats min]
    if {$min>$newValue} {set min $newValue}
    set max [dict get $stats max]
    if {$max<$newValue} {set max $newValue}
    set sum [expr [dict get $stats sum] + $newValue]
  }
  incr cnt
  return [dict create min $min max $max sum $sum cnt $cnt]
}

proc ::nimp::mergeStat { stats1 stats2 } {
  set cnt [expr [dict get $stats1 cnt] + [dict get $stats2 cnt]]
  set sum [expr [dict get $stats1 sum] + [dict get $stats2 sum]]
  set min [dict get $stats1 min]
  if {$min>[dict get $stats2 min]} {set min [dict get $stats2 min]}
  set max [dict get $stats1 max]
  if {$max<[dict get $stats2 max]} {set max [dict get $stats2 max]}
  
  return [dict create min $min max $max sum $sum cnt $cnt]
}

proc ::nimp::statColumnsStr { {titleWidth 10} {columnsWidth 10}} {
  return [format "%*s %*s %*s %*s %*s %*s" $titleWidth "" $columnsWidth min $columnsWidth max $columnsWidth sum $columnsWidth cnt $columnsWidth average]
}

proc ::nimp::statStr { stat {title ""} {titleWidth 10} {columnsWidth 10}} {
  set cnt [dict get $stat cnt]
  if {0==$cnt} { return "" }
  set sum [dict get $stat sum]
  set average [expr $sum/$cnt]
  return [format "%*s %*s %*s %*s %*s %*s" $titleWidth ${title} $columnsWidth [dict get $stat min] $columnsWidth [dict get $stat max] $columnsWidth ${sum} $columnsWidth ${cnt} $columnsWidth ${average}]
}

proc ::nimp::self_test {} {
	::nimp::show_help_and_test ::nimp
}

package provide nimp $::nimp::version

