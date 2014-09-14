#ifndef __RANDOM_H__
#define __RANDOM_H__

#ifndef __U64_DEFINED__
#define __U64_DEFINED__
typedef long unsigned int u64;
#endif

#ifndef __U32_DEFINED__
#define __U32_DEFINED__
typedef unsigned int u32;
#endif


typedef struct random_struct_t {
	u64 seed;
	u64 r1;
	u64 r2;
	u32 buf;
	u32 cnt;
} random_t;

void setSeed(random_t *prng, u64 seed);
void reset(random_t *prng);
u32 getBits32(random_t *prng);
u64 getBits64(random_t *prng);

#endif //__RANDOM_H__