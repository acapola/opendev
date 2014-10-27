#include "crc32.h"
//plain C version:

#define POLY 0x82f63b78
//#define POLY 0x1EDC6F41
//from Hacker's delight by Warren
void crc32CastagnoliInitTable(uint32_t *table){
	int j;
	uint32_t byte,mask;
	uint32_t tmp;
	for(byte = 0;byte <= 255; byte++){
		tmp = byte;
		for(j = 7; j>=0; j--){
			mask = -(tmp & 1);
			tmp = (tmp >> 1) ^ (POLY & mask);
		}
		table[byte] = tmp;
	}
}

uint32_t crc32(uint32_t *table,uint8_t *in, unsigned int len, uint32_t crc){
	unsigned int i;
	uint32_t byte;
	i=0;
	while(i!=len){
		byte=in[i++];
		crc = (crc>>8) ^ table[(crc ^ byte) & 0xFF];
	}
	return crc;
}

static uint32_t castagnoliTable[256];
static bool castagnoliTableInitialized = false;

//C++ wrapper
Crc32Castagnoli::Crc32Castagnoli(){
	if(!castagnoliTableInitialized) {
		crc32CastagnoliInitTable(castagnoliTable);
		castagnoliTableInitialized = true;//thread safe
	}
}

unsigned int Crc32Castagnoli::processData(uint8_t *in, unsigned int len){
	crc = crc32(castagnoliTable,in,len,crc);
	return crc;
}
