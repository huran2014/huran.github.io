package huitujichu;
import java.awt.*;
import javax.swing.*;

public class huatuxing extends JFrame{
	MyPanel mp=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		huatuxing hy=new huatuxing();
	}
	public huatuxing()
	{
		mp=new MyPanel();
		
		this.add(mp);
		this.setSize(400, 300);
		this.setLocation(120, 80);
		this.setTitle("huituxing");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

//定义一个自己的panel，方便自己绘图
class MyPanel extends JPanel
{
	//覆盖JPanel的paint方法
	//Graphics是绘图的重要类，可以理解成一个画笔
	public void paint(Graphics g)
	{
		//1.调用父类函数完成初始化
		//不能少
		super.paint(g);
//		//画圆
//		g.setColor(Color.red);
//		g.drawOval(20, 20, 230, 230);
//		//画直线
//		g.setColor(Color.green);
//		g.drawLine(20, 20, 250, 250);
//		//画矩形
//		g.setColor(Color.blue);
//		g.drawRect(20, 20, 230, 230);
//		//画填充矩形
//		g.setColor(Color.gray);
//		g.fillRect(20, 20, 230, 230);
//		//画椭圆
//		g.setColor(Color.pink);
//		g.fillOval(20, 20, 230, 230);
		//画图片
		Image im=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/7.jpg"));
		g.drawImage(im, 20, 20,200 , 300,this);
		//画出字
//		g.setColor(Color.yellow);
//		g.setFont(new Font("华文彩云",Font.BOLD,70));
//		g.drawString("你成功了",50, 100);
		
	}
}