
package require Tcl 8.4 
package require math::bignum

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

### STRING #######################################################################

::nimp::proc+ ::nimp::str_split { str chunkLen } {
	Split a string into chuncks of a given length
	chunkLen: maximum number of characters for chunks
	Result is a list
} {
	set len [string length $str]
	set nChunks [expr ($len + $chunkLen - 1) / $chunkLen]
	set out [list]
	for {set i 0} {$i<$nChunks} {incr i} {
		lappend out [string range $str [expr $i * $chunkLen ] [expr ($i +1)*$chunkLen -1]]
	}
	return $out
} test {
	::nimp::assert_equal [list 12 34] [str_split "1234" 2]
	::nimp::assert_equal [list 12 34 5] [str_split "12345" 2]
	::nimp::assert_equal [list 1] [str_split "1" 2]
	::nimp::assert_equal [list] [str_split "" 2]
}

::nimp::proc+ ::nimp::str_cleanup { str } {
	remove all white characters from a string
} {
	string map {  " " "" "\t" ""  "\r" "" "\n" "" } $str
} test {
	::nimp::assert_equal "1234" [str_cleanup " \t12 \n \r \t 34\n\r\t "]
}

::nimp::proc+ ::nimp::hexstr_cleanup { hexstr } {
	"Clean-up" an hexadecimal string. That means removing the following (in respective order):
	- strings 0x data key iv (no matter the casing)
	- white characters
	- any non alpha numeric character
	If the remaining contain non hexadecimal digits, an error is thrown
	WARNING: this is going to mess up string like "0x3, 0x4": one would expect to get "0304" 
	but result will be "34", which may break everything silently... 
} {
	set hexstr [string map -nocase { 0x "" data "" key "" iv "" } $hexstr]
	set hexstr [str_cleanup $hexstr]
	while 1 {
		set i -1
		set status [string is alnum -failindex i $hexstr]
		if {$status} break; #we have an alphanumeric string
		#otherwise, remove the unmatching char:
		#puts $hexstr
		set hexstr "[string range $hexstr 0 [expr $i-1]][string range $hexstr [expr $i+1] end]"
		#puts $hexstr
		#puts ""
	}
	if {![string is xdigit $hexstr]} {
		::nimp::report_fatal_error "invalid character in hexadecimal following string \n${hexstr}\n"
	}
	return $hexstr
} test {
	::nimp::assert_equal "1234AB" [hexstr_cleanup "data 0x12, \t \n \r 0x34_AB--"]
	::nimp::assert_equal "1234AB" [hexstr_cleanup "-key iv 0x12, 0x34_AB-"]
}

::nimp::proc+ ::nimp::hexstr_to_bin { hexstr } {
	Convert an hexadecimal string like "12F4" to binary
	Input string is cleaned using hexstr_cleanup
} {
	binary format H* [hexstr_cleanup $hexstr]
} test {
	::nimp::assert_equal "efg" [hexstr_to_bin "656667"]
}

::nimp::proc+ ::nimp::bin_to_hexstr { bin } {
	Convert binary data to an hexadecimal string like "12F4"
} {
	binary scan $bin H* hexstr
	string toupper $hexstr
} test {
	::nimp::assert_equal "656667" [bin_to_hexstr "efg"]
}

proc ::nimp::num_to_hexstr { n } {
    set t [::math::bignum::fromstr [string tolower $n] 16]
    return [string toupper [::math::bignum::tostr $t 16]]
}

proc ::nimp::hexstr_xor { a b } {
    set a [::math::bignum::fromstr [string tolower $a] 16]
    set b [::math::bignum::fromstr [string tolower $b] 16]
    set out [::math::bignum::bitxor $a $b]
    return [string toupper [::math::bignum::tostr $out 16]]
}

proc ::nimp::hexstr_add { a b } {
    set a [::math::bignum::fromstr [string tolower $a] 16]
    set b [::math::bignum::fromstr [string tolower $b] 16]
    set out [::math::bignum::add $a $b]
    return [string toupper [::math::bignum::tostr $out 16]]
}

proc xor128 {a b} {
    set a [::math::bignum::fromstr [string tolower $a] 16]
    set b [::math::bignum::fromstr [string tolower $b] 16]
    set out [::math::bignum::bitxor $a $b]
    return [numNormalize [::math::bignum::tostr $out 16] 32]
}



proc ::nimp::self_test {} {
	::nimp::show_help_and_test ::nimp
}





package provide nimp $::nimp::version

