/**
 * ���ܣ�Լɪ������
 */

package lessen1_1;
import java.io.*;

public class yuesefu {

	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
		System.out.println("������Ⱥ����С���ĸ���:");
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
		System.out.println("������ӵڼ���С����ʼ��:");
		/*String str2;
		int b;
		BufferedReader buf2;
		buf2=new BufferedReader(new InputStreamReader(System.in));
		str2=buf2.readLine();
		b=Integer.parseInt(str2);*/
		int b=Integer.parseInt(buf.readLine());
		System.out.println("������Ҫ�߳�������������С��:");
		/*String str3;
		int c;
		BufferedReader buf3;
		buf3=new BufferedReader(new InputStreamReader(System.in));
		str3=buf3.readLine();
		c=Integer.parseInt(str3);*/
		int c=Integer.parseInt(buf.readLine());
		System.out.println("Լɪ������Ľ����:");
		CycLink cyclink=new CycLink();
		cyclink.setLen(a);
		cyclink.setFrom(b);
		cyclink.setEnd(c);
		cyclink.createLink();
		cyclink.show();
		cyclink.play();
	}

}

//�����ڵ�
class Child
{
	int num;
	Child nextChild=null;
	public Child(int num)
	{
		//��һ�����
		this.num=num;
	}
}

//��������
class CycLink
{
	//�ȶ���һ��ָ�������һ��С��������
	//ָ���һ��С�������ã����ܶ�
	Child firstChild=null;
	Child temp=null;
	int len=0;//��ʾС���ĸ���
	int from=0;//��ʾ�ӵ�from��С����ʼ����
	int end=0;//��ʾ��������end������С���߳�Ȧ��
	
	//���������С
	public void setLen(int len)
	{
		this.len=len;
	}
	
	//����from��С
	public void setFrom(int from)
	{
		this.from=from;
	}
	
	//����end��С
	public void setEnd(int end)
	{
		this.end=end;
	}
	
	//��ʼ����������
	public void createLink()
	{
		for(int i=1;i<=len;i++)
		{
			if(i==1)
			{
				//������һ��С��
				Child ch=new Child(i);
				this.firstChild=ch;
				this.temp=ch;
			}
			else
			{
				if(i==len)
				{
					//�������һ��С��
					Child ch=new Child(i);
					temp.nextChild=ch;
					temp=ch;
					temp.nextChild=this.firstChild;
				}
				else
				{
					//��������С��
					Child ch=new Child(i);
					//������һ��С����������һ��С������
					temp.nextChild=ch;
					//������ǰС��������һ��С����������һ��С������
					temp=ch;
				}
			}
			
		}
	}
	
	//��ʼ�������
	public void play()
	{
		
		System.out.println("��Ȧ��˳����:");
		//�������������׵���
		Child temp=this.firstChild;
		int n=this.len;//����ѭ������
		
		
		//1.�ҵ���ʼ��������
		for(int i=1;i<from;i++)
		{
			temp=temp.nextChild;
		}
		
		//����ѭ��
		while(n!=1)
		{
			//2.��m����
			for(int i=1;i<end;i++)
			{
				temp=temp.nextChild;
			}
			
			//��������Ȧ����
			System.out.print(temp.num+"  ");
			
			//3.�޳���m������
			
			/*
			//�ҵ�Ҫ�޳�������˵�ǰһ����
			int m=0;
			Child temp2=temp.nextChild;
			temp=new Child(m);
			temp.nextChild=temp2.nextChild;
			*/
			
			//�ҵ�Ҫ�޳�������˵�ǰһ����
			Child temp2=temp;
			Child temp3=temp;
			while(temp2.nextChild!=temp)
			{
				temp2=temp2.nextChild;
			}
			temp=temp2;
			temp.nextChild=temp3.nextChild;
			//temp2.nextChild=temp.nextChild;
			//ָ����һ����ʼ��������
			temp=temp.nextChild;
			n--;
		}
		System.out.println();
		System.out.println("����Ȧ��С����"+temp.num);
	}
	
	//��ӡ�û�������
	public void show()
	{
		//����һ��������
		Child temp=this.firstChild;
		do{
			System.out.println(temp.num);
			temp=temp.nextChild;
		}while(temp!=this.firstChild);
	}
}
