package SOCKETServer;

import java.util.Vector;

public class ChatManager {
	//创建聊天队列
	private Vector<SocketThread> chatroom = new Vector<SocketThread>();
	//创建聊天管理器
	private static final ChatManager chatManager = new ChatManager();
	//构造聊天管理器
	private ChatManager() {};
	//获取聊天管理器
	public static ChatManager getChatManager() {
		return chatManager;
	}

	//将新的用户接入到聊天器
	public void add(SocketThread socketclient) {
		chatroom.add(socketclient);
	}

	//将新的用户接入到聊天器
	public void remove(SocketThread socketclient) {
		chatroom.remove(socketclient);
	}

	//聊天室信息发出
	public void send(SocketThread socketclient, String message) {
		for (int i = 0; i < chatroom.size(); i++) {
			if (!chatroom.get(i).equals(socketclient)) {
				chatroom.get(i).print(message);
			}
		}
	}
}
