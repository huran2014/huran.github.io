package Tank;

//����һ��̹����
class Tank2
{
	int x=0;
	int y=0;
	//����̹���ƶ���0Ϊ�ϣ�1Ϊ�ң�2Ϊ�£�3Ϊ��
	int move=0;
	//����̹���ٶ�
	int speed=3;
	
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
	public EnemyTank2(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
}

//�����ҵ�̹��
//����Լ�����ɫ̹��
class MyTank2 extends Tank2
{
	public MyTank2(int x,int y)
	{
		super(x,y);
	}
	public void moveup()
	{
		y-=speed;
	}
	public void moveright()
	{
		x+=speed;
	}
	public void movedown()
	{
		y+=speed;
	}
	public void moveleft()
	{
		x-=speed;
	}
}