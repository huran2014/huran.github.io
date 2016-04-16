/***********************************************************************
 * Module:  RS485Operater.h
 * Author:  Thinkpad
 * Modified: 2015-04-29 14:40:23
 * Purpose: Declaration of the class RS485Operater
 * Comment: RS485操作类
 ***********************************************************************/

#if !defined(__Veg_RS485Operater_h)
#define __Veg_RS485Operater_h

class RS485Operater
{
public:
   /* 向指定地址发送数据 */
   int Send(int Addr, int buf, int len, int errStr);
   /* 初始化 */
   int Init(int Addr, std::string errStr);
   /* 接收数据 */
   int Receive(int Addr, int buf, int len, std::string errStr);
   /* 关闭485 */
   int Close(std::string errStr);

protected:
private:

};

#endif