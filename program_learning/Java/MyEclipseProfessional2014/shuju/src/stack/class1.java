package stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class class1 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Children c=new Children(5);//创建孩子栈属性，总共可以存放5个孩子
		while(true)
		{
			System.out.println("请输入你要进行的操作：1、push；2、pop；3、peek；4、quit");
			
			/*****************输入操作*************************/
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
				System.out.print("请输入孩子名称：");
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

class Children//创建孩子堆栈
{
	private Vector v;//以vector数组表示堆栈
	private int max_num;//班级最大孩子数量
	
	public Children(int num)//构造函数，根据输入的数量创建班级堆栈
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
	
	public void push(String name)//栈添加数据属性
	{
		if(v.size()>=this.max_num)
		{
			System.out.println("Can't add students any more!");
		}
		else
			v.add(name);
		this.show_children();
	}
	
	public void pop()//栈查看并删除最顶数据属性
	{
		System.out.println("The student in the top is "+v.get(v.size()-1));
		v.remove(v.size()-1);
		if(v.size()<=0)
		{
			System.out.println("There are no student left.");
		}
		this.show_children();
	}
	
	public void peek()//栈查看最顶数据属性
	{
		System.out.println("The student in the top is "+v.get(v.size()-1));
	}
}