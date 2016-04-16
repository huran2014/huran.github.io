package shuzu;

import java.util.Calendar;

public class maopaopaixu2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("选择排序的结果为（由大到小）：");
		int len=200000;
		int shuju[]=new int[len];
		for(int i=0;i<len;i++)
		{
			//Math.random()可以产生一个0~1的随机数
			int t=(int)(Math.random()*200000);
			shuju[i]=t;
		}
		//int shuju[]={2,5,34,-24,-34,-65,92,38};
		Paixu xuanzepaixu=new Paixu();
		
		//记录系统时间
		Calendar cal=Calendar.getInstance();
		System.out.println("排序前时间："+cal.getTime());
		xuanzepaixu.paixu(shuju);
		Calendar cal1=Calendar.getInstance();
		System.out.println("排序后时间："+cal1.getTime());
		/*
		for(int i=0;i<shuju.length;i++)
		{
			System.out.print(shuju[i]+" , ");
		}
		*/
	}
}

class Paixu
{
	int temp=0;
	public void paixu(int shuju[])
	{
		for(int i=0;i<shuju.length-1;i++)
		{
			for(int j=i+1;j<shuju.length;j++)
			{
				if(shuju[i]<=shuju[j])
				{
					temp=shuju[j];
					shuju[j]=shuju[i];
					shuju[i]=temp;
				}
			}
		}
	}
}