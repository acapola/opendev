//#include <windows.h>
#include "wintypes.h"
#include <stdio.h>
#include "ftd2xx.h"

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
	DWORD RxBytes = 256; 
	DWORD BytesReceived; 
	char RxBuffer[256];
	ftStatus = FT_Open(deviceId,&ftHandle);
	if (ftStatus == FT_OK) { 
		printf("FT_Open OK, use ftHandle to access device %d\n",deviceId); 
	} else { 
		printf("FT_Open failed, deviceId=%d: 0x%08X\n",deviceId,ftStatus);
		return;
	}
	FT_SetTimeouts(ftHandle,5000,0);
	ftStatus = FT_SetBaudRate(ftHandle, 19200); // Set baud rate
	if (ftStatus == FT_OK) { 
		printf("FT_SetBaudRate OK\n"); 
	} else { 
		printf("FT_SetBaudRate Failed: 0x%08X\n",ftStatus);
	} 
	ftStatus = FT_Read(ftHandle,RxBuffer,RxBytes,&BytesReceived); 
	if (ftStatus == FT_OK) { 
		if (BytesReceived == RxBytes) {
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

int main(int argc, char *argv[]){
    test();
	listenToDevice(0);
	listenToDevice(1);
	printf("press any key to exit\n");
	//getchar();
}