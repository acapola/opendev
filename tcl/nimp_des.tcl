
package require des
package require nimp


namespace eval nimp::des {
    set version 0.1
}

proc ::nimp::des::key_to_bin { key } {
	set key [::nimp::hexstr_cleanup $key]
	if { 32==[string length $key] } { #TDES 2K: set k3=k1
		append key [string range $key 0 15]
	}
	::nimp::hexstr_to_bin $key
}

::nimp::proc+ ::nimp::des::ecb { key data {mode encrypt} } {
	Encrypt or decrypt using DES in ECB mode. Key should be either 64, 128 or 192 bit.
	By default, do encryption. If decryption is desired, specify the last argument as "decrypt".
	Both key and data are expected as hexadecimal strings
	Result is given as an hexadecimal string
} {
	set key [::nimp::des::key_to_bin $key]
	set data [::nimp::hexstr_to_bin $data]
	string toupper [::DES::des -hex -mode ecb -dir $mode -key $key $data]
} test {
	#NIST's SP800-67-rev1 test vectors
	::nimp::assert_equal [::nimp::hexstr_cleanup A826FD8CE53B855F_CCE21C8112256FE6_68D5C05DD9B6B900] [ecb 0123456789ABCDEF_23456789ABCDEF01_456789ABCDEF0123 5468652071756663_6B2062726F776E20_666F78206A756D70]
	::nimp::assert_equal [::nimp::hexstr_cleanup 5468652071756663_6B2062726F776E20_666F78206A756D70] [ecb 0123456789ABCDEF_23456789ABCDEF01_456789ABCDEF0123 A826FD8CE53B855F_CCE21C8112256FE6_68D5C05DD9B6B900 decrypt]
	
	#custom tests
	::nimp::assert_equal 483D84F442CBC676 [ecb EB3DB5B2B6C07CE4                                 0001020304050607]
    ::nimp::assert_equal 70F33452097D0546 [ecb 0123456789ABCDEF                                 0001020304050607 decrypt]
    ::nimp::assert_equal E1133759E627C7AC [ecb EB3DB5B2B6C07CE40123456789ABCDEF                 0001020304050607]
    ::nimp::assert_equal ABBC4E4502DE86D4 [ecb EB3DB5B2B6C07CE40123456789ABCDEF                 0001020304050607 decrypt]
    ::nimp::assert_equal 5CDEF45352FE84B4 [ecb EB3DB5B2B6C07CE40123456789ABCDEF22A6F492C49AC2B8 0001020304050607]
    ::nimp::assert_equal 6B1ABBAA9151C7B2 [ecb EB3DB5B2B6C07CE40123456789ABCDEF22A6F492C49AC2B8 0001020304050607 decrypt]
}

::nimp::proc+ ::nimp::des::cbc { iv key data {mode encrypt} } {
	Encrypt or decrypt using DES in CBC mode. Key should be either 64, 128 or 192 bit.
	By default, do encryption. If decryption is desired, specify the last argument as "decrypt".
	iv, key and data are expected as hexadecimal strings
	Result is given as an hexadecimal string
} {
	set iv [::nimp::hexstr_to_bin $iv]
	set key [::nimp::des::key_to_bin $key]
	set data [::nimp::hexstr_to_bin $data]
	string toupper [DES::des -hex -mode cbc -dir $mode -key $key -iv $iv $data]
} test {
	#custom tests
	::nimp::assert_equal 0ADDD58A8533DA86688FB905E332E15882337285BC64CDD225A2545E22E0DE920767A41FD087916657D688974A2DEF80CDDFD38A231E4DC9C839F274CEC6DCD6 [cbc "0001020304050607" "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c" "6bc1bee22e409f96e93d7e117393172a_ae2d8a571e03ac9c9eb76fac45af8e51_30c81c46a35ce411e5fbc1191a0a52ef_f69f2445df4f9b17ad2b417be66c3710"]	
	
	::nimp::assert_equal "6BC1BEE22E409F96E93D7E117393172AAE2D8A571E03AC9C9EB76FAC45AF8E5130C81C46A35CE411E5FBC1191A0A52EFF69F2445DF4F9B17AD2B417BE66C3710" [cbc "0001020304050607" "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c"  0ADDD58A8533DA86688FB905E332E15882337285BC64CDD225A2545E22E0DE920767A41FD087916657D688974A2DEF80CDDFD38A231E4DC9C839F274CEC6DCD6 decrypt]	
}

::nimp::proc+ ::nimp::des::ctr { iv key data } {
	Encrypt or decrypt using DES in CTR mode. Key should be either 64, 128 or 192 bit.
	iv, key and data are expected as hexadecimal strings
	Result is given as an hexadecimal string
} {
	set ctr [::nimp::hexstr_cleanup $iv]
	set key [::nimp::des::key_to_bin $key]
	set plainBlocks [::nimp::str_split [::nimp::hexstr_cleanup $data] 16]
	set out ""
	foreach p $plainBlocks {
		set ib [::nimp::hexstr_to_bin $ctr]
		set ob [DES::des -hex -mode ecb -dir encrypt -key $key $ib]
		set c [::nimp::hexstr_xor $p $ob 16]
		append out $c
		set ctr [::nimp::hexstr_add $ctr 1 16]
		#::nimp::dbg_puts ctr
	}
	string toupper $out
} test {
	#custom tests
	::nimp::assert_equal 5946799AEEFCBC9CA7249E0E8B630D4AC2E970CA9EB5015279B273BE0FD7B7549E902E74C89BB8EEC64EF0FF5C4E86452EDA8DFAD27EEFEA0C6D78067D166D72 [ctr "f0f1f2f3f4f5f6f7" "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c" "6bc1bee22e409f96e93d7e117393172a_ae2d8a571e03ac9c9eb76fac45af8e51_30c81c46a35ce411e5fbc1191a0a52ef_f69f2445df4f9b17ad2b417be66c3710"]	
	
	#decryption
	::nimp::assert_equal "6BC1BEE22E409F96E93D7E117393172AAE2D8A571E03AC9C9EB76FAC45AF8E5130C81C46A35CE411E5FBC1191A0A52EFF69F2445DF4F9B17AD2B417BE66C3710" [ctr "f0f1f2f3f4f5f6f7" "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c" 5946799AEEFCBC9CA7249E0E8B630D4AC2E970CA9EB5015279B273BE0FD7B7549E902E74C89BB8EEC64EF0FF5C4E86452EDA8DFAD27EEFEA0C6D78067D166D72 ]	

	#custom vector to check we survive overflow of ctr
	::nimp::assert_equal D9D3A2DDCDD486036F988E9067A9CAA59659A87C8769D6B359278B49B0744F5D089D5E9295ACD1C76FAD82D61DF79EA9D12D7AD5C0DE5FE80D033078D79BC08C [ctr "ffffffffffffffff" "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c" "6bc1bee22e409f96e93d7e117393172a_ae2d8a571e03ac9c9eb76fac45af8e51_30c81c46a35ce411e5fbc1191a0a52ef_f69f2445df4f9b17ad2b417be66c3710"]	
}

proc ::nimp::des::self_test {} {
	::nimp::show_help_and_test ::nimp::des
}

package provide nimp::des $::nimp::des::version


