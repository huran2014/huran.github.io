/***********************************************************************
 * Module:  UartTrans.h
 * Author:  Thinkpad
 * Modified: 2015-04-29 14:14:02
 * Purpose: Declaration of the class UartTrans
 * Comment: 串口操作类
 ***********************************************************************/

//头文件定义
#include <stdio.h> 	//标准输入输出定义
#include <cstdlib>	//标准函数库定义
#include <unistd.h>	//Unix标准函数定义
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h> 	//文件控制定义
#include <termios.h>	//POSIX中断控制定义
#include <cstring>
#include <string>
#if !defined(__Veg_UartTrans_h)
#define __Veg_UartTrans_h

class UartTrans
{
	public:
		/* 打开串口 */
		int UartOpen(int UartNo, int baudRate, std::string &errStr);

		/* 串口发送 */
		int UartSend(char *buf, int len, std::string &errStr);
		/* 串口接收 */
		int UartRecevie(char *buf, int len, std::string &errStr);
		/* 关闭串口 */
		int UartColse(std::string &errStr);
	private:
		int fd;		//串口的标识符
};



/* 打开串口 */
int UartTrans::UartOpen(int UartNo, int baudRate, std::string &errStr)
{
	printf("open uart......\n");
	//O_NOCTTY用来告诉Linux这个程序不会成为“控制终端”
	//O_NDELAY用来告诉Linux这个程序不关心DCD信号
	char* serial_device = new char;
	if(UartNo==0) strcpy(serial_device,"/dev/ttyS0");
	else if (UartNo == 1) strcpy(serial_device,"/dev/ttyS1");
	this->fd = open(serial_device, O_RDWR | O_NOCTTY | O_NDELAY);
	if (this->fd == -1)
	{
		//不能打开串口
		errStr = "open_port: Unable to open ";
		errStr += serial_device;
//		perror("open_port: Unable to open /dev/ttyS0 -");
		return(0);
	}
	else
		fcntl(this->fd, F_SETFL, 0);
	delete(serial_device);

	//开始定义波特率
	struct termios Opt;	//定义termios结构
	if (tcgetattr(this->fd, &Opt) != 0)
	{
		errStr="tcgetattr fd failly";
		return 0;
	}
	tcflush(this->fd, TCIOFLUSH);
	cfsetispeed(&Opt, baudRate);
	cfsetospeed(&Opt, baudRate);
	/*tcsetattr函数标志：
	TCSANOW：立即执行而不等待数据发送或者接受完成。
	TCSADRAIN：等待所有数据传递完成后执行。
	TCSAFLUSH：Flush input and output buffers and make the change
	*/
	if (tcsetattr(this->fd, TCSANOW, &Opt) != 0)
	{
		errStr="tcsetattr fd failly";
		return 0;
	}
	tcflush(this->fd, TCIOFLUSH);
	//设置奇偶校验——默认8个数据位、没有校验位

	Opt.c_cflag &= ~PARENB;
	Opt.c_cflag &= ~CSTOPB;
	Opt.c_cflag &= ~CSIZE;
	Opt.c_cflag |= CS8;

	//其他的一些配置
	//原始输入，输入字符只是被原封不动的接收
	Opt.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG);
	//软件流控制无效，因为硬件没有硬件流控制，所以就不需要管了
	Opt.c_iflag &= ~(IXON | IXOFF | IXANY);
	//原始输出方式可以通过在c_oflag中重置OPOST选项来选择：
	Opt.c_oflag |= ~OPOST;
	//VMIN可以指定读取的最小字符数。如果它被设置为0，那么VTIME值则会指定每个字符读取的等待时间。
	Opt.c_cc[VTIME] = 0;
	Opt.c_cc[VMIN] = 0;
	Opt.c_oflag &= ~(ONLCR | OCRNL);
	Opt.c_iflag &= ~(ICRNL | INLCR);
	tcflush(this->fd, TCIOFLUSH);

	printf("open uart succeed!\n");
	return 1;

}
/* 串口发送 */
int UartTrans::UartSend(char *buf, int len, std::string &errStr)
{
	printf("send data.......\n");
	//获取实际传输数据的长度
	int nread = 0;
	nread = write(this->fd, buf, len);
	if (nread <= 0)
	{
		errStr = "send data failly";
		return 0;
	}
	else
		printf("send data succeed,data length=%d\n",nread);
	return 1;
}
/* 串口接收 */
int UartTrans::UartRecevie(char *buf, int len, std::string &errStr)
{
	printf("receive data.....\n");
	bzero(buf, sizeof(buf)); 
	len = read(this->fd, buf, len);
	if (len < 0)
	{
		errStr = "Receiving data failly";
		return 0;
	}
	printf("data receive succeed,data length=%d\n",len);
	return 1;
}
/* 关闭串口 */
int UartTrans::UartColse(std::string &errStr)
{
	printf("close uart...\n");
	if (close(this->fd) == 0)
		return 1;
	else
	{
		errStr = "close_port:Unable to close uart";
		return 0;
	}
}

#endif
