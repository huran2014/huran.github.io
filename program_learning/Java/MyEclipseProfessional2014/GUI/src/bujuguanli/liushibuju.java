/*
 * ��ʽ���ַ���
 */
package bujuguanli;
import java.awt.*;
import javax.swing.*;

public class liushibuju extends JFrame{
	JButton jbutton1,jbutton2,jbutton3,jbutton4,jbutton5;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		liushibuju liushibuju1=new liushibuju();
	}
	//���췽��
	public liushibuju()
	{
		jbutton1=new JButton("һ");
		jbutton2=new JButton("��");
		jbutton3=new JButton("��");
		jbutton4=new JButton("��");
		jbutton5=new JButton("��");
		
		this.add(jbutton1);
		this.add(jbutton2);
		this.add(jbutton3);
		this.add(jbutton4);
		this.add(jbutton5);
		
		//���ò��ֹ�����
		//JFrameĬ��ʹ��BorderLayout
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.setSize(400, 300);
		this.setLocation(100, 100);
		this.setTitle("FlowLayout");
		//��ֹ�û��ı䴰���С
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}
