package bujuguanli;
import java.awt.*;
import javax.swing.*;

public class borderlayout extends JFrame{
	
	JButton jbutton1,jbutton2,jbutton3,jbutton4,jbutton5;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		borderlayout bor=new borderlayout();
	}
	//���췽��
	public borderlayout()
	{
		jbutton1=new JButton("�в�");
		jbutton2=new JButton("����");
		jbutton3=new JButton("����");
		jbutton4=new JButton("�ϲ�");
		jbutton5=new JButton("����");
		
		this.add(jbutton1, BorderLayout.CENTER);
		this.add(jbutton2, BorderLayout.NORTH);
		this.add(jbutton3, BorderLayout.EAST);
		this.add(jbutton4, BorderLayout.SOUTH);
		this.add(jbutton5, BorderLayout.WEST);
		
		this.setTitle("BorderLayout");
		this.setSize(640, 480);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
