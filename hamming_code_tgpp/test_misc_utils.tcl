source [file join [file dirname [info script]] misc_utils.tcl]

proc test_lreplaceValues {} {
    assertEqual [list a 2 b] [lreplaceValues [list 1 2 3] [dict create 1 a 0 c 3 b]]
    assertEqual a [lreplaceValues 1 [dict create 1 a 0 c 3 b]]
    puts "lreplaceValues PASS"
}

proc test_dictReplaceValues {} {
    assertEqual [dict create 1 a 2 [list 1 2 a 4 b]] [dictReplaceValues [dict create 1 3 2 [list 1 2 3 4 5]] [dict create 3 a 5 b 6 c]]
    puts "dictReplaceValues PASS"
}

proc test_toHexStr {} {
	assertEqual 0 [toHexStr 0]
	assertEqual 1 [toHexStr 1]
	assertEqual 2 [toHexStr 2]
	assertEqual A [toHexStr 10]
	assertEqual F [toHexStr 15]
	assertEqual 10 [toHexStr 16]
	assertEqual 100 [toHexStr 256]
	assertEqual 100000000 [toHexStr [expr 1<<32] ]
	puts "toHexStr PASS"
}

test_lreplaceValues
test_dictReplaceValues
test_toHexStr