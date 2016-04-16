package jpanel;
import java.awt.*;

import javax.swing.*;

public class xuanxiangka extends JFrame{
	//北部区域
	JLabel jl1;
	
	//中部区域
	JTabbedPane jtp;
	JPanel jp1,jp2,jp3;
	JLabel jl2,jl3,jl4,jl5,jl21,jl31,jl41,jl51,jl22,jl32,jl42,jl52;
	JButton jb1,jb11,jb12;
	JCheckBox jcb1,jcb2,jcb11,jcb12,jcb21,jcb22;
	JTextField jtf,jtf1,jtf2;
	JPasswordField jpf,jpf1,jpf2;
	
	//南部区域
	JButton jb2,jb3;
	JPanel jp4;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		xuanxiangka xxk=new xuanxiangka();		
	}
	public xuanxiangka()
	{
		//北部区域
		jl1=new JLabel("欢迎来到腾讯qq",JLabel.CENTER);
		//设置字体样式
		jl1.setFont(new Font("宋体",Font.PLAIN,23));
		//设置字体颜色
		jl1.setForeground(Color.BLUE);
		
		//中部区域
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		//qq号码登陆
		jl2=new JLabel("QQ号码",JLabel.CENTER);
		jl3=new JLabel("QQ密码",JLabel.CENTER);
		jl4=new JLabel("忘记密码",JLabel.CENTER);
		//设置字体样式
		jl4.setFont(new Font("宋体",Font.PLAIN,16));
		//设置字体颜色
		jl4.setForeground(Color.BLUE);
		//添加超链接
		jl5=new JLabel("<html><a href='www.qq.com'>申请密码保护</a>");
		
		jtf=new JTextField(16);
		jpf=new JPasswordField(16);
		
		jb1=new JButton("清除号码");
		
		jcb1=new JCheckBox("隐身登陆");
		jcb2=new JCheckBox("记住密码");
		//手机号码登陆
		jl21=new JLabel("手机号码",JLabel.CENTER);
		jl31=new JLabel("手机密码",JLabel.CENTER);
		jl41=new JLabel("忘记密码",JLabel.CENTER);
		//设置字体样式
		jl41.setFont(new Font("宋体",Font.PLAIN,16));
		//设置字体颜色
		jl41.setForeground(Color.BLUE);
		//添加超链接
		jl51=new JLabel("<html><a href='http://www.qq.com/'>申请密码保护</a>");
		jtf1=new JTextField(16);
		jpf1=new JPasswordField(16);
		jb11=new JButton("清除号码");
		jcb11=new JCheckBox("隐身登陆");
		jcb21=new JCheckBox("记住密码");
		//电子邮箱登陆
		jl22=new JLabel("电子邮箱",JLabel.CENTER);
		jl32=new JLabel("邮箱密码",JLabel.CENTER);
		jl42=new JLabel("忘记密码",JLabel.CENTER);
		//设置字体样式
		jl42.setFont(new Font("宋体",Font.PLAIN,16));
		//设置字体颜色
		jl42.setForeground(Color.BLUE);
		//添加超链接
		jl52=new JLabel("<html><a href='www.qq.com'>申请密码保护</a>");
		jtf2=new JTextField(16);
		jpf2=new JPasswordField(16);
		jb12=new JButton("清除号码");
		jcb12=new JCheckBox("隐身登陆");
		jcb22=new JCheckBox("记住密码");
		
		//设置面板的布局
		jp1.setLayout(new GridLayout(3,3));
		jp2.setLayout(new GridLayout(3,3));
		jp3.setLayout(new GridLayout(3,3));
		//将这些组件添加到面板中
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
		//添加页签组件
		jtp=new JTabbedPane();
		jtp.add("QQ号码",jp1);
		jtp.add("手机号码",jp2);
		jtp.add("电子邮箱",jp3);
		
		//设置南部区域
		jb2=new JButton("登陆");
		jb3=new JButton("取消");
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
