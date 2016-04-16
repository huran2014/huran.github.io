#include "mybuslistener.h"
#include "clientbusattachment.h"
#include "mybusobject.h"

#include <iostream>
#include <signal.h>

using namespace std;

extern ClientBusAttachment* clientbus;
extern MyBusObject* obj;

extern SessionPort PORT;
extern char dev_prop[1024];

SessionId g_sessionId;

extern volatile sig_atomic_t g_joinComplete;

void MyBusListener::FoundAdvertisedName(const char* name, TransportMask transport, const char* namePrefix)
{
	clientbus->EnableConcurrentCallbacks();
	SessionOpts opts(SessionOpts::TRAFFIC_MESSAGES, true, SessionOpts::PROXIMITY_ANY, TRANSPORT_ANY);

	QStatus status = clientbus->JoinSession(name, PORT, this, g_sessionId, opts);
	if (ER_OK == status) {
		cout << "Joined conversation. Session id=" << g_sessionId << endl;

	} else {
		cout << "JoinSession failed (status=" << QCC_StatusText(status) << endl;
	}

	uint32_t timeout = 40;
	status = clientbus->SetLinkTimeout(g_sessionId, timeout);
	if (ER_OK != status) {
		cout << "Set link timeout failed\n";
	}
	g_joinComplete = true;
	obj->sendDevPropSignal(dev_prop);
}


void MyBusListener::LostAdvertisedName(const char* name, TransportMask transport, const char* namePrefix)
{
	//�ͻ��˵���
	cout << "client is losing advertised name:" << name << endl;
}


void MyBusListener::SessionLost (SessionId sessionId)
{
	// �ͻ��˵��ã��ȵ����������ٵ���LostAdvertisedName
	cout << "\nsession " << sessionId << " lost\n";

	//�Ự��ʧ������ֹͣ�����źţ����Կ��ٴν������� ������
}


void MyBusListener::NameOwnerChanged(const char* busName, const char* previousOwner, const char* newOwner)
{
	cout << "NameOwnerChanged: name=" << busName << ", oldOwner=" << (previousOwner ? previousOwner : "<none>") <<
		", newOwner=" << (newOwner ? newOwner : "<none>") << endl;
}
