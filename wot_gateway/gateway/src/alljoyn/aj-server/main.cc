/*

	1����AllJoyn����ˣ�������PC��openwrt�ϣ���������AllJoyn�ͻ��˵�����
	2��ͬʱ��socket�ͻ��ˣ���Python����˷���JSON��ʽ���豸���ú���Դ����

 */

#include <cstdio>
#include <alljoyn/version.h>
#include "common.h"
#include "mybusobject.h"
#include "mybuslistener.h"
#include "servicebusattachment.h"


// ����ȫ�ֱ���
MyBusObject* s_obj = NULL;
ServiceBusAttachment* servicebus = NULL;
MyBusListener busListener;

const char* OBJ_PATH = "/";
const char* SERVICE_NAME = "org.service1";
const char* INTERFACE_NAME = "org.intf1";

int fd_dev_prop;
int fd_resdata;
int fd_picdata;

const char* host;
int port;

int main(int argc, char** argv)
{	
	//print_server_help();

#ifdef _WIN32
	init_winsock();//win�±������socket��ʹ��
#endif

	if (argc == 1) {
		//����python�����
		create_client_sock(&fd_dev_prop,LOCALNAME,SOCKET_DEV_PROP_PORT);//�豸���Զ�
		create_client_sock(&fd_resdata,LOCALNAME,SOCKET_RES_DATA_PORT);//��Դ���ݶ�
		create_client_sock(&fd_picdata,LOCALNAME,SOCKET_PIC_DATA_PORT);//����ͷsocket��
	}
	else if (argc >= 3){
		printf("parameter error\n");
		return -1;
	}
	else {
		if (strcmp(argv[1],"-nc") != 0) {
			printf("parameter error\n");
			return -1;
		}
	}
	
    QStatus status = ER_OK;

	servicebus = new ServiceBusAttachment("aj_server",true);
	servicebus->createInterface(INTERFACE_NAME);
	//����SessionMemberRemoved֮��Ͳ���������ע�������
   	//servicebus->RegisterBusListener(busListener);

    servicebus->Start();
	
    s_obj = new MyBusObject(*servicebus, OBJ_PATH);
    servicebus->RegisterBusObject(*s_obj);

    servicebus->Connect();

	const TransportMask SERVICE_TRANSPORT_TYPE = TRANSPORT_ANY;
	servicebus->advertiseService(SERVICE_NAME,SERVICE_TRANSPORT_TYPE,busListener);
	   
	printf("waiting client to join...\n");
    while (1) {
#ifdef _WIN32
		Sleep(200);
#else
		sleep(1);
#endif
	}

	delete s_obj;
	delete servicebus;
    return (int) status;
}






