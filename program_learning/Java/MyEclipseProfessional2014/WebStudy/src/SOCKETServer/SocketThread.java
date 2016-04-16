package SOCKETServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class SocketThread extends Thread{
	//�����׽���
	private Socket socket;
	//�׽�������
	private String socketname;
	//���ͻ������Ϣ
	private String message;

	//���췽��������socket����
	public SocketThread(Socket socket) {
		this.socket = socket;
	}

	//���췽��������socket����
	public SocketThread(Socket socket, String socketname) {
		this.socket = socket;
		this.socketname = socketname;
	}

	@Override
	public void run() {
		//���ݻ�ӭ��
		this.print("��ӭ����������\n");
		try {
			//��ʼ���տͻ��˷�������Ϣ
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while ((message = bufferedReader.readLine()) != null) {
				System.out.println("�յ��ͻ�����Ϣ��"+message);
//				System.out.println("message�ı�����"+this.getEncoding(message));
				ChatManager.getChatManager().send(this, socketname+" : "+message+"\n");
			}
			bufferedReader.close();
			System.out.println("�ͻ��˶Ͽ�����");
			ChatManager.getChatManager().remove(this);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("�ͻ��˶Ͽ�����");
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
			System.out.println("�Ͽ�����");
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
