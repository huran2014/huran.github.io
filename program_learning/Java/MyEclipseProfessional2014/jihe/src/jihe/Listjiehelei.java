package jihe;
import java.util.*;

public class Listjiehelei {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//首先展示LinkedList的用法（Vector向量的用法和它相同，栈Stack的用法也类似，不过只能加到最前面）
		LinkedList linkedlist=new LinkedList();
		Employee employee1=new Employee(1,"吕梦阳",7000);
		Employee employee2=new Employee(2,"李乐乐",6500);
		Employee employee3=new Employee(3,"朱   鹏",6000);
		//为LinkedList添加对象（Object类型）
		linkedlist.add(employee2);
		linkedlist.addFirst(employee1);
		linkedlist.addLast(employee3);
		linkedlist.add(employee1);
		//遍历
		for(int i=0;i<linkedlist.size();i++)
		{
			Employee temp=(Employee)linkedlist.get(i);
			System.out.println("员工编号："+temp.getNumber()+"，员工名字："+temp.getName()+"，薪水："+temp.getSalary());
		}
	}

}
