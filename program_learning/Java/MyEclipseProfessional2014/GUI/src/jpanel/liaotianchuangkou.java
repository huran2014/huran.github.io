package jpanel;
import java.awt.*;
import javax.swing.*;

public class liaotianchuangkou extends JFrame{
	JPanel jp;
	JTextArea jta;
	JScrollPane jsp;
	JComboBox jcb;
	JTextField jtf;
	JButton jb;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		liaotianchuangkou ltck=new liaotianchuangkou();
	}
	public liaotianchuangkou()
	{
		jp=new JPanel();
		jta=new JTextArea();
		jsp=new JScrollPane(jta);
		String word[]={"¬¿√Œ—Ù","÷Ï≈Ù","÷Ï√Ù","¿Ó¿÷¿÷"};
		jcb=new JComboBox(word);
		jtf=new JTextField(10);
		jb=new JButton("∑¢ÀÕ");
		
		jp.add(jcb);
		jp.add(jtf);
		jp.add(jb);
		
		this.add(jsp,BorderLayout.CENTER);
		this.add(jp,BorderLayout.SOUTH);
		
		this.setTitle("JTextArea");
		this.setIconImage(new ImageIcon("image\\1.gif").getImage());
		this.setSize(400, 300);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
