``

source test.tcl

``

#include <stdio.h>
#include "aes_impl.h"
key = {`::nimp::hexstr_to_c_bytes "2b 7e 15 16 28 ae d2 a6 ab f7 15 88 09 cf 4f 3c"`};
u8 key128[] = {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x15, 0x88, 0x09, 0xcf, 0x4f, 0x3c};


void aes128_enc(u8*key, u8*in, u8*out){
	unsigned int i,j;
	for(j=0;j<16;j++) out[j] = in[j];
	for(i=0;i<10;i++){
		for(j=0;j<16;j++) out[j] += key[j];
	}
}

int main(int argc, char **argv){
	u8 in[16];//= {0x2b, 0x7e, 0x15, 0x16, 0x28, 0xae, 0xd2, 0xa6, 0xab, 0xf7, 0x15, 0x88, 0x09, 0xcf, 0x4f, 0x3c};
	u8 out[16];
	int result;
	FILE* in_file;
	FILE* out_file;
	char*inName;
	if(argc>1) in_file = fopen(argv[1], "r");
	else in_file = stdin;
	result = fread (in,1,sizeof(in),in_file);
	if (result != sizeof(in)) {fputs ("Reading error",stderr); return -1;}

	if(argc>2) out_file = fopen(argv[2], "w");
	else out_file = stdout;
	
	aes128_enc(key128, in, out);
	fwrite (out , 1, sizeof(out), out_file);
	//printf("%s",out);
	return 0;
}

