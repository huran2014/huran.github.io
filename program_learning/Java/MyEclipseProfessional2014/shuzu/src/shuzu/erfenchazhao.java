package shuzu;
import java.io.*;

public class erfenchazhao {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		int len=100;
		int shuju[]=new int[len];
		for(int i=0;i<len;i++)
		{
			int t=(int)(Math.random()*len);
			shuju[i]=t;
		}
		System.out.println("请输入想要查找的数：");
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		String str;
		str = buf.readLine();
		int finder=Integer.parseInt(str);
		Insertpaixu insertpaixu=new Insertpaixu();
		insertpaixu.paixu(shuju);
		System.out.println("排序后数据为：");
		for(int j=0;j<len;j++)
		{
			System.out.print(shuju[j]+" , ");
		}
		System.out.println();
		Doubleselect doubleselect=new Doubleselect();
		doubleselect.find(0,len-1,finder, shuju);
		
	}
}

class Doubleselect
{
	public void find(int firstkey,int finalkey,int finder,int shuju[])
	{
		if(firstkey<=finalkey)
		{
			int middlekey=(firstkey+finalkey)/2;
			int middle=shuju[middlekey];
			
			if(finder>middle)
			{
				int firstkey1=middlekey+1;
				int finalkey1=finalkey;
				find(firstkey1,finalkey1,finder,shuju);
			}
			else if(finder<middle)
			{
				int firstkey2=firstkey;
				int finalkey2=middlekey-1;
				find(firstkey2,finalkey2,finder,shuju);
			}
			else
			{
				System.out.println("所查找数所在的位数为："+middlekey);
				int firstkey3=firstkey;
				int finalkey3=middlekey-1;
				if(finalkey3>=0)
				{
					find(firstkey3,finalkey3,finder,shuju);
				}
				int firstkey4=middlekey+1;
				int finalkey4=finalkey;
				if(firstkey4<=99)
				{
					find(firstkey4,finalkey4,finder,shuju);
				}
			}
		}
	}
}

class Insertpaixu
{
	public void paixu(int shuju[])
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
