package fanxing;
import java.util.*;

public class fanxingjichujuli {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//���о�δ�÷��͵�����
		/*
		ArrayList arraylist=new ArrayList();
		Dog dog=new Dog("����",23);
		arraylist.add(dog);
		//��ȡ��ʱ���뽫��ȡ�����Ķ���ǿ��ת��Ϊdog����
		//Dog temp=(Dog)arraylist.get(0);
		//����ȡ������ǿ�Ƹ�Ϊcat�ͣ�ϵͳ������ʾ����
		Cat temp=(Cat)arraylist.get(0);
		*/
		//���÷��͵Ļ��Ͳ��ؽ���ǿ��ת�������Է�ֹ����
		ArrayList<Dog> arraylist=new ArrayList<Dog>();
		Dog dog=new Dog("����",23);
		arraylist.add(dog);
		Dog temp=arraylist.get(0);
		System.out.println("������"+temp.getName()+"�����䣺"+temp.getAge());
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