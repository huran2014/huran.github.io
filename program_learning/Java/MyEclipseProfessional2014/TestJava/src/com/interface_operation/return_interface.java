package com.interface_operation;

public class return_interface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Object new_class=getclassofHusky1();
		System.out.println(new_class.getClass());
		((Shout) new_class).shout();
		Object new_class1=getclassofHusky();
		System.out.println(new_class1.getClass());
		if(new_class.getClass()==new_class1.getClass())
			System.out.println("二者返回对象相同");
	}
	
	public static Walk getclassofHusky()
	{
		return new Husky();
	}
	
	public static Shout getclassofHusky1()
	{
		return new Husky();
	}
	
//	public static Shout getclassofAnimal()
//	{
//		return new Animal();
//	}
}

//定义一个具体类
class Husky extends dog implements Walk
{
	
	public Husky(){
		super("hello");
	}
	
	@Override
	public void shout() {
		// TODO Auto-generated method stub
		System.out.println("汪汪");
		System.out.println(getString());
		//方法不可行。虽然继承了方法和属性。但是当属性是private时，该属性仅能被父类函数调用，子类虽可操作，但是无法直接通过this.获得到
		//System.out.println(this.word);
	}
	

	@Override
	void name() {
		// TODO Auto-generated method stub
		System.out.println("哈士奇犬");
	}

	@Override
	public void walk() {
		// TODO Auto-generated method stub
		
	}
}

//定义一个类
class dog extends Animal implements Shout
{
	private String word;
	
	public dog(){
	}
	
	public dog(String word)
	{
		this.word=word;
	}
	
	public String getString()
	{
		return this.word;
	}
	@Override
	public void shout() {
		// TODO Auto-generated method stub
		System.out.println("动物叫");
	}

	@Override
	void name() {
		// TODO Auto-generated method stub
		System.out.println("动物");
	}
}

//定义总接口类型
interface Shout
{
	void shout();
}

//定义总接口类型
interface Walk
{
	void walk();
}

//定义总类对象
abstract class Animal
{
	abstract void name();
}