package jpanel;
import java.awt.*;

import javax.swing.*;

public class duoxuankuang extends JFrame{
	JPanel jp1,jp2;
	JLabel jl1,jl2;
	//�б�����
	JList jlist;
	//���������
	JComboBox jcb;
	//���������
	JScrollPane jsp;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		duoxuankuang d=new duoxuankuang();
	}
	public duoxuankuang()
	{
		jp1=new JPanel();
		jp2=new JPanel();
		
		jl1=new JLabel("���");
		jl2=new JLabel("�·�");
		
		///*
		String []year=new String[50];
		int yearb=1965;
		for(int i=0;i<year.length;i++,yearb++)
		{
			year[i]=String.valueOf(yearb);
		}
		jcb=new JComboBox(year);
		//*/
		/*
		String []jg={"����","�Ϻ�","���"};
		jcb=new JComboBox(jg);
		*/
		
		String []month=new String[12];
		for(int i=0;i<month.length;i++)
		{
			month[i]=String.valueOf(i+1);
		}
		jlist=new JList(month);
		//������ʾ����������Ϲ������ʹ��
		jlist.setVisibleRowCount(3);
		jsp=new JScrollPane(jlist);
		
		jp1.add(jl1);
		jp1.add(jcb);
		jp2.add(jl2);
		jp2.add(jsp);
		
		this.setLayout(new GridLayout(2,1));
		
		this.add(jp1);
		this.add(jp2);
		
		this.setSize(400, 180);
		this.setLocation(200, 200);
		this.setTitle("ѡ���");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
}
