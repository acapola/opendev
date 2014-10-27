
#include "ftdi_raw_data_source.h"
#include "stdio.h"


#define BUF_USABLE_SIZE (sizeof(buf))

//return false if the device could not be openned
bool FtdiRawDataSource::open(uint8_t *deviceId, unsigned int deviceIdLength){
	readPos=-1;
	unsigned int numDevs;
	FT_STATUS ftStatus = FT_ListDevices(&numDevs,NULL,FT_LIST_NUMBER_ONLY); 
	if (ftStatus == FT_OK) { 
		printf("FT_ListDevices OK, number of devices connected is in numDevs=%d\n",numDevs); 
	} else {
		printf("FT_ListDevices failed\n");
		return false;
	}
	if(numDevs!=2) {
		printf("expect 2 devices, found %d\n",numDevs);
		return false;
	}
	unsigned int id = 1;
	ftStatus = FT_Open(id,&ftHandle);
	if (ftStatus == FT_OK) { 
		printf("FT_Open OK, use ftHandle to access device %d\n",id); 
	} else { 
		printf("FT_Open failed, device id=%d: 0x%08X\n",id,ftStatus);
		return false;
	}
	FT_SetTimeouts(ftHandle,5000,0);//5000ms timeout
	ftStatus = FT_SetBaudRate(ftHandle, FTDI_BAUDRATE); // Set baud rate
	//ftStatus = FT_SetBaudRate(ftHandle, 800000); // Set baud rate
	if (ftStatus == FT_OK) { 
		printf("FT_SetBaudRate OK\n"); 
	} else { 
		printf("FT_SetBaudRate Failed: 0x%08X\n",ftStatus);
		return false;
	}
	// Set RTS/CTS flow control 
	ftStatus = FT_SetFlowControl(ftHandle, FT_FLOW_RTS_CTS, 0x11, 0x13); 
	if (ftStatus == FT_OK) { 
		printf("FT_SetFlowControl OK\n");  
	} else { 
		printf("FT_SetFlowControl Failed\n");  
	} 
	ftStatus = FT_Purge(ftHandle, FT_PURGE_RX | FT_PURGE_TX); // Purge both Rx and Tx buffers 
	if (ftStatus == FT_OK) { 
		printf("FT_Purge OK\n");   
	} else { 
		printf("FT_Purge failed\n");   
	}
	readPos=BUF_USABLE_SIZE;//buf is empty
	return true;
}
//return the number of bytes actually received, it should be equal to len, otherwise this indicates an error.
//if it is less than len, subsequent calls are likely to fail.

unsigned int FtdiRawDataSource::read(uint8_t *rxBuffer, unsigned int len){
	/*long unsigned int bytesReceived;
	FT_STATUS ftStatus;
	printf("hit key\n");getchar();
	ftStatus = FT_Read(ftHandle,rxBuffer,len,&bytesReceived); 
	if(ftStatus!=FT_OK) return -1;
	return (unsigned int)bytesReceived;*/
	if(readPos==-1) 
		return -1;
	unsigned int out=0;
	while(len){
		if(readPos==BUF_USABLE_SIZE){
			readPos=-1;
			FT_STATUS ftStatus;
			long unsigned int cnt;
			ftStatus = FT_Read(ftHandle,buf,BUF_USABLE_SIZE,&cnt); 
			if(ftStatus!=FT_OK) 
				return -1;
			if(cnt!=BUF_USABLE_SIZE) 
				return -1;
			readPos = 0;
		} 
		unsigned int remaining = BUF_USABLE_SIZE - readPos;
		unsigned int toRead = remaining;
		if(toRead > len) toRead = len;
		memcpy(rxBuffer+out,buf+readPos,toRead);
		readPos +=toRead;
		len-=toRead;
		out+=toRead;
	}
	return out;
}
void FtdiRawDataSource::close(void){
	FT_Close (ftHandle);
}
