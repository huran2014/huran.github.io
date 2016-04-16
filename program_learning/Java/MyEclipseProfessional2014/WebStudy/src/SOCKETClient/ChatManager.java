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
	//首先单例化对象，就是使对象唯一化
	private static final ChatManager chatManager = new ChatManager();
	private ChatManager() {} 
	public static ChatManager getChatManager() {
		return chatManager;
	}
	
	//创建一个实例化的聊天窗口对象，以便将收到的信息发送到窗口中
	ChatClientJPanal chatClientJPanal;
	ChatThread chatThread;
	
	public void setChatClientJPanal(ChatClientJPanal chatClientJPanal) {
		this.chatClientJPanal = chatClientJPanal;
	}
	
	//创建聊天器连接方法
	public void connect(String ip, String port) {
		chatThread = new ChatThread(ip, port, chatClientJPanal);
		chatThread.start();
	}
	
	//创建发送信息方法
	public void sendMessage(String message) {
		if (chatThread.getPrintWriter() != null) {
			chatThread.getPrintWriter().write(message+"\n");
			chatThread.getPrintWriter().flush();
		} else {
			chatClientJPanal.appendChatWindow("当前连接已经中断，请尝试重新连接！");
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
	//创建一个实例化的聊天窗口对象，以便将收到的信息发送到窗口中
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
