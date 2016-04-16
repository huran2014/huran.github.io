/*
 * arraylist的使用
 */
package jihe;
import java.util.*;

public class arraylist {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//首先定义一个arraylist对象
		ArrayList array=new ArrayList();
		//显示array的大小
		System.out.println("刚建立时array的大小为："+array.size());
		//向array中添加对象（类型为Object（所有类都是从Object衍生出来的））
		//首先定义几个职员对象
		Clerk clerk1=new Clerk("吕梦阳",23,7000);
		Clerk clerk2=new Clerk("李乐乐",23,6500);
		Clerk clerk3=new Clerk("朱   鹏",23,6000);
		//在array中添加一个对象
		array.add(clerk1);
		array.add(clerk2);
		array.add(clerk3);
		//输出arraylist的大小
		System.out.println("此时array的大小为："+array.size());
		//遍历所有对象
		for(int i=0;i<array.size();i++)
		{
			//提取arraylist中的对象，但由于arraylist中为Object类，必须将其强制转换为Clerk类
			Clerk temp=(Clerk)array.get(i);
			System.out.println("第"+(i+1)+"个职员是："+temp.getName()+"，年龄："+temp.getAge()+"，薪水："+temp.getSallery()+"/月");
		}
		//注意：在集合中是可以重复添加同一个对象的，如：
		array.add(clerk1);
		System.out.println("**********添加同样对象**************");
		//遍历所有对象
		for(int i=0;i<array.size();i++)
		{
			//提取arraylist中的对象，但由于arraylist中为Object类，必须将其强制转换为Clerk类
			Clerk temp=(Clerk)array.get(i);
			System.out.println("第"+(i+1)+"个职员是："+temp.getName()+"，年龄："+temp.getAge()+"，薪水："+temp.getSallery()+"/月");
		}
		//在arraylist中删除对象
		array.remove(3);
		System.out.println("======删除一个对象=======");
		//遍历所有对象
		for(int i=0;i<array.size();i++)
		{
			//提取arraylist中的对象，但由于arraylist中为Object类，必须将其强制转换为Clerk类
			Clerk temp=(Clerk)array.get(i);
			System.out.println("第"+(i+1)+"个职员是："+temp.getName()+"，年龄："+temp.getAge()+"，薪水："+temp.getSallery()+"/月");
		}
	}
}

//定义职员类
class Clerk
{
	String name;
	int age;
	int salary;
	public Clerk(String name,int age,int salary)
	{
		this.name=name;
		this.age=age;
		this.salary=salary;
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
	public int getSallery() {
		return salary;
	}
	public void setSallery(int sallery) {
		this.salary = sallery;
	}
}