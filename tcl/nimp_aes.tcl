
package require aes
package require nimp


namespace eval nimp::aes {
    set version 0.1
}

::nimp::proc+ ::nimp::aes::str_cleanup { str } {
	remove all white characters from a string
} {
	string map {  " " "" "\t" ""  "\r" "" "\n" "" } $str
} test {
	::nimp::assert_equal "1234" [str_cleanup " \t12 \n \r \t 34\n\r\t "]
}

::nimp::proc+ ::nimp::aes::hexstr_cleanup { hexstr } {
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

::nimp::proc+ ::nimp::aes::hexstr_to_bin { hexstr } {
	Convert an hexadecimal string like "12F4" to binary
	Input string is cleaned using hexstr_cleanup
} {
	binary format H* [hexstr_cleanup $hexstr]
} test {
	::nimp::assert_equal "efg" [hexstr_to_bin "656667"]
}

::nimp::proc+ ::nimp::aes::ecb { key data {mode encrypt} } {
	Encrypt or decrypt using AES in ECB mode. Key should be either 128, 192 or 256 bit.
	By default, do encryption. If decryption is desired, specify the third argument as "decrypt".
	Both key and data are expected as hexadecimal strings
	Result is given as an hexadecimal string
} {
	set key [hexstr_to_bin $key]
	set data [hexstr_to_bin $data]
	string toupper [::aes::aes -hex -mode ecb -dir $mode -key $key $data]
} test {
	#FIPS 197 test vectors
	::nimp::assert_equal "3925841D02DC09FBDC118597196A0B32" [ecb "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c" "32 43 f6 a8 88 5a 30 8d 31 31 98 a2 e0 37 07 34"]	
	::nimp::assert_equal "69C4E0D86A7B0430D8CDB78070B4C55A" [ecb "000102030405060708090a0b0c0d0e0f" 								"00112233445566778899aabbccddeeff"]	
	::nimp::assert_equal "DDA97CA4864CDFE06EAF70A0EC0D7191" [ecb "000102030405060708090a0b0c0d0e0f1011121314151617" 				"00112233445566778899aabbccddeeff"]	
	::nimp::assert_equal "8EA2B7CA516745BFEAFC49904B496089" [ecb "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f" "00112233445566778899aabbccddeeff"]	
	
	::nimp::assert_equal "00112233445566778899AABBCCDDEEFF" [ecb "000102030405060708090a0b0c0d0e0f" 								"69C4E0D86A7B0430D8CDB78070B4C55A" decrypt]	
	::nimp::assert_equal "00112233445566778899AABBCCDDEEFF" [ecb "000102030405060708090a0b0c0d0e0f1011121314151617"				    "DDA97CA4864CDFE06EAF70A0EC0D7191" decrypt]	
	::nimp::assert_equal "00112233445566778899AABBCCDDEEFF" [ecb "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f" "8EA2B7CA516745BFEAFC49904B496089" decrypt]	
}

proc ::nimp::aes::cbc_enc { key data } {
	set key [hexstr_to_bin $key]
	set data [hexstr_to_bin $data]
	string toupper [aes::aes -hex -mode cbc -dir encrypt -key $key $data]
}

proc ::nimp::aes::cbc_dec { key data } {
	set key [hexstr_to_bin $key]
	set data [hexstr_to_bin $data]
	string toupper [aes::aes -hex -mode cbc -dir decrypt -key $key $data]
}


proc ::nimp::aes::self_test {} {
	::nimp::show_help_and_test ::nimp::aes
}

package provide nimp::aes $::nimp::aes::version


