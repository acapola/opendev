#include "stdio.h"
#include "string.h"
#include "safe_stream.h"
static unsigned int lostBytesDuringOpenCount = -1;
static unsigned int validBytesCount = 0;
static unsigned int lostBytesCount = 0;

static int bufReadPos=0;
static unsigned char buf[PACKET_SIZE];
static unsigned char *bufSof=buf;
static unsigned char *bufDat=bufSof+PACKET_SOF_SIZE;
static unsigned char *bufChk=bufDat+PACKET_DATA_SIZE;

#include "ftdi_raw_data_source.h"
//static TestSafeDataSource src(0);
static FtdiRawDataSource src;

void writeToLogFile(std::string ss);
void writeToLogFile(char *before,uint8_t *buf, unsigned int len, char *after);
#define LOGBUFSIZE (1024*1024)
char logBuf[LOGBUFSIZE];


//return -1 if error
//return 0 or positive number if success
//return value indicate number of bytes lost during the call
static int getSof(unsigned int maxLostBytes){
	int lostBytesCnt=0;
	uint8_t expected=0;
	if(WRITE_LOG) writeToLogFile("\ngetSof\n");
	while(expected<PACKET_SOF_SIZE){
		uint8_t c;
		unsigned int bytesReceived = src.read(&c,1);
		if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"%02X ",c);writeToLogFile(logBuf);}
		if(1!=bytesReceived) {
			if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"FATAL ERROR: bytesReceived = %d, expected 1",bytesReceived);writeToLogFile(logBuf);}
			return -1;
		}
		if(c==expected) expected++;
		else {
			if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE," !! expected: %02X\n",expected);writeToLogFile(logBuf);}
			expected = 0;
			lostBytesCnt++;
			if(lostBytesCnt>=maxLostBytes) return -1;
		}
	}
	return lostBytesCnt;
}

static bool getFrame(bool skipFirstSof){
	bufReadPos = -1;
	unsigned int toSkip = skipFirstSof ? PACKET_SOF_SIZE : 0;
	unsigned int trials;
	for(trials=0;trials<10;trials++){
		if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"INFO: getFrame: toSkip=%d\n",toSkip);writeToLogFile(logBuf);}
		unsigned int bytesReceived = src.read(buf+toSkip,PACKET_SIZE-toSkip);
		if(WRITE_LOG) writeToLogFile("INFO: getFrame:src.read returned:\n",buf+toSkip, bytesReceived, "\n");
		if(PACKET_SIZE-toSkip!=bytesReceived){ 
			if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"FATAL ERROR:getFrame: bytesReceived=%d, expected %d",bytesReceived,PACKET_SIZE-toSkip);writeToLogFile(logBuf);}
			return false;//give up
		}
		bool sofValid=true;
		for(unsigned int i=toSkip;i<PACKET_SOF_SIZE;i++){
			sofValid &= bufSof[i]==i;
		}
		toSkip = 0;
		if(!sofValid){
			lostBytesCount+=bytesReceived;
			int lost=getSof(3*PACKET_SIZE);
			if(-1==lost) {
				if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"FATAL ERROR:getFrame: getSof returned %d",lost);writeToLogFile(logBuf);}
				return false;//give up
			}
			lostBytesCount+=lost;
			bytesReceived = src.read(bufDat,PACKET_DATA_SIZE+PACKET_CHECKSUM_SIZE);
			if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"INFO: getFrame:lostBytesCount=%d\n\tsrc.read after getSof returned:\n",lostBytesCount);writeToLogFile(logBuf,bufDat, bytesReceived, "\n");}
			if((PACKET_DATA_SIZE+PACKET_CHECKSUM_SIZE)!=bytesReceived) {
				if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"FATAL ERROR:getFrame: bytesReceived=%d, expected %d",bytesReceived,(PACKET_DATA_SIZE+PACKET_CHECKSUM_SIZE));writeToLogFile(logBuf);}
				return false;//give up
			}
		}
		//read the checksum
		uint32_t checksum = 0;
		for(unsigned int i=0;i<PACKET_CHECKSUM_SIZE;i++){checksum|=((uint32_t)bufChk[i])<<(8*i);}
		//compute the checksum on the received data
		Crc32Castagnoli crc;
		uint32_t checksum2 = crc.processData(bufDat,PACKET_DATA_SIZE);
		if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"checksum=%08X, checksum2=%08X",checksum,checksum2);writeToLogFile(logBuf);}
		if(checksum==checksum2) {
			if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE," -> Frame accepted\n");writeToLogFile(logBuf);}
			break;//match, validate this packet
		} else {
			if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE," -> Frame rejected\n");writeToLogFile(logBuf);}
		}
		lostBytesCount+=bytesReceived;
		if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"INFO: getFrame:lostBytesCount=%d\n",lostBytesCount);writeToLogFile(logBuf);}
	}
	if(trials==10) {
		if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"FATAL ERROR:getFrame: trials=%d\n",trials);writeToLogFile(logBuf);}
		return false;
	}
	bufReadPos = 0;
	validBytesCount+=PACKET_DATA_SIZE;
	return true;
}

bool open(void){
	bufReadPos = -1;
	src.open(0,0);
	unsigned int lost=getSof(3*PACKET_SIZE);
	if(-1==lost) {
		if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"Could not recognize start of frame pattern\n");writeToLogFile(logBuf);}
		return false;
	}
	lostBytesDuringOpenCount=lost;
	return getFrame(true);
}
unsigned int getLostBytesDuringOpenCount(void){
	return lostBytesDuringOpenCount;
}

//return the number of bytes actually received, it should be equal to len, otherwise this indicates an error.
//if it is less than len, subsequent calls are likely to fail.
unsigned int read(unsigned char *rxBuffer, unsigned int len){
	unsigned int out = 0;
	while(len){
		if(bufReadPos!=-1){
			//consume any buffered data
			unsigned int toReadFromBuf = len;
			unsigned int remaining = PACKET_DATA_SIZE - bufReadPos;
			if(toReadFromBuf>remaining) toReadFromBuf = remaining;
			memcpy(rxBuffer+out,bufDat+bufReadPos,toReadFromBuf);
			out += toReadFromBuf;
			len -= toReadFromBuf;
			bufReadPos = (bufReadPos + toReadFromBuf) % PACKET_DATA_SIZE;
		}		
		if(bufReadPos<=0){
			//get next frame to fill buffer
			if(!getFrame(false)){
				if(WRITE_LOG) {snprintf(logBuf,LOGBUFSIZE,"ERROR: Too many frame rejected");writeToLogFile(logBuf);}
				return out;//give up and return what we received so far
			}
		}
	}
	return out;
}
unsigned int getValidBytesCount(void){
	return validBytesCount;
}
unsigned int getLostBytesCount(void){
	return lostBytesCount;
}
void close(void){
	src.close();
}


/*
static FT_HANDLE ftHandle;

//return -1 if error
//return 0 or positive number if success
//return value indicate number of bytes lost during the call
static unsigned int getSof(unsigned int maxLostBytes){
	unsigned int lostBytesCnt=0;
	unsigned char expected=0;
	while(expected<PACKET_SOF_SIZE){
		unsigned char c;
		unsigned int bytesReceived;
		ftStatus = FT_Read(ftHandle,c,1,&bytesReceived); 
		if(ftStatus!=FT_OK) return -1;
		if(1!=bytesReceived) return -1;
		if(c==expected) expected++;
		else {
			expected = 0;
			lostBytesCnt++;
			if(lostBytesCnt>=maxLostBytes) return -1;
		}
	}
	return lostBytesCnt;
}

bool open(void){
	unsigned int numDevs;
	FT_STATUS ftStatus = FT_ListDevices(&numDevs,NULL,FT_LIST_NUMBER_ONLY); 
	if (ftStatus == FT_OK) { 
		printf("FT_ListDevices OK, number of devices connected is in numDevs=%d\n",numDevs); 
	} else {
		printf("FT_ListDevices failed\n");
		return false;
	}
	if(numDev!=2) {
		printf("expect 2 devices, found %d\n",numDev);
		return false;
	}
	unsigned int deviceId = 1;
	ftStatus = FT_Open(deviceId,&ftHandle);
	if (ftStatus == FT_OK) { 
		printf("FT_Open OK, use ftHandle to access device %d\n",deviceId); 
	} else { 
		printf("FT_Open failed, deviceId=%d: 0x%08X\n",deviceId,ftStatus);
		return false;
	}
	FT_SetTimeouts(ftHandle,5,0);//5ms timeout
	ftStatus = FT_SetBaudRate(ftHandle, 19200); // Set baud rate
	if (ftStatus == FT_OK) { 
		printf("FT_SetBaudRate OK\n"); 
	} else { 
		printf("FT_SetBaudRate Failed: 0x%08X\n",ftStatus);
		return false;
	}
	unsigned int lost=getSof(3*PACKET_SIZE);
	if(-1==lost) {
		printf("Could not recognize start of frame pattern\n");
		return false;
	}
	lostBytesDuringOpenCount+=lost;
	
}
unsigned int getLostBytesDuringOpenCount(void);
//return the number of bytes actually received, it should be equal to len, otherwise this indicates an error.
//if it is less than len, subsequent calls are likely to fail.
unsigned int read(unsigned char *rxBuffer, unsigned int len);
unsigned int getValidPacketsCount(void);
unsigned int getLostPacketsCount(void);
void close(void){
	FT_Close (ftHandle);
}
*/