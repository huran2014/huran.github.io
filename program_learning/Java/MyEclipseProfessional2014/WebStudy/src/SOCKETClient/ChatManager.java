package SOCKETClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;

import org.omg.CORBA.PRIVATE_MEMBER;

public class ChatManager {
	//���ȵ��������󣬾���ʹ����Ψһ��
	private static final ChatManager chatManager = new ChatManager();
	private ChatManager() {} 
	public static ChatManager getChatManager() {
		return chatManager;
	}
	
	//����һ��ʵ���������촰�ڶ����Ա㽫�յ�����Ϣ���͵�������
	ChatClientJPanal chatClientJPanal;
	ChatThread chatThread;
	
	public void setChatClientJPanal(ChatClientJPanal chatClientJPanal) {
		this.chatClientJPanal = chatClientJPanal;
	}
	
	//�������������ӷ���
	public void connect(String ip, String port) {
		chatThread = new ChatThread(ip, port, chatClientJPanal);
		chatThread.start();
	}
	
	//����������Ϣ����
	public void sendMessage(String message) {
		if (chatThread.getPrintWriter() != null) {
			chatThread.getPrintWriter().write(message+"\n");
			chatThread.getPrintWriter().flush();
		} else {
			chatClientJPanal.appendChatWindow("��ǰ�����Ѿ��жϣ��볢���������ӣ�");
		}
	}
}

class ChatThread extends Thread
{
	private String ip;
	private int port;
	Socket socket;
	BufferedReader bufferedReader;
	PrintWriter printWriter;
	String message;
	//����һ��ʵ���������촰�ڶ����Ա㽫�յ�����Ϣ���͵�������
	ChatClientJPanal chatClientJPanal;
	
	public ChatThread(String ip, String port, ChatClientJPanal chatClientJPanal) {
		this.ip = ip;
		this.port = Integer.parseInt(port);
		this.chatClientJPanal = chatClientJPanal;
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	@Override
	public void run() {
		try {
			socket = new Socket(ip, port);
			printWriter = new PrintWriter(
					new OutputStreamWriter(
							socket.getOutputStream()));
			bufferedReader  = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
			
			while ((message = bufferedReader.readLine()) != null) {
				chatClientJPanal.appendChatWindow(message);
			}
			printWriter.close();
			bufferedReader.close();
			printWriter = null;
			bufferedReader = null;
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
