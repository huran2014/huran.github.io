/*
 * 流式布局方案
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
	//构造方法
	public liushibuju()
	{
		jbutton1=new JButton("一");
		jbutton2=new JButton("二");
		jbutton3=new JButton("三");
		jbutton4=new JButton("四");
		jbutton5=new JButton("五");
		
		this.add(jbutton1);
		this.add(jbutton2);
		this.add(jbutton3);
		this.add(jbutton4);
		this.add(jbutton5);
		
		//设置布局管理器
		//JFrame默认使用BorderLayout
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.setSize(400, 300);
		this.setLocation(100, 100);
		this.setTitle("FlowLayout");
		//禁止用户改变窗体大小
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
}
