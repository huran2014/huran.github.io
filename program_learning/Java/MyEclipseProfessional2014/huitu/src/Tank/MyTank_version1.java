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
class MyPanel2 extends JPanel implements KeyListener
{
	//�����Լ���̹��
	MyTank2 mytank2=null;
	//������˵�̹��
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
		//���Ƶ���̹��
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
			//��ʼ��̹��
			//�Ȼ�����
			g.fillRect(x, y, 10, 30);
			g.fillRect(x+20, y, 10, 30);
			//�ٻ�����
			g.fillRect(x+5, y+5, 20, 20);
			//�ٻ���̨
			g.fillOval(x+10, y+10, 10, 10);
			g.fillOval(x+12,y+12 , 6, 6);
			//���Ʊ߿�
			g.setColor(Color.pink);
			//�Ȼ�����
			g.drawRect(x, y, 10, 30);
			g.drawRect(x+20, y, 10, 30);
			//�ٻ�����
			g.drawRect(x+5, y+5, 20, 20);
			//�ٻ���̨
			g.drawOval(x+10, y+10, 10, 10);
			g.drawOval(x+12,y+12 , 6, 6);
			//�����Ͳ
			g.drawLine(x+15, y-3, x+15, y+12);
			break;
		case 1:
			//�Ȼ�����
			g.fillRect(x, y, 30, 10);
			g.fillRect(x, y+20, 30, 10);
			//�ٻ�����
			g.fillRect(x+5, y+5, 20, 20);
			//�ٻ���̨
			g.fillOval(x+10, y+10, 10, 10);
			g.fillOval(x+12,y+12 , 6, 6);
			//���Ʊ߿�
			g.setColor(Color.pink);
			//�Ȼ�����
			g.drawRect(x, y, 30, 10);
			g.drawRect(x, y+20, 30, 10);
			//�ٻ�����
			g.drawRect(x+5, y+5, 20, 20);
			//�ٻ���̨
			g.drawOval(x+10, y+10, 10, 10);
			g.drawOval(x+12,y+12 , 6, 6);
			//�����Ͳ
			g.drawLine(x+18, y+15, x+33, y+15);
			break;
		case 2:
			//�Ȼ�����
			g.fillRect(x, y, 10, 30);
			g.fillRect(x+20, y, 10, 30);
			//�ٻ�����
			g.fillRect(x+5, y+5, 20, 20);
			//�ٻ���̨
			g.fillOval(x+10, y+10, 10, 10);
			g.fillOval(x+12,y+12 , 6, 6);
			//���Ʊ߿�
			g.setColor(Color.pink);
			//�Ȼ�����
			g.drawRect(x, y, 10, 30);
			g.drawRect(x+20, y, 10, 30);
			//�ٻ�����
			g.drawRect(x+5, y+5, 20, 20);
			//�ٻ���̨
			g.drawOval(x+10, y+10, 10, 10);
			g.drawOval(x+12,y+12 , 6, 6);
			//�����Ͳ
			g.drawLine(x+15, y+18, x+15, y+33);
			break;
		case 3:
			//�Ȼ�����
			g.fillRect(x, y, 30, 10);
			g.fillRect(x, y+20, 30, 10);
			//�ٻ�����
			g.fillRect(x+5, y+5, 20, 20);
			//�ٻ���̨
			g.fillOval(x+10, y+10, 10, 10);
			g.fillOval(x+12,y+12 , 6, 6);
			//���Ʊ߿�
			g.setColor(Color.pink);
			//�Ȼ�����
			g.drawRect(x, y, 30, 10);
			g.drawRect(x, y+20, 30, 10);
			//�ٻ�����
			g.drawRect(x+5, y+5, 20, 20);
			//�ٻ���̨
			g.drawOval(x+10, y+10, 10, 10);
			g.drawOval(x+12,y+12 , 6, 6);
			//�����Ͳ
			g.drawLine(x-3, y+15, x+12, y+15);
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
		else
		{
			this.mytank2.setMove(3);
			this.mytank2.moveleft();
		}
		//�ػ洰��
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
