#include <stdio.h>
#include "random.h"

void print64(u64 data){
	printf("%08X%08X\n",(unsigned int) (data>>32),(unsigned int) (data & 0xFFFFFFFF));
}

int main(int argc, char **argv) {
	unsigned int i;
	random_t prng;
	setSeed(&prng,0L);
	reset(&prng);
	
	for(i=0;i<200;i++){
		printf("%01X",step(&prng));
	}
	printf("\n");
	for(i=0;i<10;i++){
		print64(getBits64(&prng,64));
	}
	return 0;
}
