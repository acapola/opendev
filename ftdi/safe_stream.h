#ifndef __SAFE_STREAM_H__
#define __SAFE_STREAM_H__
#include "raw_data_source.h"
#include "crc32.h"

#define WRITE_LOG 0

#include <random>
#include <algorithm>

#define PACKET_SOF_SIZE 4 
#define PACKET_DATA_SIZE 128
#define PACKET_CHECKSUM_SIZE 4
#define PACKET_SIZE (PACKET_SOF_SIZE+PACKET_DATA_SIZE+PACKET_CHECKSUM_SIZE)

//return false if the device could not be openned
bool open(void);
unsigned int getLostBytesDuringOpenCount(void);
//return the number of bytes actually received, it should be equal to len, otherwise this indicates an error.
//if it is less than len, subsequent calls are likely to fail.
unsigned int read(uint8_t *rxBuffer, unsigned int len);
unsigned int getValidBytesCount(void);
unsigned int getLostBytesCount(void);
void close(void);

class TestSafeDataSource: public RawDataSource {
	TestRawDataSource src;
	unsigned int readPos;
	std::random_device rd;
	std::uniform_int_distribution<uint32_t> *unidist;//(0, PACKET_SIZE);
	Crc32Castagnoli crc;
	uint8_t buf[PACKET_SIZE];
	uint8_t *bufSof;
	uint8_t *bufDat;
	uint8_t *bufChk;
	unsigned int errorPercent;
	void refill(){
		for(unsigned int i=0;i<PACKET_SOF_SIZE;i++) bufSof[i]=i;
		src.read(bufDat,PACKET_DATA_SIZE);
		crc.reset();
		unsigned int chk = crc.processData(bufDat,PACKET_DATA_SIZE);
		for(unsigned int i=0;i<4;i++) bufChk[i] = (unsigned char)((chk >> (8*i)) & 0xFF); 
		
		std::uniform_int_distribution<unsigned int> dis(0, 100);
		if(dis(rd)<errorPercent) {//mess up the data to simulate noise between sender and receiver
			//inject a byte error somewhere in the frame
			std::uniform_int_distribution<unsigned int> disErrorLoc(0, PACKET_SIZE-1);
			unsigned int errorLoc = disErrorLoc(rd);
			buf[errorLoc] = ~buf[errorLoc];
			crc.reset();
			unsigned int chk2 = crc.processData(bufDat,PACKET_DATA_SIZE);
			printf("error injected at position %d: %02X. chk=%08X, chk2=%08X\n",errorLoc,buf[errorLoc],chk,chk2);
			if(chk==chk2){
				printf("FATAL ERROR: injected error not detected by checksum.");
				exit(-1);
			}
		}
		
		readPos = 0;
	}
public:
	TestSafeDataSource(unsigned int _errorPercent){
		errorPercent = _errorPercent;
		bufSof = buf;
		bufDat = bufSof+PACKET_SOF_SIZE;
		bufChk = bufDat+PACKET_DATA_SIZE;
		refill();
		std::uniform_int_distribution<unsigned int> dis100(0, 100);
		if(dis100(rd)<errorPercent) {//mess up the data to simulate noise between sender and receiver
			std::uniform_int_distribution<unsigned int> dis(1, PACKET_SIZE);
			readPos = dis(rd);//mess up the read logic to simulate loss of sync between sender and receiver
		}
	}
	//return false if the device could not be openned
	bool open(uint8_t *deviceId, unsigned int deviceIdLength){return true;};
	//return the number of bytes actually received, it should be equal to len, otherwise this indicates an error.
	//if it is less than len, subsequent calls are likely to fail.
	unsigned int read(uint8_t *rxBuffer, unsigned int len){
		unsigned int out=0;
		while(len){
			unsigned int remaining = PACKET_SIZE - readPos;
			unsigned int toRead = remaining;
			if(toRead > len) toRead = len;
			memcpy(rxBuffer,buf+readPos,toRead);
			readPos +=toRead;
			len-=toRead;
			out+=toRead;
			if(readPos==PACKET_SIZE) refill();
		}
		return out;
	};
	void close(void){};
};

#endif