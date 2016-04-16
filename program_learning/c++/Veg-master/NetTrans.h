/***********************************************************************
 * Module:  NetTrans.h
 * Author:  Thinkpad
 * Modified: 2015-04-29 13:49:24
 * Purpose: Declaration of the class NetTrans
 * Comment: 网络通信类，负责连接后台、发送数据、接收数据、断开连接
 ***********************************************************************/

#if !defined(__Veg_NetTrans_h)
#define __Veg_NetTrans_h

class NetTrans
{
public:
   /* 连接服务器 */
   int NetConnect(std::string IP, std::string port, std::string errStr);
   /* 发送数据到后台 */
   int NetSend(char buf, int len, std::string errStr);
   /* 从后台接收数据，非阻塞，如果在timeout时间内没有数据传来就退出 */
   int NetRecevie(char buf, int len, int timeout, std::string errStr);
   /* 关闭连接 */
   int NetClose(std::string errStr);

protected:
private:

};

#endif