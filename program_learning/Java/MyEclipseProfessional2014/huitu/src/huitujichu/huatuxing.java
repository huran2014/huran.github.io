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

//����һ���Լ���panel�������Լ���ͼ
class MyPanel extends JPanel
{
	//����JPanel��paint����
	//Graphics�ǻ�ͼ����Ҫ�࣬��������һ������
	public void paint(Graphics g)
	{
		//1.���ø��ຯ����ɳ�ʼ��
		//������
		super.paint(g);
//		//��Բ
//		g.setColor(Color.red);
//		g.drawOval(20, 20, 230, 230);
//		//��ֱ��
//		g.setColor(Color.green);
//		g.drawLine(20, 20, 250, 250);
//		//������
//		g.setColor(Color.blue);
//		g.drawRect(20, 20, 230, 230);
//		//��������
//		g.setColor(Color.gray);
//		g.fillRect(20, 20, 230, 230);
//		//����Բ
//		g.setColor(Color.pink);
//		g.fillOval(20, 20, 230, 230);
		//��ͼƬ
		Image im=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/7.jpg"));
		g.drawImage(im, 20, 20,200 , 300,this);
		//������
//		g.setColor(Color.yellow);
//		g.setFont(new Font("���Ĳ���",Font.BOLD,70));
//		g.drawString("��ɹ���",50, 100);
		
	}
}