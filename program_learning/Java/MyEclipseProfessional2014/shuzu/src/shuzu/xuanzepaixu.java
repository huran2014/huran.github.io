package shuzu;

import java.util.Calendar;

public class xuanzepaixu {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//int shuju[]={34,6,3,65,23,8,54,87};
		//����һ������10������ݵ����飬����ֵ��1~10��
		int len=200000;
		int shuju[]=new int[len];
		for(int i=0;i<len;i++)
		{
			//Math.random()���Բ���һ��0~1�������
			int t=(int)(Math.random()*200000);
			shuju[i]=t;
		}
		Select select=new Select();
		
		//���Ӽ�������ʱ��
		//���ϵͳʱ��
		Calendar cal=Calendar.getInstance();
		System.out.println("����ǰϵͳʱ��"+cal.getTime());
		select.xuanze(shuju);
		//��������ϵͳʱ��
		Calendar cal1=Calendar.getInstance();
		System.out.println("�����ϵͳʱ��"+cal1.getTime());
		/*
		System.out.println("������Ϊ����С���󣩣�");
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
			System.out.println("������Ϊ����С���󣩣�");
			for(int i=0;i<shuju1.length;i++)
			{
				System.out.print(shuju1[i]+" , ");
			}	
		}*/
	}
}