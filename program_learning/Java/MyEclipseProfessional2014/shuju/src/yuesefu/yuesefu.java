package yuesefu;
import java.util.*;

public class yuesefu 
{

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Children c=new Children(100,5);//���캢�Ӷ��У����뺢����Ŀ��������Ŀ
		c.show_children();//�����еĺ���չʾ����
		c.child_count();//��ʼԼɪ�����
		c.show_children();//��ʣ��ĺ���չʾ����
	}

}

class Children
{
	private int max_num;//���庢����Ŀ
	private Vector v;//���庢�Ӷ���
	private int count_num;
	
	public Children(int num,int count)//���캯��
	{
		this.max_num=num;//���뺢����Ŀ
		v=new Vector(this.max_num);//�������Ӷ���
		this.count_num=count;//����Ҫ������Ŀ
		for(int i=1;i<=this.max_num;i++)//�����еĺ��ӱ��
		{
			v.add(i);
		}
	}
	
	public void show_children()//չʾ����
	{
		System.out.println("The children's number is "+v.size());
		for(int i=0;i<v.size();i++)//ѭ����ʾ����
		{
			System.out.print(v.get(i)+" ");//�����ǰ����
		}
		System.out.println();//��ӻس�����
	}
	
	public void child_count()//���к��ӿ�ʼ����
	{
		int counts=0;//��ʼ����
		while(v.size()!=1)//ֱ��������ʣһ������
		{
			counts++;//���ο�ʼ����
			if(counts==this.count_num)//���������������Ŀ
			{
				v.remove(0);//�������ʼ�ĺ���
				counts=0;//���¿�ʼ����
			}
			else//���û�м�����������Ŀ
			{
				v.add(v.get(0));//����һ�����䵽�������
				v.remove(0);//ɾ����һ��
			}
			//System.out.println("The children's number now is "+v.size());
		}
		//this.show_children();
	}
}