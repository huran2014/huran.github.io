package jpanel;
import java.awt.*;

import javax.swing.*;

public class dengluchuangkou extends JFrame{
	//面板组件
	JPanel jp1,jp2,jp3;
	//文本框组件
	JTextField jtextfield;
	//密码框组件
	JPasswordField jpasswordfield;
	//标签组件
	JLabel jlabel1,jlabel2;
	//按钮组件
	JButton jb1,jb2;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dengluchuangkou d=new dengluchuangkou();
	}
	public dengluchuangkou()
	{
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		//附加宽度
		jtextfield=new JTextField(10);
		//附加宽度
		jpasswordfield=new JPasswordField(10);
		
		jlabel1=new JLabel("管理员");
		jlabel2=new JLabel("密   码");
		
		jb1=new JButton("确认");
		jb2=new JButton("取消");
		
		jp1.add(jlabel1);
		jp1.add(jtextfield);
		jp2.add(jlabel2);
		jp2.add(jpasswordfield);
		jp3.add(jb1);
		jp3.add(jb2);
		
		//设置布局
		this.setLayout(new GridLayout(3,1));
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		
		this.setTitle("会员管理系统");
		this.setSize(320, 140);
		this.setResizable(false);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
