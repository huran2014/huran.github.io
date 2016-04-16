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

//反射机制
//首先定义一个类
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

//定义一个泛型类
class Gen<T>
{
	//用泛型类定义一个对象
	T o;
	
	//构造函数
	public Gen(T o)
	{
		this.o=o;
	}
	
	//得到T类型的名称
	public void showTypename()
	{
		System.out.println("类型是："+o.getClass().getName());
		//通过反射机制，可以得到泛型类T的很多信息（比如得到成员函数名称）
		//得到的成员方法名称会返回到一个数组里(此处需要引入包)
		Method m[]=o.getClass().getDeclaredMethods();
		for(int i=0;i<m.length;i++)
		{
			System.out.println(m[i].getName());
		}
	}
}
