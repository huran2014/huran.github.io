package fanxing;

import java.lang.reflect.Method;

public class fanxinglei {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Gen<String> gen1=new Gen<String>("aaa");
		Gen<Bird> gen1=new Gen<Bird>(new Bird());
		gen1.showTypename();
	}

}

//�������
//���ȶ���һ����
class Bird
{
	public void test()
	{
		System.out.println("yes");
	}
	public void add(int a,int b)
	{
		System.out.println(a+b);
	}
}

//����һ��������
class Gen<T>
{
	//�÷����ඨ��һ������
	T o;
	
	//���캯��
	public Gen(T o)
	{
		this.o=o;
	}
	
	//�õ�T���͵�����
	public void showTypename()
	{
		System.out.println("�����ǣ�"+o.getClass().getName());
		//ͨ��������ƣ����Եõ�������T�ĺܶ���Ϣ������õ���Ա�������ƣ�
		//�õ��ĳ�Ա�������ƻ᷵�ص�һ��������(�˴���Ҫ�����)
		Method m[]=o.getClass().getDeclaredMethods();
		for(int i=0;i<m.length;i++)
		{
			System.out.println(m[i].getName());
		}
	}
}
