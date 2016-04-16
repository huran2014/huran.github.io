package com.interface_operation;

public class return_Void {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Void_r void_r=new Void_r();
		System.out.println(void_r.getStr2());
		System.out.println(void_r.getStr1());
		System.out.println(void_r.getStr1().getClass());
	}

}

//定义一个实现类
class Void_r
{
	private Void_f str1;
	private String str2;
	
	public Void_f getStr1() {
		return str1;
	}

	public String getStr2() {
		return str2;
	}
	
	public Void_r()
	{
		this.str1=new Void_f(new Collable<Void>(){
			public Void call()
			{
				str2="hello";
				return null;
			}
		});
		System.out.println(str2);
	}
}

//定义一个总类
class Void_f
{
	private Collable<?> str;
	
	public Collable<?> getStr()
	{
		return this.str;
	}
	
	public Void_f(Collable<?> collable)
	{
		this.str=collable;
	}
}

//定义一个接口
interface Collable<V>
{
	V call();
}