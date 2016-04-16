/*
	�������ڶ����ͻ�ʱ��Ҳ�����˿ͻ�1��session id���ˣ�Ϊʲô ��
	��ô�������ͻ��ڲ�ͬ��session�У�

	��������ʱ��ʲôʱ����char��ʲôʱ����unsigned char ?

*/

#include <cstdlib>
#include <cstring>
#include <iostream>
#include <time.h>
#include <signal.h>

#include <qcc/Debug.h>
#include <alljoyn/version.h>

#include "clientbusattachment.h"
#include "mybuslistener.h"
#include "mybusobject.h"
#include "common.h"

ClientBusAttachment* clientbus = NULL;
MyBusObject* obj = NULL;
MyBusListener busListener;

const char* OBJ_PATH = "/";
const char* SERVICE_NAME = "org.service1";
const char* INTERFACE_NAME = "org.intf1";
SessionPort PORT = 250; // ���ܼ�const���Σ�������mybuslistner��externʱ�����ӳ���

ajn::SessionId sessionId;
volatile sig_atomic_t g_joinComplete = false;

char macstr[13] = {'\0'};//mac��ַ

char dev_prop[1024] = "{\"Mac_address\":\"";

struct res *root;

void handle_prompt(int argc,char** argv)
{
	char tmp[128] = "";
	sprintf(tmp,"%d",argc-1);

	if (argc < 2) {
		printf("usage:ajtest_client {resnum:restype} ...\n");
		exit(0);
	}
	struct res *pRes = (struct res *)malloc(sizeof(struct res) * (argc-1));
	root = pRes;

	if (pRes == NULL) {
		printf("malloc failed\n");
		return;
	}

	strcat(dev_prop,macstr);
	strcat(dev_prop,"\",\"Res_num\":");
	strcat(dev_prop,tmp);
	strcat(dev_prop,",\"flags\":0,\"Res\":[");

	for (int i=1; i<argc; i++) {
		char restmp[10];
		
		std::string tmpstr(argv[i]);
		int pos = tmpstr.find(':');
		std::string type = tmpstr.substr(pos+1);
		std::string id = tmpstr.substr(0,pos);

		pRes->resnum = atoi(id.c_str());
		strcpy(tmp,"{\"Res_name\":\"\",\"Res_port\":");
		strcat(tmp,id.c_str());
		strcpy(restmp,type.c_str());
		strcpy(pRes->type,type.c_str());

		strcat(tmp,",\"Res_unit\":\"\",\"Res_type\":\"");
		strcat(tmp,restmp);
		strcat(tmp,"\"}");

		if (i != argc-1) {
			strcat(tmp,",");
			strcat(dev_prop,tmp);	
		}
		else {
			strcat(dev_prop,tmp);
		}
		++pRes;
	}
	strcat(dev_prop,"]}");
	printf("dev_prop: %s\n",dev_prop);
}

int main(int argc, char** argv)
{
	get_mac(macstr);

	handle_prompt(argc,argv);

	//print_client_help();

	//printf("%s\n",ajn::GetBuildInfo());
	QStatus status = ER_OK;

	clientbus = new ClientBusAttachment("aj_client",true);
	clientbus->createInterface(INTERFACE_NAME);
	clientbus->RegisterBusListener(busListener);
	clientbus->startMessageBus();

	obj = new MyBusObject(*clientbus, OBJ_PATH);

	clientbus->registerBusObject(*obj);

    clientbus->connectBusAttachment();
	
	clientbus->findAdvertisedName();
	clientbus->waitForJoinSessionCompletion();

	srand((unsigned)time(NULL));
	printf("\nstarting to send res data_info...\n");

	double val;
	while (ER_OK == status) {
		for (int i=0; i<argc-1; i++) {

			if (strcmp(root[i].type,"temp") == 0 || strcmp(root[i].type,"light") == 0 ){
#if _WIN32
				//�����WIN�£������������ֵ
				val = rand() % 100;
#else
				//�����������
				val = get_sensor_data();
#endif
				obj->sendResdataSignal(root[i].resnum,val);//���ʹ�����ֵ
			}

			//���������ͷ�������
			else if (strcmp(root[i].type,"monitor") == 0) {
#ifdef _WIN32
				std::string filename = "image\\";
#else
				std::string filename = "image/";
#endif
				//��ȡ����ͷ������������imageĿ¼�µ�ͼƬ
				char resid[3];
				sprintf(resid,"%d",root[i].resnum);
				filename.append(resid);
				filename.append(".jpg");

				//printf("debug filename:%s\n",filename.c_str());
				obj->sendPicSignal(filename.c_str());

			}
			else {
				//�����������Ͳ�ƥ��ʱ����������
				printf("sensor type input error\n");
				val = rand() % 100;
				obj->sendResdataSignal(root[i].resnum,val);//���ʹ���������ֵ
			}
		}

		printf("\n");
		
		//һ���豸��������Դ���ݷ���һ�κ󣬶�ʱһ��ʱ���ٷ���
#ifdef _WIN32
		Sleep(1000*30);
#else
		sleep(10);
#endif
	}
    return 0;
}





