#ifndef __RAW_DATA_SOURCE_H__
#define __RAW_DATA_SOURCE_H__

#include <cstdint>

class RawDataSource {
  
public:
	//return false if the device could not be openned
	virtual bool open(uint8_t *deviceId, unsigned int deviceIdLength) = 0;
	//return the number of bytes actually received, it should be equal to len, otherwise this indicates an error.
	//if it is less than len, subsequent calls are likely to fail.
	virtual unsigned int read(uint8_t *rxBuffer, unsigned int len) = 0;
	virtual void close(void) = 0;
};


class TestRawDataSource : public RawDataSource {
	uint32_t cnt;
	unsigned int shift;
public:
	TestRawDataSource(){
		cnt = 0x00147F60;
		shift = 0;
	}
	//return false if the device could not be openned
	bool open(uint8_t *deviceId, unsigned int deviceIdLength){return true;};
	//return the number of bytes actually received, it should be equal to len, otherwise this indicates an error.
	//if it is less than len, subsequent calls are likely to fail.
	unsigned int read(uint8_t *rxBuffer, unsigned int len){
		unsigned int out = 0;
		unsigned int offset;
		for(unsigned int i=0;i<len;i+=sizeof(cnt)){
			for(unsigned int j=0;j<sizeof(cnt);j++){
				offset = i+j;
				rxBuffer[offset] = (cnt>>(8*shift)) & 0xFF;
				shift = (shift+1) %sizeof(cnt);
				out++;
				if(out==len) return len;
			} 
			cnt++;
		}
		return len;
	};
	void close(void){};
};
#endif