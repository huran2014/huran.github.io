#ifndef MYBUSLISTENER_H_
#define MYBUSLISTENER_H_

#include <alljoyn/BusAttachment.h>

using namespace ajn;

class MyBusListener : public BusListener, public SessionListener
{
public:
	//���������Ǽ̳���BusListener
	virtual void FoundAdvertisedName(const char* name, TransportMask transport, const char* namePrefix);		
	virtual void LostAdvertisedName(const char* name, TransportMask transport, const char* namePrefix);
	virtual void NameOwnerChanged(const char* busName, const char* previousOwner, const char* newOwner);
	//virtual void BusDisconnected();

	//�̳���SessionListener
	virtual void SessionLost (SessionId sessionId);

};

#endif // !MYBUSLISTENER_H_