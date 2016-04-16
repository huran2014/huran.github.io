package SOCKETServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread{
	@Override
	public void run() {
		//创建客户编号
		int count = 0;
		try {
			//创建serversocket对象
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(10010);
			while(true)
			{
				//侦听socket连接，阻塞方式
				Socket socket = serverSocket.accept();
				//创建一个新的socket线程来接收和发送信息
				SocketThread socketThread = new SocketThread(socket, "client"+count++);
				socketThread.start();
				ChatManager.getChatManager().add(socketThread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
