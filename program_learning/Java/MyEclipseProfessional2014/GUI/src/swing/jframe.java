/*
 * 一个简单的窗口程序
 */
package swing;
import java.awt.*;
import javax.swing.*;

public class jframe extends JFrame{
	//定义按钮组件
	JButton jbutton=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	jframe jframe1=new jframe();
	}
	//添加构造函数，更加像面向对象编程
	public jframe()
	{
		//创建Button按钮
		jbutton=new JButton("请按这个按钮");
		
		//添加按钮
		this.add(jbutton);
		
		//设置顶层名称
		this.setTitle("Thank you!!!!!");
		
		//设置大小（按像素）
		this.setSize(480, 320);
		
		//设置位置（x，y）坐标
		this.setLocation(100, 100);
		
		//设置当关闭窗口时，虚拟机也随即退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//显示
		this.setVisible(true);
	}
}
