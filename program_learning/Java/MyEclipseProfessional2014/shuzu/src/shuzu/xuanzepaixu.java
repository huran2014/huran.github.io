package shuzu;

import java.util.Calendar;

public class xuanzepaixu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int shuju[]={34,6,3,65,23,8,54,87};
		//产生一个含有10万个数据的数组，数组值从1~10万
		int len=200000;
		int shuju[]=new int[len];
		for(int i=0;i<len;i++)
		{
			//Math.random()可以产生一个0~1的随机数
			int t=(int)(Math.random()*200000);
			shuju[i]=t;
		}
		Select select=new Select();
		
		//附加计算排序时间
		//输出系统时间
		Calendar cal=Calendar.getInstance();
		System.out.println("排序前系统时间"+cal.getTime());
		select.xuanze(shuju);
		//输出排序后系统时间
		Calendar cal1=Calendar.getInstance();
		System.out.println("排序后系统时间"+cal1.getTime());
		/*
		System.out.println("排序结果为（由小到大）：");
		for(int i=0;i<shuju.length;i++)
		{
			System.out.print(shuju[i]+" , ");
		}
		*/
	}
}

class Select
{
	int temp=0;
	public void xuanze(int shuju[])
	{
		for(int i=0;i<shuju.length-1;i++)
		{
			int min=shuju[i];
			int minkey=i;
			for(int j=i+1;j<shuju.length;j++)
			{
				if(shuju[j]<min)
				{
					minkey=j;
					min=shuju[j];
				}
			}
			temp=shuju[i];
			shuju[i]=min;
			shuju[minkey]=temp;
		}
		/*public void shuchu(int shuju1[])
		{
			System.out.println("排序结果为（由小到大）：");
			for(int i=0;i<shuju1.length;i++)
			{
				System.out.print(shuju1[i]+" , ");
			}	
		}*/
	}
}