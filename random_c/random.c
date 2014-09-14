/*
 * Pseudo Random Number Generator using home made structure
 */

#include "random.h"

//    variable tapsMask1 [lfsrExponentsToMask 64  4  3 1 0]
//    variable tapsMask2 [lfsrExponentsToMask 63       1 0]
static const u64 TAPSMASK1 = 0x800000000000000DL;
static const u64 TAPSMASK2 = 0x4000000000000001L;
static const unsigned int TAPSMASK1_WIDTH = 64;
static const unsigned int TAPSMASK2_WIDTH = 63;

void setSeed(random_t *prng, u64 seed) {
	prng->seed = seed;
	reset(prng);
}
void reset(random_t *prng) {
	u64 seed = prng->seed;
	prng->cnt=0;
	prng->r1 = seed^0xEC6754ABB8A213CDL;prng->r1 = prng->r1 ? prng->r1 : 1;
	prng->r2 = seed^0x2A1234568304FC34L;prng->r2 = prng->r2 ? prng->r2 : 1;
}

//Galois LFSR step
static u64 lfsrStep(u64 state, u64 tapsMask) {
	u64 out = state>>1;
	if(state & 1){
		out = out ^ tapsMask;
	}
	//printf("	%08X%08X\n",(unsigned int) (state>>32),(unsigned int) (state & 0xFFFFFFFF));
	return out;
}

unsigned int step(random_t *prng) {
	u32 buf;
	unsigned int out;
	if(0==prng->cnt) {
		prng->cnt=31;
		buf = getBits32(prng);
	}else{
		prng->cnt--;
		buf = prng->buf;
	} 
	out = (buf>>31) & 1;
	prng->buf = buf<<1;
	return out;
}

u32 getBits32(random_t *prng){
	unsigned int i;
	u32 invR2;
	unsigned int step1 = (prng->r2 >> (TAPSMASK2_WIDTH-1)) ^ (1 & (prng->r2 >> (TAPSMASK2_WIDTH-2)));
	unsigned int step2 = (prng->r1 >> (TAPSMASK1_WIDTH-1)) ^ (1 & (prng->r1 >> (TAPSMASK1_WIDTH-2)));
	if(!(step1 | step2)) {
		step1=1;
		step2=1;
	}
	for(i=0;i<32;i++){
		if(step1) prng->r1 = lfsrStep(prng->r1,TAPSMASK1);
		if(step2) prng->r2 = lfsrStep(prng->r2,TAPSMASK2);
	}
	for(i=0;i<32;i++){
		invR2 = (invR2<<1) | ((prng->r2>>i) & 1);
	}
	return 0xFFFFFFFF & (prng->r1 ^ invR2);
}
u64 getBits64(random_t *prng){
	u64 out=0;
	out = getBits32(prng);
	out = (out<<32) | getBits32(prng);
	return out;
}
