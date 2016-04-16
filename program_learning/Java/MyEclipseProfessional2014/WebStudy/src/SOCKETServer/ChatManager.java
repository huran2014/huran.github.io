package SOCKETServer;

import java.util.Vector;

public class ChatManager {
	//�����������
	private Vector<SocketThread> chatroom = new Vector<SocketThread>();
	//�������������
	private static final ChatManager chatManager = new ChatManager();
	//�������������
	private ChatManager() {};
	//��ȡ���������
	public static ChatManager getChatManager() {
		return chatManager;
	}

	//���µ��û����뵽������
	public void add(SocketThread socketclient) {
		chatroom.add(socketclient);
	}

	//���µ��û����뵽������
	public void remove(SocketThread socketclient) {
		chatroom.remove(socketclient);
	}

	//��������Ϣ����
	public void send(SocketThread socketclient, String message) {
		for (int i = 0; i < chatroom.size(); i++) {
			if (!chatroom.get(i).equals(socketclient)) {
				chatroom.get(i).print(message);
			}
		}
	}
}
