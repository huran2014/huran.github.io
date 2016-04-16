/**
 * 功能：约瑟夫问题
 */

package lessen1_1;
import java.io.*;

public class yuesefu {

	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		System.out.println("请输入群组中小孩的个数:");
		/*String str1;
		int a;
		BufferedReader buf1;
		buf1=new BufferedReader(new InputStreamReader(System.in));
		str1=buf1.readLine();
		a=Integer.parseInt(str1);*/
		String str;
		int a;
		BufferedReader buf;
		buf=new BufferedReader(new InputStreamReader(System.in));
		str=buf.readLine();
		a=Integer.parseInt(str);
		System.out.println("请输入从第几个小孩开始数:");
		/*String str2;
		int b;
		BufferedReader buf2;
		buf2=new BufferedReader(new InputStreamReader(System.in));
		str2=buf2.readLine();
		b=Integer.parseInt(str2);*/
		int b=Integer.parseInt(buf.readLine());
		System.out.println("请输入要踢出数到多少数的小孩:");
		/*String str3;
		int c;
		BufferedReader buf3;
		buf3=new BufferedReader(new InputStreamReader(System.in));
		str3=buf3.readLine();
		c=Integer.parseInt(str3);*/
		int c=Integer.parseInt(buf.readLine());
		System.out.println("约瑟夫问题的结果是:");
		CycLink cyclink=new CycLink();
		cyclink.setLen(a);
		cyclink.setFrom(b);
		cyclink.setEnd(c);
		cyclink.createLink();
		cyclink.show();
		cyclink.play();
	}

}

//创建节点
class Child
{
	int num;
	Child nextChild=null;
	public Child(int num)
	{
		//给一个编号
		this.num=num;
	}
}

//环形链表
class CycLink
{
	//先定义一个指向链表第一个小孩的引用
	//指向第一个小孩的引用，不能动
	Child firstChild=null;
	Child temp=null;
	int len=0;//表示小孩的个数
	int from=0;//表示从第from个小孩开始数数
	int end=0;//表示将数到第end个数的小孩踢出圈子
	
	//设置量表大小
	public void setLen(int len)
	{
		this.len=len;
	}
	
	//设置from大小
	public void setFrom(int from)
	{
		this.from=from;
	}
	
	//设置end大小
	public void setEnd(int end)
	{
		this.end=end;
	}
	
	//初始化环形链表
	public void createLink()
	{
		for(int i=1;i<=len;i++)
		{
			if(i==1)
			{
				//创建第一个小孩
				Child ch=new Child(i);
				this.firstChild=ch;
				this.temp=ch;
			}
			else
			{
				if(i==len)
				{
					//创建最后一个小孩
					Child ch=new Child(i);
					temp.nextChild=ch;
					temp=ch;
					temp.nextChild=this.firstChild;
				}
				else
				{
					//继续创建小孩
					Child ch=new Child(i);
					//对于上一个小孩创建其下一个小孩变量
					temp.nextChild=ch;
					//创建当前小孩，其下一个小孩变量由下一个小孩给出
					temp=ch;
				}
			}
			
		}
	}
	
	//开始解决问题
	public void play()
	{
		
		System.out.println("出圈的顺序是:");
		//继续设置跑龙套的人
		Child temp=this.firstChild;
		int n=this.len;//设置循环变量
		
		
		//1.找到开始数数的人
		for(int i=1;i<from;i++)
		{
			temp=temp.nextChild;
		}
		
		//设置循环
		while(n!=1)
		{
			//2.数m个数
			for(int i=1;i<end;i++)
			{
				temp=temp.nextChild;
			}
			
			//输出这个出圈的人
			System.out.print(temp.num+"  ");
			
			//3.剔除数m数的人
			
			/*
			//找到要剔除的这个人的前一个人
			int m=0;
			Child temp2=temp.nextChild;
			temp=new Child(m);
			temp.nextChild=temp2.nextChild;
			*/
			
			//找到要剔除的这个人的前一个人
			Child temp2=temp;
			Child temp3=temp;
			while(temp2.nextChild!=temp)
			{
				temp2=temp2.nextChild;
			}
			temp=temp2;
			temp.nextChild=temp3.nextChild;
			//temp2.nextChild=temp.nextChild;
			//指向下一个开始数数的人
			temp=temp.nextChild;
			n--;
		}
		System.out.println();
		System.out.println("最后出圈的小孩是"+temp.num);
	}
	
	//打印该环形链表
	public void show()
	{
		//定义一个跑龙套
		Child temp=this.firstChild;
		do{
			System.out.println(temp.num);
			temp=temp.nextChild;
		}while(temp!=this.firstChild);
	}
}
