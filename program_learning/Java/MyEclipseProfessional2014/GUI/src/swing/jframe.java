/*
 * һ���򵥵Ĵ��ڳ���
 */
package swing;
import java.awt.*;
import javax.swing.*;

public class jframe extends JFrame{
	//���尴ť���
	JButton jbutton=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	jframe jframe1=new jframe();
	}
	//��ӹ��캯�������������������
	public jframe()
	{
		//����Button��ť
		jbutton=new JButton("�밴�����ť");
		
		//��Ӱ�ť
		this.add(jbutton);
		
		//���ö�������
		this.setTitle("Thank you!!!!!");
		
		//���ô�С�������أ�
		this.setSize(480, 320);
		
		//����λ�ã�x��y������
		this.setLocation(100, 100);
		
		//���õ��رմ���ʱ�������Ҳ�漴�˳�
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//��ʾ
		this.setVisible(true);
	}
}
