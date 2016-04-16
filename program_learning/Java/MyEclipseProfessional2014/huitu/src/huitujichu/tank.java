package huitujichu;
import java.awt.*;

import javax.swing.*;

public class tank extends JFrame{
	MyPanel1 mp1=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		tank t=new tank();
	}
	public tank()
	{
		mp1=new MyPanel1();
		
		this.add(mp1);
		this.setSize(400, 300);
		this.setLocation(120, 80);
		this.setTitle("mytank1");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

//创造自己的面板
class MyPanel1 extends JPanel
{
	MyTank mytank1=null;
	public  MyPanel1()
	{
		mytank1=new MyTank(100,75);
	}
	public void paint(Graphics g)
	{
		//初始化，必须写
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//开始画坦克
		this.drawtank(mytank1.getX(), mytank1.getY(), g, 0);
	}
	public void drawtank(int x,int y,Graphics g,int type)
	{
		switch(type)
		{
		case 0:
			g.setColor(Color.yellow);break;
		case 1:
			g.setColor(Color.blue);break;
		}
		//开始画坦克
		//先画轮子
		g.fillRect(x, y, 10, 30);
		g.fillRect(x+20, y, 10, 30);
		//再画主体
		g.fillRect(x+5, y+5, 20, 20);
		//再画炮台
		g.fillOval(x+10, y+10, 10, 10);
		g.fillOval(x+12,y+12 , 6, 6);
		//绘制边框
		g.setColor(Color.pink);
		//先画轮子
		g.drawRect(x, y, 10, 30);
		g.drawRect(x+20, y, 10, 30);
		//再画主体
		g.drawRect(x+5, y+5, 20, 20);
		//再画炮台
		g.drawOval(x+10, y+10, 10, 10);
		g.drawOval(x+12,y+12 , 6, 6);
		//最后画炮筒
		g.drawLine(x+15, y-3, x+15, y+12);
	}
}

//创造一个坦克类
class Tank1
{
	int x=0;
	int y=0;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Tank1(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
}

//创造我的坦克
//设计自己的特色坦克
class MyTank extends Tank1
{
	public MyTank(int x,int y)
	{
		super(x,y);
	}
}