/**
 * 可以实现我的坦克自由行走并发子弹，而且也有3个敌人的坦克
 * 线程不可以同时启动两次
 * 我的坦克可以发射有限的炮弹，而且可以击毁敌人的坦克
 * 可以循环补充新的坦克
 * 1、坦克不能边走边发炮弹
 * 2、坦克无法设定发射炮弹的频率
 * 3、敌人坦克死亡后无法删除向量
 */
package Tank3;
import java.awt.*;

import javax.swing.*;

import java.util.*;
import java.awt.event.*;

public class MyTank_version3 extends JFrame{
	MyPanel2 mp2=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTank_version3 t=new MyTank_version3();
	}
	public MyTank_version3()
	{
		mp2=new MyPanel2();
		//启动mp2线程
		Thread t1=new Thread(mp2);
		t1.start();
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
class MyPanel2 extends JPanel implements KeyListener,Runnable
{
	//定义自己的坦克
	MyTank2 mytank2=null;
	//定义敌人的坦克向量
	Vector<EnemyTank2> ets=new Vector<EnemyTank2>();
	//定义敌人坦克数目
	int enemytanknum=15;
	//定义一个炸弹集合
	Vector<Bomb> bombs=new Vector<Bomb>();
	
	//定义三张图片
	Image image1=null;
	Image image2=null;
	Image image3=null;
	
	//构造函数
	//初始化函数尽量放在构造函数中
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
		
		//初始化爆炸图片
		image1=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1,gif"));
		image2=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
		image3=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
		
	}
	public void paint(Graphics g)
	{
		//初始化，必须写
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//开始画坦克
		this.drawtank(mytank2.getX(), mytank2.getY(), g,mytank2.getMove(),0);
		
		for(int i=0;i<mytank2.bullet_vector.size();i++)
		{
			Bullet mytank2_bullet=mytank2.bullet_vector.get(i);
			//这样只能绘制出一颗子弹
			if(mytank2_bullet!=null&&mytank2_bullet.bullet_live==true)
			{
				g.fillOval(mytank2_bullet.x-2, mytank2_bullet.y-2, 4, 4);
			}
			if(mytank2_bullet.bullet_live==false)
			{
				//移除死亡的子弹
				mytank2.bullet_vector.remove(mytank2_bullet);
			}
		}
		
		//绘制炸弹
		for(int i=0;i<bombs.size();i++)
		{
			//取出炸弹
			Bomb bomb=bombs.get(i);
			
			if(bomb.bomb_life>6)
			{
				g.drawImage(image1, bomb.x, bomb.y, 30, 30,this);
			}
			else if(bomb.bomb_life>3)
			{
				g.drawImage(image2, bomb.x, bomb.y, 30, 30,this);
			}
			else
			{
				g.drawImage(image3, bomb.x, bomb.y, 30, 30,this);
			}
			
			//绘制完后要让生命值减少
			bomb.bomb_life();
			
			//如果炸弹的生命值为0，就要把这个炸弹从向量中去掉
			if(bomb.bomb_life==0)
			{
				bombs.remove(bomb);
			}
		}
		
		//绘制敌人坦克
		for(int i=0;i<3;i++)
		{
			EnemyTank2 et=ets.get(i);
			//判断敌人的坦克是否存活，否则删除
			if(et.tank_live)
			{
				this.drawtank(ets.get(i).getX(), ets.get(i).getY(), g, ets.get(i).getMove(), 1);
			}
			else
			{
				ets.remove(et);
				//根据新的向量再次判断是否有新的坦克
				EnemyTank2 et1=ets.get(i);
				//判断敌人的坦克是否存活，否则删除
				if(et1.tank_live)
				{
					this.drawtank(ets.get(i).getX(), ets.get(i).getY(), g, ets.get(i).getMove(), 1);
				}
			}
		}
	}
	
	//判断敌人坦克是否死亡
	public void killTank(Bullet bullet,Tank2 tank)
	{
		//首先判断坦克的方向，不同的方向所处理的空间不同
		switch(tank.move)
		{
		case 0|2:
			if(bullet.x>tank.x&&bullet.x<tank.x+20&&bullet.y>tank.y&&bullet.y<tank.y+30)
			{
				//此时坦克被击中
				//子弹死亡
				bullet.bullet_live=false;
				//坦克死亡
				tank.tank_live=false;
				//创造一个炸弹放入Vector中
				Bomb bomb=new Bomb(tank.x,tank.y);
				bombs.add(bomb);
				break;
				
			}
		case 1|3:
			if(bullet.x>tank.x&&bullet.x<tank.x+30&&bullet.y>tank.y&&bullet.y<tank.y+20)
			{
				//此时坦克被击中
				//子弹死亡
				bullet.bullet_live=false;
				//坦克死亡
				tank.tank_live=false;
				//创造一个炸弹放入Vector中
				Bomb bomb=new Bomb(tank.x,tank.y);
				bombs.add(bomb);
				break;
			}
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
			g.fillRect(x, y, 5, 30);
			g.fillRect(x+15, y, 5, 30);
			//再画主体
			g.fillRect(x, y+5, 20, 20);
			//再画炮台
			g.fillOval(x+5, y+10, 10, 10);
			g.fillOval(x+7,y+12 , 6, 6);
			//绘制边框
			g.setColor(Color.pink);
			//先画轮子
			g.drawRect(x, y, 5, 30);
			g.drawRect(x+15, y, 5, 30);
			//再画主体
			g.drawRect(x, y+5, 20, 20);
			//再画炮台
			g.drawOval(x+5, y+10, 10, 10);
			g.drawOval(x+7,y+12 , 6, 6);
			//最后画炮筒
			g.drawLine(x+10, y-3, x+10, y+12);
			break;
		case 1:
			//先画轮子
			g.fillRect(x-5, y+5, 30, 5);
			g.fillRect(x-5, y+20, 30, 5);
			//再画主体
			g.fillRect(x, y+5, 20, 20);
			//再画炮台
			g.fillOval(x+5, y+10, 10, 10);
			g.fillOval(x+7,y+12 , 6, 6);
			//绘制边框
			g.setColor(Color.pink);
			//先画轮子
			g.drawRect(x-5, y+5, 30, 5);
			g.drawRect(x-5, y+20, 30, 5);
			//再画主体
			g.drawRect(x, y+5, 20, 20);
			//再画炮台
			g.drawOval(x+5, y+10, 10, 10);
			g.drawOval(x+7,y+12 , 6, 6);
			//最后画炮筒
			g.drawLine(x+13, y+15, x+28, y+15);
			break;
		case 2:
			//先画轮子
			g.fillRect(x, y, 5, 30);
			g.fillRect(x+15, y, 5, 30);
			//再画主体
			g.fillRect(x, y+5, 20, 20);
			//再画炮台
			g.fillOval(x+5, y+10, 10, 10);
			g.fillOval(x+7,y+12 , 6, 6);
			//绘制边框
			g.setColor(Color.pink);
			//先画轮子
			g.drawRect(x, y, 5, 30);
			g.drawRect(x+15, y, 5, 30);
			//再画主体
			g.drawRect(x, y+5, 20, 20);
			//再画炮台
			g.drawOval(x+5, y+10, 10, 10);
			g.drawOval(x+7,y+12 , 6, 6);
			//最后画炮筒
			g.drawLine(x+10, y+18, x+10, y+33);
			break;
		case 3:
			//先画轮子
			g.fillRect(x-5, y+5, 30, 5);
			g.fillRect(x-5, y+20, 30, 5);
			//再画主体
			g.fillRect(x, y+5, 20, 20);
			//再画炮台
			g.fillOval(x+5, y+10, 10, 10);
			g.fillOval(x+7,y+12 , 6, 6);
			//绘制边框
			g.setColor(Color.pink);
			//先画轮子
			g.drawRect(x-5, y+5, 30, 5);
			g.drawRect(x-5, y+20, 30, 5);
			//再画主体
			g.drawRect(x, y+5, 20, 20);
			//再画炮台
			g.drawOval(x+5, y+10, 10, 10);
			g.drawOval(x+7,y+12 , 6, 6);
			//最后画炮筒
			g.drawLine(x-8, y+15, x+7, y+15);
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
		else if(e.getKeyCode()==KeyEvent.VK_A)
		{
			this.mytank2.setMove(3);
			this.mytank2.moveleft();
		}
		//判断是否开火
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			//开火，而且只能同时有8颗炮弹
			if(mytank2.bullet_vector.size()<8)
			{
				this.mytank2.fire();
			}
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		}
//		//重绘窗口
//		this.repaint();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		//每隔50ms重画
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//判断坦克是否被击中
			for(int i=0;i<mytank2.bullet_vector.size();i++)
			{
				//首先取出子弹
				Bullet bullet=mytank2.bullet_vector.get(i);
				//判断坦克是否死亡
				if(bullet.bullet_live)
				{
					for(int j=0;j<ets.size();j++)
					{
						//取出坦克，判断坦克是否死亡
						EnemyTank2 enemytank=ets.get(j); 
						this.killTank(bullet, enemytank);
						if(!enemytank.isTank_live())
						{
							//ets.remove(enemytank);
							if(enemytanknum>0)
							{
								enemytanknum--;
							}
						}
					}
				}
			}
			
			this.repaint();
		}
	}
}
