
#include <sstream>  
#include <string> 
#include <iostream>
#include <fstream> 
#define LOG_FILE "ftdi.log"
std::string datFileName = "icestick.dat";
void clearFile(std::string fileName){
	using namespace std;  
    ofstream myfile;  
    myfile.open (fileName);  
    myfile.close(); 
}
void clearLogFile(void){
	clearFile("ftdi.log"); 
}
void writeToLogFile(std::string ss){  
    using namespace std;  
    ofstream myfile;  
    myfile.open (LOG_FILE, ios_base::app);  
    myfile << ss;  
    myfile.close();  
}

void writeToLogFile(std::stringstream& ss){  
    using namespace std;  
    string myString = ss.str();  
    writeToLogFile(myString);
}
void writeToLogFile(char *before,uint8_t *buf, unsigned int len, char *after){
	using namespace std;  
    ofstream myfile;  
    myfile.open (LOG_FILE, ios_base::app);  
    myfile << before;
	char hexByte[4];
	for(unsigned int i=0;i<len;i++){
		snprintf(hexByte,4,"%02X ",buf[i]);
		myfile << hexByte;
		if(i%16 == 15) myfile << endl;
	}
    myfile << after;  
    myfile.close(); 
}
void appendToBinaryFile(const uint8_t* buffer,unsigned long size, std::string fileName){
	std::ofstream outfile (fileName.c_str(),std::ofstream::binary|std::ios_base::app);
	outfile.write ((const char *)buffer,size);
	outfile.close();
}

#include <windows.h>
//#include "wintypes.h"
//#include <stdio.h>
#include "ftd2xx_windows.h"

#include "safe_stream.h"
#include "ftdi_raw_data_source.h"


void test(void){
	unsigned int numDevs;
	FT_STATUS ftStatus = FT_ListDevices(&numDevs,NULL,FT_LIST_NUMBER_ONLY); 
	if (ftStatus == FT_OK) { 
		printf("FT_ListDevices OK, number of devices connected is in numDevs=%d\n",numDevs); 
	} else {
		printf("FT_ListDevices failed\n"); 
	}
	char *BufPtrs[3]; // pointer to array of 3 pointers 
	char Buffer1[64]; // buffer for description of first device 
	char Buffer2[64]; // buffer for description of second device 
	// initialize the array of pointers 
	BufPtrs[0] = Buffer1; BufPtrs[1] = Buffer2; BufPtrs[2] = NULL; // last entry should be NULL
	ftStatus = FT_ListDevices(BufPtrs,&numDevs,FT_LIST_ALL|FT_OPEN_BY_DESCRIPTION); 
	if (ftStatus == FT_OK) { 
		printf("FT_ListDevices OK, product descriptions are in Buffer1 and Buffer2, and numDevs contains the number of devices connected\n");
		printf(Buffer1);
		printf("\n");
		printf(Buffer2);
		printf("\n");
	} else { 
		printf("FT_ListDevices failed\n");  
	}
}
void listenToDevice(unsigned int deviceId){
	FT_HANDLE ftHandle; 
	FT_STATUS ftStatus; 
	uint8_t RxBuffer[2*1024];
	DWORD RxBytes = sizeof(RxBuffer); 
	DWORD BytesReceived; 
	ftStatus = FT_Open(deviceId,&ftHandle);
	if (ftStatus == FT_OK) { 
		printf("FT_Open OK, use ftHandle to access device %d\n",deviceId); 
	} else { 
		printf("FT_Open failed, deviceId=%d: 0x%08X\n",deviceId,ftStatus);
		return;
	}
	FT_SetTimeouts(ftHandle,5000,0);
	ftStatus = FT_SetBaudRate(ftHandle, FTDI_BAUDRATE); // Set baud rate
	//ftStatus = FT_SetBaudRate(ftHandle, 921600);
	//ftStatus = FT_SetDivisor(ftHandle, 0x0380); // Set baud rate
	//ftStatus = FT_SetDivisor(ftHandle, 0x0400); // Set baud rate
	if (ftStatus == FT_OK) { 
		printf("FT_SetBaudRate OK\n"); 
	} else { 
		printf("FT_SetBaudRate Failed: 0x%08X\n",ftStatus);
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
	ftStatus = FT_Read(ftHandle,RxBuffer,16,&BytesReceived); 
	if (ftStatus == FT_OK) { 
		if (BytesReceived == 16) {
			printf("FT_Read OK, %d bytes received\n",BytesReceived); 
		} else {
			printf("FT_Read Timeout, %d bytes received\n",BytesReceived);
		}
		for(unsigned int i=0;i<BytesReceived;i++) {
			printf("%02X ",0x0FF & RxBuffer[i]);
			if(i%16 == 15) printf("\n");
		}
		printf("\n");
	} else { 
		printf("FT_Read Failed: 0x%08X\n",ftStatus); 
	}
	printf("press enter to receive next bytes\n");
	getchar();
	ftStatus = FT_Read(ftHandle,RxBuffer,RxBytes-16,&BytesReceived); 
	if (ftStatus == FT_OK) { 
		if (BytesReceived == RxBytes-16) {
			printf("FT_Read OK, %d bytes received\n",BytesReceived); 
		} else {
			printf("FT_Read Timeout, %d bytes received\n",BytesReceived);
		}
		for(unsigned int i=0;i<BytesReceived;i++) {
			printf("%02X ",0x0FF & RxBuffer[i]);
			if(i%16 == 15) printf("\n");
		}
		printf("\n");
	} else { 
		printf("FT_Read Failed: 0x%08X\n",ftStatus); 
	}
	FT_Close (ftHandle);
}
void printHex(const char *before, unsigned char *buf,unsigned int len, const char *after){
	printf(before);
	for(unsigned int i=0;i<len;i++) {
		printf("%02X ",0x0FF & buf[i]);
		if(i%16 == 15) printf("\n");
	}
	printf(after);
}

void makeCrcTestVector(void){
	Crc32Castagnoli crc;
	
	printf("init: %08X\n",crc.getCrc());
	for(uint8_t i = 0;i<10;i++){
		crc.processData(&i,1);
		printf("tv[i] = 40'h%02X_%08X;i=i+1;\n",i,crc.getCrc());	
		crc.processData(&i,1);
		printf("tv[i] = 40'h%02X_%08X;i=i+1;\n",i,crc.getCrc());	
		crc.processData(&i,1);
		printf("tv[i] = 40'h%02X_%08X;i=i+1;\n",i,crc.getCrc());	
	}
	crc.reset();
	printf("%08X",crc.processData((uint8_t*)"0",1));
}

void checkCom(void){
	if(!open()){
		printf("open fail\n");
	}
	
	uint8_t rxBuf[1024*1024];
	while(true){
		unsigned int bytesReceived = read(rxBuf,sizeof(rxBuf));
		if(bytesReceived!=sizeof(rxBuf)){
			printf("ERROR: bytesReceived = %d\n", bytesReceived);
		} else {
			printf("SUCCESS: bytesReceived = %d\n", bytesReceived);
		}
		printf("%d valid Mbytes, %d lost bytes, %d lost during open\n",getValidBytesCount()/(1024*1024), getLostBytesCount(), getLostBytesDuringOpenCount());
		clearFile(datFileName);
		appendToBinaryFile(rxBuf,bytesReceived,datFileName);
	}
	close();
}

void gatherTestData(unsigned int megaBytes){
	if(!open()){
		printf("open fail\n");
	}
	clearFile(datFileName);
	uint8_t rxBuf[1024*1024];
	for(unsigned int i=0;i<megaBytes;i++){
		unsigned int bytesReceived = read(rxBuf,sizeof(rxBuf));
		if(bytesReceived!=sizeof(rxBuf)){
			printf("ERROR: bytesReceived = %d\n", bytesReceived);
		} else {
			printf("SUCCESS: bytesReceived = %d\n", bytesReceived);
		}
		printf("%d Mbytes, %d lost bytes, %d lost during open\n",getValidBytesCount()/(1024*1024), getLostBytesCount(), getLostBytesDuringOpenCount());
		appendToBinaryFile(rxBuf,bytesReceived,datFileName);
	}
	close();
	printf("press any key to continue\n");getchar();
}

int main(int argc, char *argv[]){
	if(WRITE_LOG) clearLogFile();
    //test();
	//listenToDevice(0);
	//listenToDevice(1);printf("press any key to continue\n");getchar();
	unsigned int dataSize = 100;
	if(argc>=2) dataSize = atoi(argv[1]);
	
	gatherTestData(dataSize);
	//checkCom();
	return 0;
	if(!open()){
		printf("open fail\n");
	}
	printf("press any key to continue\n");getchar();
	
	uint8_t rxBuf[1000];
	unsigned int bytesReceived = read(rxBuf,sizeof(rxBuf));
	if(bytesReceived!=sizeof(rxBuf)){
		printf("ERROR: bytesReceived = %d\n", bytesReceived);
	} else {
		printf("SUCCESS: bytesReceived = %d\n", bytesReceived);
	}
	close();
	printHex("bytes read: \n",rxBuf,sizeof(rxBuf),"\n");
	printf("%d valid bytes, %d lost bytes, %d lost during open\n",getValidBytesCount(), getLostBytesCount(), getLostBytesDuringOpenCount());
	//makeCrcTestVector();
	printf("press any key to exit\n");
	getchar();
}