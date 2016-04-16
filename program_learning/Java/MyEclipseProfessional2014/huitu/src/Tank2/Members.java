package Tank2;

import java.util.Vector;

//创造一个子弹类
class Bullet implements Runnable
{
	int x;
	int y;
	int move;
	int bulletspeed;
	//判断子弹是否存活
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
			//判断子弹死亡
			if(x<0||y<0||x>400||y>300)
			{
				bullet_live=false;
				break;
			}
		}
	}
}

//创造一个坦克类
class Tank2
{
	int x=0;
	int y=0;
	//设置坦克移动，0为上，1为右，2为下，3为左；
	int move=0;
	//设置坦克速度
	int speed=1;
	//判断坦克是否死亡
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

//创造敌人的坦克
class EnemyTank2 extends Tank2
{
	//Bullet bullet=null;
	//定义bullet向量
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
		//启动子弹
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

//创造我的坦克
//设计自己的特色坦克
class MyTank2 extends Tank2
{
	//定义bullet向量
	Vector<Bullet> bullet_vector=new Vector<Bullet>();
	Bullet bullet=null;
	//Bullet bullet=null;
	int bulletspeed=5;
	//创造坦克
	public MyTank2(int x,int y)
	{
		super(x,y);
	}
	//开火
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
		//启动子弹
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