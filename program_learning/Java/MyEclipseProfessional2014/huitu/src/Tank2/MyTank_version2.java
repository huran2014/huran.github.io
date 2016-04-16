/**
 * ����ʵ���ҵ�̹���������߲����ӵ�������Ҳ��3�����˵�̹��
 * �̲߳�����ͬʱ��������
 * �ҵ�̹�˿��Է������޵��ڵ������ҿ��Ի��ٵ��˵�̹��
 * 1��̹�˲��ܱ��߱߷��ڵ�
 * 2��̹���޷��趨�����ڵ���Ƶ��
 * 3������̹���������޷�ɾ������
 */
package Tank2;
import java.awt.*;

import javax.swing.*;

import java.util.*;
import java.awt.event.*;

public class MyTank_version2 extends JFrame{
	MyPanel2 mp2=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTank_version2 t=new MyTank_version2();
	}
	public MyTank_version2()
	{
		mp2=new MyPanel2();
		//����mp2�߳�
		Thread t1=new Thread(mp2);
		t1.start();
		//ע�����
		this.addKeyListener(mp2);
		this.add(mp2);
		this.setSize(400, 300);
		this.setLocation(120, 80);
		this.setTitle("mytank1");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

//�����Լ������
class MyPanel2 extends JPanel implements KeyListener,Runnable
{
	//�����Լ���̹��
	MyTank2 mytank2=null;
	//������˵�̹������
	Vector<EnemyTank2> ets=new Vector<EnemyTank2>();
	//�������̹����Ŀ
	int enemytanknum=3;
	
	//���캯��
	public  MyPanel2()
	{
		mytank2=new MyTank2(100,75);
		//��ʼ������̹��
		for(int i=0;i<enemytanknum;i++)
		{
			EnemyTank2 et=new EnemyTank2((i+1)*50,0);
			et.setMove(2); 
			ets.add(et);
		}
	}
	public void paint(Graphics g)
	{
		//��ʼ��������д
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		//��ʼ��̹��
		this.drawtank(mytank2.getX(), mytank2.getY(), g,mytank2.getMove(),0);
		
		for(int i=0;i<mytank2.bullet_vector.size();i++)
		{
			Bullet mytank2_bullet=mytank2.bullet_vector.get(i);
			//����ֻ�ܻ��Ƴ�һ���ӵ�
			if(mytank2_bullet!=null&&mytank2_bullet.bullet_live==true)
			{
				g.fillOval(mytank2_bullet.x-2, mytank2_bullet.y-2, 4, 4);
			}
			if(mytank2_bullet.bullet_live==false)
			{
				//�Ƴ��������ӵ�
				mytank2.bullet_vector.remove(mytank2_bullet);
			}
		}
		//���Ƶ���̹��
		for(int i=0;i<enemytanknum;i++)
		{
			EnemyTank2 et=ets.get(i);
			//�жϵ��˵�̹���Ƿ������ɾ��
			if(et.tank_live)
			{
				this.drawtank(ets.get(i).getX(), ets.get(i).getY(), g, ets.get(i).getMove(), 1);
			}
		}
	}
	
	//�жϵ���̹���Ƿ�����
	public void killTank(Bullet bullet,Tank2 tank)
	{
		//�����ж�̹�˵ķ��򣬲�ͬ�ķ���������Ŀռ䲻ͬ
		switch(tank.move)
		{
		case 0|2:
			if(bullet.x>tank.x&&bullet.x<tank.x+20&&bullet.y>tank.y&&bullet.y<tank.y+30)
			{
				//��ʱ̹�˱�����
				//�ӵ�����
				bullet.bullet_live=false;
				//̹������
				tank.tank_live=false;
				break;
			}
		case 1|3:
			if(bullet.x>tank.x&&bullet.x<tank.x+30&&bullet.y>tank.y&&bullet.y<tank.y+20)
			{
				//��ʱ̹�˱�����
				//�ӵ�����
				bullet.bullet_live=false;
				//̹������
				tank.tank_live=false;
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
			//��ʼ��̹��
			//�Ȼ�����
			g.fillRect(x, y, 5, 30);
			g.fillRect(x+15, y, 5, 30);
			//�ٻ�����
			g.fillRect(x, y+5, 20, 20);
			//�ٻ���̨
			g.fillOval(x+5, y+10, 10, 10);
			g.fillOval(x+7,y+12 , 6, 6);
			//���Ʊ߿�
			g.setColor(Color.pink);
			//�Ȼ�����
			g.drawRect(x, y, 5, 30);
			g.drawRect(x+15, y, 5, 30);
			//�ٻ�����
			g.drawRect(x, y+5, 20, 20);
			//�ٻ���̨
			g.drawOval(x+5, y+10, 10, 10);
			g.drawOval(x+7,y+12 , 6, 6);
			//�����Ͳ
			g.drawLine(x+10, y-3, x+10, y+12);
			break;
		case 1:
			//�Ȼ�����
			g.fillRect(x-5, y+5, 30, 5);
			g.fillRect(x-5, y+20, 30, 5);
			//�ٻ�����
			g.fillRect(x, y+5, 20, 20);
			//�ٻ���̨
			g.fillOval(x+5, y+10, 10, 10);
			g.fillOval(x+7,y+12 , 6, 6);
			//���Ʊ߿�
			g.setColor(Color.pink);
			//�Ȼ�����
			g.drawRect(x-5, y+5, 30, 5);
			g.drawRect(x-5, y+20, 30, 5);
			//�ٻ�����
			g.drawRect(x, y+5, 20, 20);
			//�ٻ���̨
			g.drawOval(x+5, y+10, 10, 10);
			g.drawOval(x+7,y+12 , 6, 6);
			//�����Ͳ
			g.drawLine(x+13, y+15, x+28, y+15);
			break;
		case 2:
			//�Ȼ�����
			g.fillRect(x, y, 5, 30);
			g.fillRect(x+15, y, 5, 30);
			//�ٻ�����
			g.fillRect(x, y+5, 20, 20);
			//�ٻ���̨
			g.fillOval(x+5, y+10, 10, 10);
			g.fillOval(x+7,y+12 , 6, 6);
			//���Ʊ߿�
			g.setColor(Color.pink);
			//�Ȼ�����
			g.drawRect(x, y, 5, 30);
			g.drawRect(x+15, y, 5, 30);
			//�ٻ�����
			g.drawRect(x, y+5, 20, 20);
			//�ٻ���̨
			g.drawOval(x+5, y+10, 10, 10);
			g.drawOval(x+7,y+12 , 6, 6);
			//�����Ͳ
			g.drawLine(x+10, y+18, x+10, y+33);
			break;
		case 3:
			//�Ȼ�����
			g.fillRect(x-5, y+5, 30, 5);
			g.fillRect(x-5, y+20, 30, 5);
			//�ٻ�����
			g.fillRect(x, y+5, 20, 20);
			//�ٻ���̨
			g.fillOval(x+5, y+10, 10, 10);
			g.fillOval(x+7,y+12 , 6, 6);
			//���Ʊ߿�
			g.setColor(Color.pink);
			//�Ȼ�����
			g.drawRect(x-5, y+5, 30, 5);
			g.drawRect(x-5, y+20, 30, 5);
			//�ٻ�����
			g.drawRect(x, y+5, 20, 20);
			//�ٻ���̨
			g.drawOval(x+5, y+10, 10, 10);
			g.drawOval(x+7,y+12 , 6, 6);
			//�����Ͳ
			g.drawLine(x-8, y+15, x+7, y+15);
			break;
		}
		
	}
	//��ⰴ�������� wΧ�ϣ�sΪ�£�aΪ��dΪ��
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
		//�ж��Ƿ񿪻�
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			//���𣬶���ֻ��ͬʱ��8���ڵ�
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
//		//�ػ洰��
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
		//ÿ��50ms�ػ�
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//�ж�̹���Ƿ񱻻���
			for(int i=0;i<mytank2.bullet_vector.size();i++)
			{
				//����ȡ���ӵ�
				Bullet bullet=mytank2.bullet_vector.get(i);
				//�ж�̹���Ƿ�����
				if(bullet.bullet_live)
				{
					for(int j=0;j<ets.size();j++)
					{
						//ȡ��̹�ˣ��ж�̹���Ƿ�����
						EnemyTank2 enemytank=ets.get(j); 
						this.killTank(bullet, enemytank);
						if(!enemytank.isTank_live())
						{
							ets.remove(enemytank);
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
