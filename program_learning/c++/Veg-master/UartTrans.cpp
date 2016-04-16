/***********************************************************************
 * Module:  UartTrans.cpp
 * Author:  Thinkpad
 * Modified: 2015-04-29 14:14:02
 * Purpose: Implementation of the class UartTrans
 * Comment: 串口操作类
 ***********************************************************************/

#include <iostream>
#include "UartTrans.h"
#include <string>
#include <cstring>

////////////////////////////////////////////////////////////////////////
// Name:       UartOpen
// Purpose:    Implementation of UartTrans::UartOpen()
// Comment:    打开串口
// Parameters: 
// [in] UartNo 		串口号
// [in] baudRate	波特率
// [in/out] errStr  错误信息
// Return:     int  1-成功 0-失败
////////////////////////////////////////////////////////////////////////

//int UartTrans::UartOpen(int UartNo, int baudRate, std::string &errStr)
//{
   // TODO : implement
//}

////////////////////////////////////////////////////////////////////////
// Name:       UartTrans::UartSend(int buf, int len, std::string errStr)
// Purpose:    Implementation of UartTrans::UartSend()
// Comment:    串口发送
// Parameters:
// [in] buf    		发送数据指针
// [in] len			发送数据长度
// [in/out] errStr	错误信息
// Return:     int  1-成功 0-失败
////////////////////////////////////////////////////////////////////////

//int UartTrans::UartSend(char *buf, int len, std::string &errStr)
//{
   // TODO : implement
//}

////////////////////////////////////////////////////////////////////////
// Name:       UartTrans::UartRecevie(char* buf, int len, std::string errStr)
// Purpose:    Implementation of UartTrans::UartRecevie()
// Comment:    串口接收
// Parameters:
// [in/out] buf		接收数据地址
// [in/out] len		接收数据的长度
// [in/out] errStr	错误信息
// Return:     int  1-成功 0-失败
////////////////////////////////////////////////////////////////////////

//int UartTrans::UartRecevie(char *buf, int len, std::string &errStr)
//{
   // TODO : implement
//}

////////////////////////////////////////////////////////////////////////
// Name:       UartTrans::UartColse(std::string errStr)
// Purpose:    Implementation of UartTrans::UartColse()
// Comment:    关闭串口
// Parameters:
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

//int UartTrans::UartColse(std::string &errStr)
//{
   // TODO : implement
//}

int main()
{
	using std::string;
	using std::cout;
	using std::endl;
	string errStr;
	UartTrans uart;
	if (uart.UartOpen(1, 115200, errStr) == 0)
	{
		cout << errStr << endl;
		return -1;
	}
	char* buf = new char;
	strcpy(buf,"I make it!");
	int len = strlen(buf);
	if (uart.UartSend(buf, len, errStr) == 0)
	{
		cout << errStr << endl;
		return -1;
	}
	while(1)
	{
		if (uart.UartRecevie(buf, len, errStr) == 0)
		{
			cout << errStr << endl;
			return -1;
		}
		if (uart.UartSend(buf,len,errStr) == 0)
		{
			cout << errStr << endl;
			return -1;
		}
	}
	if (uart.UartColse(errStr) == 0)
	{
		cout << errStr << endl;
		return -1;
	}
	delete(buf);
	return 0;
}
