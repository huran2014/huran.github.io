package jpanel;
import java.awt.*;
import javax.swing.*;

public class xuanzekuang extends JFrame{
	JPanel jp1,jp2,jp3;
	JButton jb1,jb2;
	JLabel jl1,jl2;
	JCheckBox jcb1,jcb2,jcb3;
	JRadioButton jrb1,jrb2,jrb3;
	//�����ĵ�ѡ���������ŵ�ButtonGroup��
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
		
		jb1=new JButton("ע���û�");
		jb2=new JButton("ȡ��ע��");
		
		jl1=new JLabel("��ϲ�����˶�");
		jl2=new JLabel("   ����Ա�   ");
		
		jcb1=new JCheckBox("����");
		jcb2=new JCheckBox("����");
		jcb3=new JCheckBox("����");
		
		jrb1=new JRadioButton(" ��  ");
		jrb2=new JRadioButton(" Ů  ");
		jrb3=new JRadioButton("����");
		//�����úõĵ�ѡ������ŵ�ButtonGroup��
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
		
		this.setTitle("�û�ע�����");
		this.setSize(320,140);
		this.setLocation(400,400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
