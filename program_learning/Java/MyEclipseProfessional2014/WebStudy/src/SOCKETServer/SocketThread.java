package SOCKETServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class SocketThread extends Thread{
	//接入套接字
	private Socket socket;
	//套接字名称
	private String socketname;
	//发送或接收信息
	private String message;

	//构造方法，传入socket连接
	public SocketThread(Socket socket) {
		this.socket = socket;
	}

	//构造方法，传入socket连接
	public SocketThread(Socket socket, String socketname) {
		this.socket = socket;
		this.socketname = socketname;
	}

	@Override
	public void run() {
		//传递欢迎词
		this.print("欢迎进入聊天室\n");
		try {
			//开始接收客户端发来的信息
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((message = bufferedReader.readLine()) != null) {
				System.out.println("收到客户端信息："+message);
//				System.out.println("message的编码是"+this.getEncoding(message));
				ChatManager.getChatManager().send(this, socketname+" : "+message+"\n");
			}
			bufferedReader.close();
			System.out.println("客户端断开连接");
			ChatManager.getChatManager().remove(this);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("客户端断开连接");
			ChatManager.getChatManager().remove(this);
			e.printStackTrace();
		}
		
//		int count = 0;
//		while (true) {
//			try {
//				this.print("this is the "+count+++" loop\n");
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}

	public void print(String string) {
		try {
			socket.getOutputStream().write(string.getBytes("GB2312"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("断开连接");
			ChatManager.getChatManager().remove(this);
			e.printStackTrace();
		} 
	}
	
	public  String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }
}
