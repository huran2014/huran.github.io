/*
 * 网格布局方案
 */
package bujuguanli;
import java.awt.*;
import javax.swing.*;

public class wanggebuju extends JFrame{
	int size=9;
	JButton jbutton[]=new JButton[size];
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		wanggebuju wanggebuju1=new wanggebuju();
	}
	public wanggebuju()
	{
		for(int i=0;i<size;i++)
		{
			jbutton[i]=new JButton(String.valueOf(i+1));
		}
		//设置布局
		this.setLayout(new GridLayout(3,3));
		for(int i=0;i<size;i++)
		{
			this.add(jbutton[i]);
		}
		this.setTitle("GridLayout");
		this.setSize(400, 300);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
