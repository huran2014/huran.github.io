#ifndef MYBUSLISTENER_H_
#define MYBUSLISTENER_H_

#include <alljoyn/BusAttachment.h>

using namespace ajn;

//��������˵�listner�̳���SessionListner���������������RegisterListener�ͻ����������쳣����Ӧ��
//ѡ����SessionJoined�е���SetSessionListner
/*
Assertion failed:m_constructed && "IpNameService::" "\RegisterListener\"" "():
Singleton not constructed", file alljoyn_core\router\ns\IpNameService.cc,line 531
*/

//����ٴγ����������󣬿��Գ�����Ӽ̳�,public SessionListener
class MyBusListener : public BusListener, public SessionPortListener, public SessionListener
{
public:
	
	//���������Ǽ̳���SessionPortListener
	virtual bool AcceptSessionJoiner(SessionPort sessionPort, const char* joiner, const SessionOpts& opts);
	virtual void SessionJoined(SessionPort sessionPort, SessionId id, const char* joiner);

	//�̳���BusListener
	virtual void NameOwnerChanged(const char* busName, const char* previousOwner, const char* newOwner);


	//SessionListener�������������ƺ��Ͳ������NameOwnerChanged
	virtual void SessionMemberAdded(SessionId sessionId, const char* uniqueName);
	virtual void SessionMemberRemoved(SessionId sessionId, const char* uniqueName);

};

#endif // !MYBUSLISTENER_H_