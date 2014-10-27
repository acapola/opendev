#ifndef __FTDI_RAW_DATA_SOURCE_H__
#define __FTDI_RAW_DATA_SOURCE__

#include <cstdint>
#include "raw_data_source.h"
#include <windows.h>
#include "ftd2xx_windows.h"
//#define FTDI_BAUDRATE 115200
//#define FTDI_BAUDRATE 921600
//#define FTDI_BAUDRATE 1500000
#define FTDI_BAUDRATE 3000000

class FtdiRawDataSource : public RawDataSource {
	FT_HANDLE ftHandle;
	uint8_t buf[1024];
	int readPos;
public:
	//return false if the device could not be openned
	bool open(uint8_t *deviceId, unsigned int deviceIdLength);
	//return the number of bytes actually received, it should be equal to len, otherwise this indicates an error.
	//if it is less than len, subsequent calls are likely to fail.
	unsigned int read(uint8_t *rxBuffer, unsigned int len);
	void close(void);
};

#endif
