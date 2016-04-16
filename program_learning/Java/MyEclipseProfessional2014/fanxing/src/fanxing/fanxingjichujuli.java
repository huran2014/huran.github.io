package fanxing;
import java.util.*;

public class fanxingjichujuli {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//先列举未用泛型的例子
		/*
		ArrayList arraylist=new ArrayList();
		Dog dog=new Dog("阳阳",23);
		arraylist.add(dog);
		//在取出时必须将提取出来的对象强制转换为dog类型
		//Dog temp=(Dog)arraylist.get(0);
		//若提取出来后强制改为cat型，系统不会提示出错
		Cat temp=(Cat)arraylist.get(0);
		*/
		//而用泛型的话就不必进行强制转化，可以防止出错
		ArrayList<Dog> arraylist=new ArrayList<Dog>();
		Dog dog=new Dog("阳阳",23);
		arraylist.add(dog);
		Dog temp=arraylist.get(0);
		System.out.println("姓名："+temp.getName()+"，年龄："+temp.getAge());
	}
}

class Dog
{
	private String name;
	private int age;
	public Dog (String name,int age)
	{
		this.name=name;
		this.age=age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}

class Cat
{
	String name;
	int age;
	public Cat (String name,int age)
	{
		this.name=name;
		this.age=age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}