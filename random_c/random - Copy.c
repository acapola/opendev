/*
 * Pseudo Random Number Generator using the "alternative step generator" structure
 * See Algorithm 6.57 of Handbook of applied Crypto by Menezes, Oorschot and Vandstone
 */

#include "random.h"

//    variable tapsMask1 [lfsrExponentsToMask 64  4  3 1 0]
//    variable tapsMask2 [lfsrExponentsToMask 61 16 15 1 0]
//    variable tapsMask3 [lfsrExponentsToMask 63       1 0]
static const u64 TAPSMASK1 = 0x800000000000000DL;
static const u64 TAPSMASK2 = 0x100000000000C001L;
static const u64 TAPSMASK3 = 0x4000000000000001L;

void setSeed(random_t *prng, u64 seed) {
	prng->seed = seed;
	reset(prng);
}
void reset(random_t *prng) {
	u64 seed = prng->seed;
	prng->r1 = seed^0xB8A213CDL;prng->r1 = prng->r1 ? prng->r1 : 1;
	prng->r2 = seed^0xDA123456L;prng->r2 = prng->r2 ? prng->r2 : 1;
	prng->r3 = seed^0xECE6B896L;prng->r3 = prng->r3 ? prng->r3 : 1;
}

//Galois LFSR step
static u64 lfsrStep(u64 state, u64 tapsMask) {
	u64 out = state>>1;
	if(state & 1){
		out = out ^ tapsMask;
	}
	return out;
}

unsigned int step(random_t *prng) {
	prng->r1 = lfsrStep(prng->r1,TAPSMASK1);
	if(prng->r1 & 1) {
		prng->r2 = lfsrStep(prng->r2,TAPSMASK2);
	} else {
	    prng->r3 = lfsrStep(prng->r3,TAPSMASK3);
	}
	return (prng->r2 & 1) ^ (prng->r3 & 1);
}
u64 getBits64(random_t *prng, unsigned int width){
	u64 out=0;
	unsigned int i;
	for(i=0;i<width;i++) {	
	    out = (out << 1) | step(prng);
	}
	return out;
}
