/*
 * 介绍HashMap的一般用法
 */
package jihe;
import java.util.*;

public class hashmap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//首先建立一个HashMap
		HashMap hashmap=new HashMap();
		//定义几个对象
		Employee employee1=new Employee(1,"吕梦阳",7000);
		Employee employee2=new Employee(2,"李乐乐",6500);
		Employee employee3=new Employee(3,"朱   鹏",6000);
		//添加对象-->需要添加两个对象，第一个是key（键，是个object类），第二个也是个对象（object类）。两个对象相互关联
		hashmap.put("001", employee1);
		hashmap.put("002", employee2);
		hashmap.put("003", employee3);//此处key若是002，则会覆盖前一个
		 //提取某个员工
		System.out.println("提取某个员工");
		if(hashmap.containsKey("001"))
		{
			System.out.println("该员工存在");
			Employee temp=(Employee)hashmap.get("001");
			System.out.println("员工编号："+temp.getNumber()+"，员工名字："+temp.getName()+"，薪水："+temp.getSalary());
		}
		//遍历所有员工
		System.out.println("遍历所有员工");
		//利用Iterator（迭代）
		Iterator iterator=hashmap.keySet().iterator();
		//创造循环，其中iterator.hasNext返回Boolean值
		while(iterator.hasNext())
		{
			//取出key
			String key=iterator.next().toString();
			//通过key取出value
			Employee temp=(Employee)hashmap.get(key);
			System.out.println("员工编号："+temp.getNumber()+"，员工名字："+temp.getName()+"，薪水："+temp.getSalary());
		}
	}

}
