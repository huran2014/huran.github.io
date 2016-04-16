package Tank;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class MyTank_version1 extends JFrame{
	MyPanel2 mp2=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTank_version1 t=new MyTank_version1();
	}
	public MyTank_version1()
	{
		mp2=new MyPanel2();
		//注册监听
		this.addKeyListener(mp2);
		this.add(mp2);
		this.setSize(400, 300);
		this.setLocation(120, 80);
		this.setTitle("mytank1");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

//创造自己的面板
class MyPanel2 extends JPanel implements KeyListener
{
	//定义自己的坦克
	MyTank2 mytank2=null;
	//定义敌人的坦克
	Vector<EnemyTank2> ets=new Vector<EnemyTank2>();
	//定义敌人坦克数目
	int enemytanknum=3;
	
	//构造函数
	public  MyPanel2()
	{
		mytank2=new MyTank2(100,75);
		//初始化敌人坦克
		for(int i=0;i<enemytanknum;i++)
		{
			EnemyTank2 et=new EnemyTank2((i+1)*50,0);
			et.setMove(2);
			ets.add(et);
		}
	}
	public void paint(Graphics g)
	{
		//初始化，必须写
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//开始画坦克
		this.drawtank(mytank2.getX(), mytank2.getY(), g,mytank2.getMove(),0);
		//绘制敌人坦克
		for(int i=0;i<enemytanknum;i++)
		{
			this.drawtank(ets.get(i).getX(), ets.get(i).getY(), g, ets.get(i).getMove(), 1);
		}
	}
	public void drawtank(int x,int y,Graphics g,int move,int type)
	{
		switch(type)
		{
		case 0:
			g.setColor(Color.yellow);break;
		case 1:
			g.setColor(Color.blue);break;
		}
		switch(move)
		{
		case 0:
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
			break;
		case 1:
			//先画轮子
			g.fillRect(x, y, 30, 10);
			g.fillRect(x, y+20, 30, 10);
			//再画主体
			g.fillRect(x+5, y+5, 20, 20);
			//再画炮台
			g.fillOval(x+10, y+10, 10, 10);
			g.fillOval(x+12,y+12 , 6, 6);
			//绘制边框
			g.setColor(Color.pink);
			//先画轮子
			g.drawRect(x, y, 30, 10);
			g.drawRect(x, y+20, 30, 10);
			//再画主体
			g.drawRect(x+5, y+5, 20, 20);
			//再画炮台
			g.drawOval(x+10, y+10, 10, 10);
			g.drawOval(x+12,y+12 , 6, 6);
			//最后画炮筒
			g.drawLine(x+18, y+15, x+33, y+15);
			break;
		case 2:
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
			g.drawLine(x+15, y+18, x+15, y+33);
			break;
		case 3:
			//先画轮子
			g.fillRect(x, y, 30, 10);
			g.fillRect(x, y+20, 30, 10);
			//再画主体
			g.fillRect(x+5, y+5, 20, 20);
			//再画炮台
			g.fillOval(x+10, y+10, 10, 10);
			g.fillOval(x+12,y+12 , 6, 6);
			//绘制边框
			g.setColor(Color.pink);
			//先画轮子
			g.drawRect(x, y, 30, 10);
			g.drawRect(x, y+20, 30, 10);
			//再画主体
			g.drawRect(x+5, y+5, 20, 20);
			//再画炮台
			g.drawOval(x+10, y+10, 10, 10);
			g.drawOval(x+12,y+12 , 6, 6);
			//最后画炮筒
			g.drawLine(x-3, y+15, x+12, y+15);
			break;
		}
		
	}
	//检测按键被按下 w围上，s为下，a为左，d为右
	public void keyPressed(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_W)
		{
			this.mytank2.setMove(0);
			this.mytank2.moveup();
		}
		else if(e.getKeyCode()==KeyEvent.VK_D)
		{
			this.mytank2.setMove(1);
			this.mytank2.moveright();
		}
		else if(e.getKeyCode()==KeyEvent.VK_S)
		{
			this.mytank2.setMove(2);
			this.mytank2.movedown();
		}
		else
		{
			this.mytank2.setMove(3);
			this.mytank2.moveleft();
		}
		//重绘窗口
		this.repaint();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
