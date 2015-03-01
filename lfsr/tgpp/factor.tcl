

proc factor { num } {
    #set res [exec yafu factor($num)]
	if {[file exists factor.log]} {
		#this log files can grow very big, clean it.
		file delete factor.log
	}
	set status [catch {exec yafu factor($num)} res]
	if {$status == 0} {
		# The command succeeded, and wrote nothing to stderr.
		# $result contains what it wrote to stdout, unless you
		# redirected it
	} elseif {[string equal $::errorCode NONE]} {
		# The command exited with a normal status, but wrote something
		# to stderr, which is included in $result.
	} else {
		switch -exact -- [lindex $::errorCode 0] {
			CHILDKILLED {
				puts CHILDKILLED
				foreach {- pid sigName msg} $::errorCode break

				# A child process, whose process ID was $pid,
				# died on a signal named $sigName.  A human-
				# readable message appears in $msg.

			}
			CHILDSTATUS {
				puts CHILDSTATUS
				foreach {- pid code} $::errorCode break

				# A child process, whose process ID was $pid,
				# exited with a non-zero exit status, $code.

			}

			CHILDSUSP {
				puts CHILDSUSP
				foreach {- pid sigName msg} $::errorCode break

				# A child process, whose process ID was $pid,
				# has been suspended because of a signal named
				# $sigName.  A human-readable description of the
				# signal appears in $msg.

			}

			POSIX {
				puts POSIX
				foreach {- errName msg} $::errorCode break

				# One of the kernel calls to launch the command
				# failed.  The error code is in $errName, and a
				# human-readable message is in $msg.

			}

		}
	}
	set pPos [string first "\nP" $res]
    set res [string range $res $pPos end]
	set ansPos [string first "\nans" $res]
    set res [string range $res 0 $ansPos]
    #puts $res
    set lines [split $res "\n"]
    set factors [dict create]
    foreach line $lines {
        set tokens [split $line "="]
        set factor [string trim [lindex $tokens 1]]
        if {[string length $factor]} {
            #puts $factor
            if {[dict exists $factors $factor]} {
                dict incr factors $factor
            } else {
                dict set factors $factor 1
            }
        }
    }
    return $factors
}

#TODO: optimmize
proc pow {base exp} {
    set out $base
    for {set i 1} {$i<$exp} {incr i} {
        set out [expr $out * $base]
    }
    return $out
}

proc test {} {
    for {set i 192} {$i < 200} {incr i} {
        set n [expr [pow 2 $i] -1]
        puts "$i -> $n"
        set factors [factor $n]
        dict for {k v} $factors {
            puts "$k ^ $v"
        }
        flush stdout
    }
}
#test