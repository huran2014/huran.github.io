package jpanel;
import java.awt.*;

import javax.swing.*;

public class chaifenchuangkou extends JFrame{
	JSplitPane jsp;
	JList jlist;
	JScrollPane jsp1;
	JLabel jl;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		chaifenchuangkou cfck=new chaifenchuangkou();
	}
	public chaifenchuangkou()
	{
		String test[]=new String[100];
		for(int i=0;i<test.length;i++)
		{
			test[i]=String.valueOf(i);
		}
		jlist=new JList(test);
		jsp1=new JScrollPane(jlist);
		
		//���ͼƬ
		jl=new JLabel(new ImageIcon(getClass().getResource("/7.jpg")));
		
		//��ִ���
		jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp1,jl);
		//������߿���Ա仯
		jsp.setOneTouchExpandable(true);
		
		this.add(jsp);
		
		this.setTitle("JSplitPane");
		this.setSize(400, 300);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
