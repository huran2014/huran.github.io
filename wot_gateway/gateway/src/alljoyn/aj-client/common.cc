#ifdef _WIN32
#include <WinSock2.h>
#include <IPHlpApi.h>
#else
#include <sys/types.h>
#include <netinet/in.h>
#include <net/if.h>
#include <sys/ioctl.h>

#include <cstring>
#include <cstdio>  
#include <cstdlib> 

#include <unistd.h>  
#include <sys/types.h>  
#include <sys/stat.h>  
#include <fcntl.h>   
#include <termios.h>   
#include <errno.h>    
 
#define serial_device "/dev/ttyS1"
#endif

#include <cstdio>

/*
void print_mac_info(PIP_ADAPTER_INFO adapterInfo)
{
	while (adapterInfo) {
		printf("adapter name: %s\n",adapterInfo->AdapterName);
		printf("adapter description: %s\n",adapterInfo->Description);
		printf("mac addr:");
		
		for (int i=0; i<adapterInfo->AddressLength; i++) {
			printf("%2x",adapterInfo->Address[i]);
		}
		printf("\n");

		//���������ж�IP,���ͨ��ѭ��ȥ�ж�
		IP_ADDR_STRING *pIpAddrString =&(adapterInfo->IpAddressList);
		do 
		{
			printf("%s\n",pIpAddrString->IpAddress.String);
			pIpAddrString=pIpAddrString->Next;
		} while (pIpAddrString);
		adapterInfo = adapterInfo->Next;
	}
}
*/

void get_mac(char macstr[])
{
#ifdef _WIN32
	ULONG outBufLen = sizeof(IP_ADAPTER_ADDRESSES);
	PIP_ADAPTER_ADDRESSES pAddresses = (IP_ADAPTER_ADDRESSES*)malloc(outBufLen);
	// Make an initial call to GetAdaptersAddresses to get the necessary size into the ulOutBufLen variable
	if(GetAdaptersAddresses(AF_UNSPEC, 0, NULL, pAddresses, &outBufLen) == ERROR_BUFFER_OVERFLOW)
	{
		free(pAddresses);
		pAddresses = (IP_ADAPTER_ADDRESSES*)malloc(outBufLen);
	}

	if(GetAdaptersAddresses(AF_UNSPEC, 0, NULL, pAddresses, &outBufLen) == NO_ERROR)
	{
		// If successful, output some information from the data we received
		for(PIP_ADAPTER_ADDRESSES pCurrAddresses = pAddresses; pCurrAddresses != NULL; pCurrAddresses = pCurrAddresses->Next)
		{
			// ȷ��MAC��ַ�ĳ���Ϊ 00-00-00-00-00-00
			if(pCurrAddresses->PhysicalAddressLength != 6)
				continue;
			sprintf(macstr, "%02X%02X%02X%02X%02X%02X",
				int (pCurrAddresses->PhysicalAddress[0]),
				int (pCurrAddresses->PhysicalAddress[1]),
				int (pCurrAddresses->PhysicalAddress[2]),
				int (pCurrAddresses->PhysicalAddress[3]),
				int (pCurrAddresses->PhysicalAddress[4]),
				int (pCurrAddresses->PhysicalAddress[5]));
			break;
		}
	}
	free(pAddresses);
#else
	int sockfd;
	struct ifreq tmp;
	sockfd = socket(AF_INET,SOCK_STREAM,0);
	memset(&tmp,0,sizeof(struct ifreq));

	strncpy(tmp.ifr_name,"apcli0",sizeof(tmp.ifr_name) - 1);

	ioctl(sockfd,SIOCGIFHWADDR,&tmp);

	sprintf(macstr,"%02x%02x%02x%02x%02x%02x",
		(unsigned char)tmp.ifr_hwaddr.sa_data[0],
		(unsigned char)tmp.ifr_hwaddr.sa_data[1],
		(unsigned char)tmp.ifr_hwaddr.sa_data[2],
		(unsigned char)tmp.ifr_hwaddr.sa_data[3],
		(unsigned char)tmp.ifr_hwaddr.sa_data[4],
		(unsigned char)tmp.ifr_hwaddr.sa_data[5]
	);

#endif
}

void print_client_help()
{
	printf("--------------------  The Program Flow   -------------------\n\n");
	printf("\t1.Create a client BusAttachment instance\n");
	printf("\t2.Create interfaces for the instance\n");
	printf("\t3.Register BusListener for the instance\n");
	printf("\t4.Start bus instance\n");
	printf("\t5.Create BusObject instance\n");
	printf("\t6.Register BusObject for bus instance\n");
	printf("\t7.Connect to router\n");
	printf("\t8.Start to find service and do work\n\n");
	printf("--------------------   End               -------------------\n\n");
}

#ifndef _WIN32
  
//�򿪴���  
static int open_port(void)  
{  
    int fd;     //���ڵı�ʶ��  
    //O_NOCTTY��������Linux������򲻻��Ϊ�������նˡ�  
    //O_NDELAY��������Linux������򲻹���DCD�ź�  
    fd=open(serial_device,O_RDWR | O_NOCTTY | O_NDELAY);  
    if(fd == -1)  
    {  
        //���ܴ򿪴���  
        perror("open_port: Unable to open /dev/ttyS0 -");  
        return(fd);  
    }  
    else  
    {  
        fcntl(fd, F_SETFL, 0);  
        //printf("open ttys1 .....\n");  
        return(fd);  
    }  
}  
  
//���ò�����  
static void set_speed_and_parity(int fd, int speed)  
{  
    int i=0;        //����ѭ����־����ע�ⲻҪ��for�����ã���������  
    struct termios Opt; //����termios�ṹ  
    if(tcgetattr(fd,&Opt)!=0)  
    {  
        perror("tcgetattr fd");  
        return;  
    }  
    tcflush(fd, TCIOFLUSH);  
    cfsetispeed(&Opt, speed);  
    cfsetospeed(&Opt, speed);  
    /*tcsetattr������־�� 
    TCSANOW������ִ�ж����ȴ����ݷ��ͻ��߽�����ɡ� 
    TCSADRAIN���ȴ��������ݴ�����ɺ�ִ�С� 
    TCSAFLUSH��Flush input and output buffers and make the change 
    */  
    if(tcsetattr(fd, TCSANOW, &Opt) != 0)  
    {     
        //perror("tcsetattr fd");  
        return;  
    }  
    tcflush(fd, TCIOFLUSH);  
    //������żУ�顪��Ĭ��8������λ��û��У��λ  
          
    Opt.c_cflag &= ~PARENB;  
    Opt.c_cflag &= ~CSTOPB;  
    Opt.c_cflag &= ~CSIZE;  
    Opt.c_cflag |= CS8;  
      
    //������һЩ����  
    //ԭʼ���룬�����ַ�ֻ�Ǳ�ԭ�ⲻ���Ľ���  
    Opt.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG);  
    //�����������Ч����ΪӲ��û��Ӳ�������ƣ����ԾͲ���Ҫ����  
    Opt.c_iflag &= ~(IXON | IXOFF | IXANY);  
    //ԭʼ�����ʽ����ͨ����c_oflag������OPOSTѡ����ѡ��  
    Opt.c_oflag |= ~OPOST;  
    //VMIN����ָ����ȡ����С�ַ����������������Ϊ0����ôVTIMEֵ���ָ��ÿ���ַ���ȡ�ĵȴ�ʱ�䡣  
    Opt.c_cc[VTIME] = 0;  
    Opt.c_cc[VMIN] = 0;  
      
//      Opt.c_cflag &= ~INPCK;  
//      Opt.c_cflag |= (CLOCAL | CREAD);  
//    
//      Opt.c_lflag &= ~(ICANON | ECHO | ECHOE | ISIG);  
//   
//      Opt.c_oflag &= ~OPOST;  
    Opt.c_oflag &= ~(ONLCR | OCRNL);      
  
    Opt.c_iflag &= ~(ICRNL | INLCR);  
//      Opt.c_iflag &= ~(IXON | IXOFF | IXANY);      
//      
//      Opt.c_cc[VTIME] = 0;  
//      Opt.c_cc[VMIN] = 0;  
      
    tcflush(fd, TCIOFLUSH);  
  
}  
  
  
/** 
  *���ڷ������ݺ��� 
  *fd������������ 
  *data������������ 
  *datalen�����ݳ��� 
  */  
static int serial_write(int fd ,char *data, int datalen)  
{  
    int len=0;  
    //��ȡʵ�ʴ������ݵĳ���  
    len=write(fd,data,datalen);  
    //printf("send data OK! datalen=%d\n",len);  
    return len;   
}  
  
/**  
  *���ڽ�������  
  *Ҫ����������pc�˷���ascii�ļ�  
  */   
static int serial_read(int fd,char buff[],int datalen)  
{  
    int nread=0;  
    //printf("Ready for receiving data...");  
    nread=read(fd,buff,datalen);  
    if(nread>0&&buff[0]!='\n')  
    {  
        //printf("readlength=%d\n",nread);  
        buff[nread]='\0';  
        //printf("%s\n",buff);  
    }  
    return nread;  
}  
  
static int serialport()  
{  
    int fd;   
    //�򿪴���  
    if((fd=open_port())<0)  
        {  
            perror("open_port error");  
            return 0;  
        }  
    //���ò����ʺ�У��λ  
    set_speed_and_parity(fd,115200);  
    return (fd);  
}  
  
double get_sensor_data()  
{  
    int fd;  
	double data = 0;
    int nread,i,n =0,datalen=16, len = 0;  
    char testbuff[]="Hello\r\n";  
    char readbuff[16];  
      
    fd=serialport();  
    //printf("fd=%d\n",fd);  
    //������д����  
    nread=write(fd,testbuff,datalen);  
    if(nread==-1) {  
        return 0;  
    }  
    else{  
        //printf("the test is ok!\n");  
    }     

	bzero(readbuff, sizeof(readbuff));  
	datalen=serial_read(fd,readbuff,16);  
	if(datalen > 0&&readbuff[0]!='\n')  
	{  
		//printf("readbuff:%s\n",readbuff);
		data = strtod(readbuff,NULL);//�ַ���ת��double
    } 
	return data;
}  

#endif