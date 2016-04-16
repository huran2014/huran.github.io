package jpanel;
import java.awt.*;
import javax.swing.*;

public class jpanelshili extends JFrame{
	JPanel jp1,jp2;
	JButton jb1,jb2,jb3,jb4,jb5,jb6,jb7;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jpanelshili j1=new jpanelshili();
	}
	public jpanelshili()
	{
		//创建组件
		//JPanel默认FlowLayout
		jp1=new JPanel();
		jp2=new JPanel();
		
		jb1=new JButton("一");
		jb2=new JButton("二");
		jb3=new JButton("三");
		jb4=new JButton("四");
		jb5=new JButton("五");
		jb6=new JButton("六");
		jb7=new JButton("七");
		
		jp1.add(jb1);
		jp1.add(jb2);
		jp1.add(jb3);
		jp2.add(jb5);
		jp2.add(jb6);
		jp2.add(jb7);
		
		//JFrame默认为BorderLayout
		this.add(jp1, BorderLayout.NORTH);
		this.add(jb4,BorderLayout.CENTER);
		this.add(jp2, BorderLayout.SOUTH);
		
		this.setSize(400, 200);
		this.setLocation(100, 100);
		this.setTitle("JPanel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
}
