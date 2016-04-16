package yuesefu;
import java.util.*;

public class yuesefu 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Children c=new Children(100,5);//构造孩子队列，输入孩子数目和数数数目
		c.show_children();//将所有的孩子展示出来
		c.child_count();//开始约瑟夫计数
		c.show_children();//将剩余的孩子展示出来
	}

}

class Children
{
	private int max_num;//定义孩子数目
	private Vector v;//定义孩子队列
	private int count_num;
	
	public Children(int num,int count)//构造函数
	{
		this.max_num=num;//输入孩子数目
		v=new Vector(this.max_num);//建立孩子队列
		this.count_num=count;//输入要数的数目
		for(int i=1;i<=this.max_num;i++)//对所有的孩子标号
		{
			v.add(i);
		}
	}
	
	public void show_children()//展示孩子
	{
		System.out.println("The children's number is "+v.size());
		for(int i=0;i<v.size();i++)//循环显示孩子
		{
			System.out.print(v.get(i)+" ");//输出当前孩子
		}
		System.out.println();//添加回车换行
	}
	
	public void child_count()//所有孩子开始数数
	{
		int counts=0;//初始数数
		while(v.size()!=1)//直到数数还剩一个孩子
		{
			counts++;//依次开始计数
			if(counts==this.count_num)//如果计数到丢弃数目
			{
				v.remove(0);//丢弃最初始的孩子
				counts=0;//重新开始计数
			}
			else//如果没有计数到丢弃数目
			{
				v.add(v.get(0));//将第一个补充到队列最后
				v.remove(0);//删除第一个
			}
			//System.out.println("The children's number now is "+v.size());
		}
		//this.show_children();
	}
}