package jpanel;
import java.awt.*;

import javax.swing.*;

public class dengluchuangkou extends JFrame{
	//������
	JPanel jp1,jp2,jp3;
	//�ı������
	JTextField jtextfield;
	//��������
	JPasswordField jpasswordfield;
	//��ǩ���
	JLabel jlabel1,jlabel2;
	//��ť���
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
		//���ӿ��
		jtextfield=new JTextField(10);
		//���ӿ��
		jpasswordfield=new JPasswordField(10);
		
		jlabel1=new JLabel("����Ա");
		jlabel2=new JLabel("��   ��");
		
		jb1=new JButton("ȷ��");
		jb2=new JButton("ȡ��");
		
		jp1.add(jlabel1);
		jp1.add(jtextfield);
		jp2.add(jlabel2);
		jp2.add(jpasswordfield);
		jp3.add(jb1);
		jp3.add(jb2);
		
		//���ò���
		this.setLayout(new GridLayout(3,1));
		
		this.add(jp1);
		this.add(jp2);
		this.add(jp3);
		
		this.setTitle("��Ա����ϵͳ");
		this.setSize(320, 140);
		this.setResizable(false);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
