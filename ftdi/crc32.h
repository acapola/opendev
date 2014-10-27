#ifndef __CRC32_H__
#define __CRC32_H__

#include <cstdint>

class Crc32Interface {
public:
	virtual void reset(void) = 0;
	virtual void setCrc(uint32_t state) = 0;
	virtual uint32_t getCrc(void) = 0;
	virtual uint32_t processData(uint8_t *in, unsigned int len) = 0;
};

class Crc32Base: public Crc32Interface {
protected:
	uint32_t crc;
	uint32_t initialValue;
public:
	Crc32Base(){
		initialValue = 0xFFFFFFFF;
		reset();
	}
	Crc32Base(uint32_t _initialValue){
		initialValue = _initialValue;	
		reset();
	}
	void reset(void){
		crc = initialValue;
	}
	void setCrc(uint32_t state){
		crc = state;
	}
	uint32_t getCrc(void){
		return crc;
	}
	//unsigned int processData(unsigned char *in, unsigned int len) = 0;
};
class Crc32Castagnoli: public Crc32Base {
public:
	Crc32Castagnoli();
	uint32_t processData(uint8_t *in, unsigned int len);
};

#endif
