package SOCKETServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
	@Override
	public void run() {
		//�����ͻ����
		int count = 0;
		try {
			//����serversocket����
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(10010);
			while(true)
			{
				//����socket���ӣ�������ʽ
				Socket socket = serverSocket.accept();
				//����һ���µ�socket�߳������պͷ�����Ϣ
				SocketThread socketThread = new SocketThread(socket, "client"+count++);
				socketThread.start();
				ChatManager.getChatManager().add(socketThread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
