package Tank2;

import java.util.Vector;

//����һ���ӵ���
class Bullet implements Runnable
{
	int x;
	int y;
	int move;
	int bulletspeed;
	//�ж��ӵ��Ƿ���
	boolean bullet_live=true;
	public Bullet(int x,int y,int move,int bulletspeed)
	{
		this.x=x;
		this.y=y;
		this.move=move;
		this.bulletspeed=bulletspeed;
	}
	public void run()
	{
		while(true)
		{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(move)
			{
			case 0:
				y-=bulletspeed;
				break;
			case 1:
				x+=bulletspeed;
				break;
			case 2:
				y+=bulletspeed;
				break;
			case 3:
				x-=bulletspeed;
				break;
			}
			//�ж��ӵ�����
			if(x<0||y<0||x>400||y>300)
			{
				bullet_live=false;
				break;
			}
		}
	}
}

//����һ��̹����
class Tank2
{
	int x=0;
	int y=0;
	//����̹���ƶ���0Ϊ�ϣ�1Ϊ�ң�2Ϊ�£�3Ϊ��
	int move=0;
	//����̹���ٶ�
	int speed=1;
	//�ж�̹���Ƿ�����
	boolean tank_live=true;
	
	public boolean isTank_live() {
		return tank_live;
	}
	public void setTank_live(boolean tank_live) {
		this.tank_live = tank_live;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getMove() {
		return move;
	}
	public void setMove(int move) {
		this.move = move;
	}
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
	public Tank2(int x,int y)
	{
		this.x=x;
		this.y=y;
	}
}

//������˵�̹��
class EnemyTank2 extends Tank2
{
	//Bullet bullet=null;
	//����bullet����
	Vector<Bullet> bullet_vector=new Vector<Bullet>();
	Bullet bullet=null;
	int bulletspeed=3;
	public EnemyTank2(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	public void enemyfire()
	{
		switch(this.move)
		{
		case 0:
			bullet=new Bullet(x+10,y-3,0,bulletspeed);
			bullet_vector.add(bullet);
			break;
		case 1:
			bullet=new Bullet(x+28,y+15,1,bulletspeed);
			bullet_vector.add(bullet);
			break;
		case 2:
			bullet=new Bullet(x+10,y+33,2,bulletspeed);
			bullet_vector.add(bullet);
			break;
		case 3:
			bullet=new Bullet(x-8,y+15,3,bulletspeed);
			bullet_vector.add(bullet);
		}
		//�����ӵ�
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread t=new Thread(bullet);
		t.start();
	}
}

//�����ҵ�̹��
//����Լ�����ɫ̹��
class MyTank2 extends Tank2
{
	//����bullet����
	Vector<Bullet> bullet_vector=new Vector<Bullet>();
	Bullet bullet=null;
	//Bullet bullet=null;
	int bulletspeed=5;
	//����̹��
	public MyTank2(int x,int y)
	{
		super(x,y);
	}
	//����
	public void fire()
	{
		
		switch(this.move)
		{
		case 0:
			bullet=new Bullet(x+10,y-3,0,bulletspeed);
			bullet_vector.add(bullet);
			break;
		case 1:
			bullet=new Bullet(x+28,y+15,1,bulletspeed);
			bullet_vector.add(bullet);
			break;
		case 2:
			bullet=new Bullet(x+10,y+33,2,bulletspeed);
			bullet_vector.add(bullet);
			break;
		case 3:
			bullet=new Bullet(x-8,y+15,3,bulletspeed);
			bullet_vector.add(bullet);
			break;
		}
		//�����ӵ�
		Thread t=new Thread(bullet);
		t.start();
	}
	
	public void moveup()
	{
		y-=speed;
		if(y<0) y+=speed;
	}
	public void moveright()
	{
		x+=speed;
		if(x>370) x-=speed;
	}
	public void movedown()
	{
		y+=speed;
		if(y>270) y-=speed;
	}
	public void moveleft()
	{
		x-=speed;
		if(x<0) x+=speed;
	}
}