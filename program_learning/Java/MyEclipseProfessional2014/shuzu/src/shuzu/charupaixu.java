package shuzu;

import java.util.Calendar;

public class charupaixu {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		int len=200000;
		int shuju[]=new int[len];
		for(int i=0;i<len;i++)
		{
			//产生随机数
			int t=(int)(Math.random()*1000);
			shuju[i]=t;
		}
		Insertselect insertselect=new Insertselect();
		Calendar cal=Calendar.getInstance();
		System.out.println("排序前时间："+cal.getTime());
		insertselect.select(shuju);
		Calendar cal1=Calendar.getInstance();
		System.out.println("排序后时间："+cal1.getTime());
		/*System.out.println("输出结果为：");
		for(int m=0;m<len;m++)
		{
			System.out.print(shuju[m]+" , ");
		}*/
	}
}

class Insertselect
{
	public void select(int shuju[])
	{
		for(int i=1;i<shuju.length;i++)
		{
			int rand=shuju[i];
			int randkey=i-1;
			while(randkey>=0&&rand<shuju[randkey])
			{
				shuju[randkey+1]=shuju[randkey];
				randkey--;
			}
			shuju[randkey+1]=rand;
		}
	}
}