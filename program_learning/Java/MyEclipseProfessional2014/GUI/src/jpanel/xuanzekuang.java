package jpanel;
import java.awt.*;
import javax.swing.*;

public class xuanzekuang extends JFrame{
	JPanel jp1,jp2,jp3;
	JButton jb1,jb2;
	JLabel jl1,jl2;
	JCheckBox jcb1,jcb2,jcb3;
	JRadioButton jrb1,jrb2,jrb3;
	//建立的单选框组件必须放到ButtonGroup中
	ButtonGroup bg;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		xuanzekuang x=new xuanzekuang();
	}
	public xuanzekuang()
	{
		jp1=new JPanel();
		jp2=new JPanel();
		jp3=new JPanel();
		
		jb1=new JButton("注册用户");
		jb2=new JButton("取消注册");
		
		jl1=new JLabel("你喜欢的运动");
		jl2=new JLabel("   你的性别   ");
		
		jcb1=new JCheckBox("足球");
		jcb2=new JCheckBox("篮球");
		jcb3=new JCheckBox("网球");
		
		jrb1=new JRadioButton(" 男  ");
		jrb2=new JRadioButton(" 女  ");
		jrb3=new JRadioButton("其他");
		//将设置好的单选框组件放到ButtonGroup中
		bg=new ButtonGroup();
		bg.add(jrb1);
		bg.add(jrb2);
		bg.add(jrb3);
		
		jp1.add(jl1);
		jp1.add(jcb1);
		jp1.add(jcb2);
		jp1.add(jcb3);
		jp2.add(jl2);
		jp2.add(jrb1);
		jp2.add(jrb2);
		jp2.add(jrb3);
		jp3.add(jb1);
		jp3.add(jb2);
		
		this.setLayout(new GridLayout(3,1));
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		
		this.setTitle("用户注册界面");
		this.setSize(320,140);
		this.setLocation(400,400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
