package jpanel;
import java.awt.*;

import javax.swing.*;

public class xuanxiangka extends JFrame{
	//��������
	JLabel jl1;
	
	//�в�����
	JTabbedPane jtp;
	JPanel jp1,jp2,jp3;
	JLabel jl2,jl3,jl4,jl5,jl21,jl31,jl41,jl51,jl22,jl32,jl42,jl52;
	JButton jb1,jb11,jb12;
	JCheckBox jcb1,jcb2,jcb11,jcb12,jcb21,jcb22;
	JTextField jtf,jtf1,jtf2;
	JPasswordField jpf,jpf1,jpf2;
	
	//�ϲ�����
	JButton jb2,jb3;
	JPanel jp4;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		xuanxiangka xxk=new xuanxiangka();		
	}
	public xuanxiangka()
	{
		//��������
		jl1=new JLabel("��ӭ������Ѷqq",JLabel.CENTER);
		//����������ʽ
		jl1.setFont(new Font("����",Font.PLAIN,23));
		//����������ɫ
		jl1.setForeground(Color.BLUE);
		
		//�в�����
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		//qq�����½
		jl2=new JLabel("QQ����",JLabel.CENTER);
		jl3=new JLabel("QQ����",JLabel.CENTER);
		jl4=new JLabel("��������",JLabel.CENTER);
		//����������ʽ
		jl4.setFont(new Font("����",Font.PLAIN,16));
		//����������ɫ
		jl4.setForeground(Color.BLUE);
		//��ӳ�����
		jl5=new JLabel("<html><a href='www.qq.com'>�������뱣��</a>");
		
		jtf=new JTextField(16);
		jpf=new JPasswordField(16);
		
		jb1=new JButton("�������");
		
		jcb1=new JCheckBox("�����½");
		jcb2=new JCheckBox("��ס����");
		//�ֻ������½
		jl21=new JLabel("�ֻ�����",JLabel.CENTER);
		jl31=new JLabel("�ֻ�����",JLabel.CENTER);
		jl41=new JLabel("��������",JLabel.CENTER);
		//����������ʽ
		jl41.setFont(new Font("����",Font.PLAIN,16));
		//����������ɫ
		jl41.setForeground(Color.BLUE);
		//��ӳ�����
		jl51=new JLabel("<html><a href='http://www.qq.com/'>�������뱣��</a>");
		jtf1=new JTextField(16);
		jpf1=new JPasswordField(16);
		jb11=new JButton("�������");
		jcb11=new JCheckBox("�����½");
		jcb21=new JCheckBox("��ס����");
		//���������½
		jl22=new JLabel("��������",JLabel.CENTER);
		jl32=new JLabel("��������",JLabel.CENTER);
		jl42=new JLabel("��������",JLabel.CENTER);
		//����������ʽ
		jl42.setFont(new Font("����",Font.PLAIN,16));
		//����������ɫ
		jl42.setForeground(Color.BLUE);
		//��ӳ�����
		jl52=new JLabel("<html><a href='www.qq.com'>�������뱣��</a>");
		jtf2=new JTextField(16);
		jpf2=new JPasswordField(16);
		jb12=new JButton("�������");
		jcb12=new JCheckBox("�����½");
		jcb22=new JCheckBox("��ס����");
		
		//�������Ĳ���
		jp1.setLayout(new GridLayout(3,3));
		jp2.setLayout(new GridLayout(3,3));
		jp3.setLayout(new GridLayout(3,3));
		//����Щ�����ӵ������
		jp1.add(jl2);
		jp1.add(jtf);
		jp1.add(jb1);
		jp1.add(jl3);
		jp1.add(jpf);
		jp1.add(jl4);
		jp1.add(jcb1);
		jp1.add(jcb2);
		jp1.add(jl5);
		
		jp2.add(jl21);
		jp2.add(jtf1);
		jp2.add(jb11);
		jp2.add(jl31);
		jp2.add(jpf1);
		jp2.add(jl41);
		jp2.add(jcb11);
		jp2.add(jcb21);
		jp2.add(jl51);
		
		jp3.add(jl22);
		jp3.add(jtf2);
		jp3.add(jb12);
		jp3.add(jl32);
		jp3.add(jpf2);
		jp3.add(jl42);
		jp3.add(jcb12);
		jp3.add(jcb22);
		jp3.add(jl52);
		//���ҳǩ���
		jtp=new JTabbedPane();
		jtp.add("QQ����",jp1);
		jtp.add("�ֻ�����",jp2);
		jtp.add("��������",jp3);
		
		//�����ϲ�����
		jb2=new JButton("��½");
		jb3=new JButton("ȡ��");
		jp4=new JPanel();
		jp4.add(jb2);
		jp4.add(jb3);
		
		this.add(jl1,BorderLayout.NORTH);
		this.add(jtp,BorderLayout.CENTER);
		this.add(jp4,BorderLayout.SOUTH);
		
		this.setTitle("JTabbedPanel");
		this.setSize(400, 230);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
