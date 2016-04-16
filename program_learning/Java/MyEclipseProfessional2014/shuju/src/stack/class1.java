package stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class class1 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Children c=new Children(5);//��������ջ���ԣ��ܹ����Դ��5������
		while(true)
		{
			System.out.println("��������Ҫ���еĲ�����1��push��2��pop��3��peek��4��quit");
			
			/*****************�������*************************/
			String str;
			int a;
			BufferedReader buf;
			buf=new BufferedReader(new InputStreamReader(System.in));
			str=buf.readLine();
			a=Integer.parseInt(str);

			if(a==4) break;
			switch(a)
			{
			case 1:
				System.out.print("�����뺢�����ƣ�");
				buf=new BufferedReader(new InputStreamReader(System.in));
				str=buf.readLine();
				c.push(str);
				break;
			case 2:
				c.pop();
				break;
			case 3:
				c.peek();
			default:
				break;
			}
		}
		System.out.println("The program is finished!");
	}

}

class Children//�������Ӷ�ջ
{
	private Vector v;//��vector�����ʾ��ջ
	private int max_num;//�༶���������
	
	public Children(int num)//���캯����������������������༶��ջ
	{
		this.max_num=num;
		v=new Vector(max_num);
	}
	
	public void show_children()
	{
		for(int i=0;i<v.size();i++)
		{
			System.out.print(v.get(i)+" ");
		}
		System.out.println();
	}
	
	public void push(String name)//ջ�����������
	{
		if(v.size()>=this.max_num)
		{
			System.out.println("Can't add students any more!");
		}
		else
			v.add(name);
		this.show_children();
	}
	
	public void pop()//ջ�鿴��ɾ�����������
	{
		System.out.println("The student in the top is "+v.get(v.size()-1));
		v.remove(v.size()-1);
		if(v.size()<=0)
		{
			System.out.println("There are no student left.");
		}
		this.show_children();
	}
	
	public void peek()//ջ�鿴���������
	{
		System.out.println("The student in the top is "+v.get(v.size()-1));
	}
}