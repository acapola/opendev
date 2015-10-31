
package require aes
package require nimp


namespace eval nimp::aes {
    set version 0.1
}

::nimp::proc+ ::nimp::aes::ecb { key data {mode encrypt} } {
	Encrypt or decrypt using AES in ECB mode. Key should be either 128, 192 or 256 bit.
	By default, do encryption. If decryption is desired, specify the last argument as "decrypt".
	Both key and data are expected as hexadecimal strings
	Result is given as an hexadecimal string
} {
	set key [::nimp::hexstr_to_bin $key]
	set data [::nimp::hexstr_to_bin $data]
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

::nimp::proc+ ::nimp::aes::cbc { iv key data {mode encrypt} } {
	Encrypt or decrypt using AES in CBC mode. Key should be either 128, 192 or 256 bit.
	By default, do encryption. If decryption is desired, specify the last argument as "decrypt".
	iv, key and data are expected as hexadecimal strings
	Result is given as an hexadecimal string
} {
	set iv [::nimp::hexstr_to_bin $iv]
	set key [::nimp::hexstr_to_bin $key]
	set data [::nimp::hexstr_to_bin $data]
	string toupper [aes::aes -hex -mode cbc -dir $mode -key $key -iv $iv $data]
} test {
	#AES CBC test vector from NIST sp800-38a
	::nimp::assert_equal "7649ABAC8119B246CEE98E9B12E9197D5086CB9B507219EE95DB113A917678B273BED6B8E3C1743B7116E69E222295163FF1CAA1681FAC09120ECA307586E1A7" [cbc "000102030405060708090a0b0c0d0e0f" "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c" "6bc1bee22e409f96e93d7e117393172a_ae2d8a571e03ac9c9eb76fac45af8e51_30c81c46a35ce411e5fbc1191a0a52ef_f69f2445df4f9b17ad2b417be66c3710"]	
	
	::nimp::assert_equal "6BC1BEE22E409F96E93D7E117393172AAE2D8A571E03AC9C9EB76FAC45AF8E5130C81C46A35CE411E5FBC1191A0A52EFF69F2445DF4F9B17AD2B417BE66C3710" [cbc "000102030405060708090a0b0c0d0e0f" "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c"  "7649ABAC8119B246CEE98E9B12E9197D5086CB9B507219EE95DB113A917678B273BED6B8E3C1743B7116E69E222295163FF1CAA1681FAC09120ECA307586E1A7" decrypt]	
}

::nimp::proc+ ::nimp::aes::ctr { iv key data } {
	Encrypt or decrypt using AES in CTR mode. Key should be either 128, 192 or 256 bit.
	iv, key and data are expected as hexadecimal strings
	Result is given as an hexadecimal string
} {
	set ctr [::nimp::hexstr_cleanup $iv]
	set key [::nimp::hexstr_to_bin $key]
	set plainBlocks [::nimp::str_split [::nimp::hexstr_cleanup $data] 32]
	set out ""
	foreach p $plainBlocks {
		set ib [::nimp::hexstr_to_bin $ctr]
		set ob [aes::aes -hex -mode ecb -dir encrypt -key $key $ib]
		set c [::nimp::hexstr_xor $p $ob]
		append out $c
		set ctr [::nimp::hexstr_add $ctr 1]
	}
	string toupper $out
} test {
	#AES CBC test vector from NIST sp800-38a
	::nimp::assert_equal "874D6191B620E3261BEF6864990DB6CE9806F66B7970FDFF8617187BB9FFFDFF5AE4DF3EDBD5D35E5B4F09020DB03EAB1E031DDA2FBE03D1792170A0F3009CEE" [ctr "f0f1f2f3f4f5f6f7f8f9fafbfcfdfeff" "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c" "6bc1bee22e409f96e93d7e117393172a_ae2d8a571e03ac9c9eb76fac45af8e51_30c81c46a35ce411e5fbc1191a0a52ef_f69f2445df4f9b17ad2b417be66c3710"]	
	
	#decryption
	::nimp::assert_equal "6BC1BEE22E409F96E93D7E117393172AAE2D8A571E03AC9C9EB76FAC45AF8E5130C81C46A35CE411E5FBC1191A0A52EFF69F2445DF4F9B17AD2B417BE66C3710" [ctr "f0f1f2f3f4f5f6f7f8f9fafbfcfdfeff" "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c" "874D6191B620E3261BEF6864990DB6CE9806F66B7970FDFF8617187BB9FFFDFF5AE4DF3EDBD5D35E5B4F09020DB03EAB1E031DDA2FBE03D1792170A0F3009CEE"]	
}

proc ::nimp::aes::self_test {} {
	::nimp::show_help_and_test ::nimp::aes
}

package provide nimp::aes $::nimp::aes::version


