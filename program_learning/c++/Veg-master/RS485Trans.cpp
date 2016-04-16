/***********************************************************************
 * Module:  RS485Operater.cpp
 * Author:  Thinkpad
 * Modified: 2015-04-29 14:40:23
 * Purpose: Implementation of the class RS485Operater
 * Comment: RS485操作类
 ***********************************************************************/

#include "RS485Trans.h"
#include <string.h>
#include "usart.h" 
#include "crc.h"


#include "LOGCTRL.h"
//#define NO_POS_DEBUG
#include "pos_debug.h"

////////////////////////////////////////////////////////////////////////
// Name:       RS485Operater::Send(int Addr, int buf, int len, int errStr)
// Purpose:    Implementation of RS485Operater::Send()
// Comment:    向指定地址发送数据
// Parameters:
// - Addr
// - buf
// - len
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int RS485Trans::Send(char* Addr, char* buf, int len)
{
   // TODO : implement
   unsigned short crc=0;
   m_fillPos = 0;
   //包头
   m_sendBuff[0] = "2";
   m_fillPos++;
   //对方的地址
   memcpy(m_sendBuff+m_fillPos,Addr,MAC_NUM_LEN);
   m_fillPos += 10;
   //自己的地址
   memcpy(m_sendBuff+m_fillPos,m_addr,MAC_NUM_LEN);
   m_fillPos += 10;
   //信息内容长度
   memcpy(m_sendBuff + m_fillPos,(char *)&len,4);
   m_fillPos += 4;
   //信息内容
   memcpy(m_sendBuff + m_fillPos,buf,len);
   m_fillPos += len;

   	//crc校验
	crc = cal_crc(m_sendBuff, m_fillPos);
	DBG_PRN(("crc = %04x",crc))
	*(m_sendBuff + m_fillPos++) =  ((unsigned char*)&crc)[1];
	*(m_sendBuff + m_fillPos++) =  ((unsigned char*)&crc)[0];

	usart1_write(m_sendBuff,m_fillPos);
   
   
}

////////////////////////////////////////////////////////////////////////
// Name:       RS485Operater::Init(int Addr, std::string errStr)
// Purpose:    Implementation of RS485Operater::Init()
// Comment:    初始化
// Parameters:
// - Addr
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int RS485Trans::Init(char* Addr);
   // TODO : implement
	memcpy(m_addr,Addr,MAC_NUM_LEN);
   
   
}

////////////////////////////////////////////////////////////////////////
// Name:       RS485Operater::Receive(int Addr, int buf, int len, std::string errStr)
// Purpose:    Implementation of RS485Operater::Receive()
// Comment:    接收数据
// Parameters:
// [in/out] Addr 返回发送方地址	
// [out] buf	 返回接收数据首地址
// [in/out] len  返回接收到的数据长度
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int RS485Trans::Receive(char* Addr, char *buf, int &len);
{
   // TODO : implement
   	int overtime = 0;
	int packageLen = 0; //包长度
	int tmpLen;
	unsigned short crc=0;
	m_recvPos = 0;
	while (overtime < 1000)
	{
		if (ser_can_read(UART1)> 0)
		{
			//判断包头
			usart1_read(m_recvBuff, 1);
			DBG_PRN(("%02X",m_recvBuff[0])
			if(m_recvBuff[0] != '2')//包头不对，跳出
			{
				return 0;
			}
			m_recvPos++;
			//进行地址匹配
			usart1_read(m_recvBuff+m_recvPos, MAC_NUM_LEN);
			if(strncmp(m_recvBuff+m_recvPos, m_addr,MAC_NUM_LEN )!=0)//地址不对 跳出
			{
				m_recvPos = 0;
				return 0;
			}
			m_recvPos = m_recvPos+10;
			
			//将发送方地址传入到ADDR
			usart1_read(m_recvBuff+m_recvPos, MAC_NUM_LEN);
			memcpy(Addr,m_recvBuff+m_recvPos,MAC_NUM_LEN);
			m_recvPos = m_recvPos+10;
		
			//数据总长度
			usart1_read(m_recvBuff+m_recvPos, 4);
			memcpy((char*)&packageLen,m_recvBuff+m_recvPos,4);
			DBG_PRN(("%s len= %d","get packge",packageLen))
			m_recvPos = m_recvPos+4;
			len = packageLen
						
			if (packageLen > 0)
			{
				while(m_recvPos < packageLen)
				{
					tmpLen =  usart1_read((char*)(m_recvBuff+m_recvPos), packageLen-m_recvPos);
					m_recvPos += tmpLen;
					if (tmpLen ==0)//超时过多接收不到包就跳出
					{
						overtime++;
						Delay_ms(3);
						if (overtime < NODATA_MAXTIME)
						{
							return -1;
						}
					}
				}
				//成功接收完一包数据 开始crc校验
				crc = cal_crc(m_recvBuff, m_recvPos);
				DBG_PRN(("crc = %04x",crc))
				//crc = (crc&0x00FF)*256 + (crc&0xFF00)/256;
				DBG_NPRINT_HEX(((char *)&crc),2)
				if(strncmp((const char*)m_recvBuff+m_recvPos,(char *)&crc, 2))
				{
					DBG_PRN(("数据校验错误"))
					return 0;
				}

				return 1;
			}
		}
		overtime++;
		Delay_ms(3);
	}
}

////////////////////////////////////////////////////////////////////////
// Name:       RS485Operater::Close(std::string errStr)
// Purpose:    Implementation of RS485Operater::Close()
// Comment:    关闭485
// Parameters:
// - errStr
// Return:     int
////////////////////////////////////////////////////////////////////////

int RS485Trans::Close()
{
   // TODO : implement
}



RS485Trans::RS485Trans()
{
	memset(m_addr,'0',sizeof(m_addr));
}
