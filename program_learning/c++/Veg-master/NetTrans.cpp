/***********************************************************************
 * Module:  NetTrans.cpp
 * Author:  Thinkpad
 * Modified: 2015-04-29 13:49:24
 * Purpose: Implementation of the class NetTrans
 * Comment: 网络通信类，负责连接后台、发送数据、接收数据、断开连接
 ***********************************************************************/

#include "NetTrans.h"

////////////////////////////////////////////////////////////////////////
// Name:       NetTrans::NetConnect(std::string IP, std::string port, std::string errStr)
// Purpose:    Implementation of NetTrans::NetConnect()
// Comment:    连接服务器
// Parameters:
// [in] IP 		服务器IP
// [in] port 	服务器Port
// [in] path	服务器路径
// [in/out] errStr 错误信息
// Return:     int  1-成功 0-失败
////////////////////////////////////////////////////////////////////////

int NetTrans::NetConnect(string IP, string port, string path, &string errStr)
{
   // TODO : implement
}

////////////////////////////////////////////////////////////////////////
// Name:       NetTrans::NetSend(char buf, int len, std::string errStr)
// Purpose:    Implementation of NetTrans::NetSend()
// Comment:    发送数据到后台
// Parameters:
// [in] buf  发送数据指针
// [in] len	 发送数据长度
// [in/out] errStr 错误信息
// Return:     int  1-成功 0-失败
////////////////////////////////////////////////////////////////////////

int NetTrans::NetSend(char *buf, int len, string &errStr)
{
   // TODO : implement
}

////////////////////////////////////////////////////////////////////////
// Name:       NetTrans::NetRecevie(char buf, int len, int timeout, std::string errStr)
// Purpose:    Implementation of NetTrans::NetRecevie()
// Comment:    从后台接收数据，非阻塞，如果在timeout时间内没有数据传来就退出
// Parameters:
// [in/out] buf 接收数据指针
// [in/out] len 接收数据长度
// [in] timeout 超时时间 毫秒
// [in/out] errStr 错误信息
// Return:     int  1-成功 0-失败
////////////////////////////////////////////////////////////////////////

int NetTrans::NetRecevie(char *buf, int &len, int timeout, string &errStr)
{
   // TODO : implement
}

////////////////////////////////////////////////////////////////////////
// Name:       NetTrans::NetClose(std::string errStr)
// Purpose:    Implementation of NetTrans::NetClose()
// Comment:    关闭连接
// Parameters:
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int NetTrans::NetClose(std::string errStr)
{
   // TODO : implement
}