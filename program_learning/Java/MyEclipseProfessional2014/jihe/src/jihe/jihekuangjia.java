/*
 * 公司职员薪水管理系统，实现功能：
 * 1、当有新员工时，将该员工加入到管理系统
 * 2、可以根据员工号，显示员工信息
 * 3、可以显示所有员工信息
 * 4、可以修改员工的薪水
 * 5、当员工离职时，将该员工从管理系统中删除
 * 6、可以按照薪水从低到高顺序排列
 * 7、可以统计员工的平均工资和最低、最高工资
 */
package jihe;
import java.util.*;
import java.io.*;

public class jihekuangjia {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		//首先定义一个ArrayList集合
		ArrayList arraylist=new ArrayList();
		Employee employee1=new Employee(1,"吕梦阳",7000);
		Employee employee2=new Employee(2,"李乐乐",6500);
		Employee employee3=new Employee(3,"朱   鹏",6000);
		//将对象添加到集合中
		arraylist.add(employee1);
		arraylist.add(employee2);
		arraylist.add(employee3);
		Employeemanage manage=new Employeemanage(arraylist);
		manage.employeexianshi();
		System.out.println("请问是否继续进行操作？（y/n）");
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		String str=buf.readLine();
		while("y".equals(str))
		{
			System.out.println("请选择您要进行的操作：（1、添加成员 2、查看某一员工信息 3、显示所有员工信息 4、修改某一员工薪水 5、删除员工 6、薪水排序 7、平均工资及最低最高工资）");
			str=buf.readLine();
			int num=Integer.parseInt(str);
			if(num==1) manage.employeeadd();
			else if(num==2) manage.employeexuanze();
			else if(num==3) manage.employeexianshi();
			else if(num==4) manage.employeexinshui();
			else if(num==5) manage.employeeshanchu();
			else if(num==6) manage.employeepaixu();
			else if(num==7) manage.employeetongji();
			else System.out.println("没有该操作，请重新选择。");
			System.out.println("请问是否继续操作？（y/n）");
			str=buf.readLine();
		}
	}
}

//定义职员管理
class Employeemanage
{
	ArrayList arraylist=null;
	//构造函数 ===用来初始化成员变量，规范用法，比new好
	public Employeemanage(ArrayList arraylist)
	{
		this.arraylist=arraylist;
		//ArrayList=new ArrayList();
	}
	BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
	//添加一个成员
	public void employeeadd() throws Exception
	{
		Employee employee=new Employee();
		System.out.println("请输入添加员工的编号：");
		String str = null;
		str = buf.readLine();
		int number=Integer.parseInt(str);
		employee.setNumber(number);
		System.out.println("请输入添加员工的姓名：");
		str=buf.readLine();
		employee.setName(str);
		System.out.println("请输入添加员工的薪水：");
		str=buf.readLine();
		int salary=Integer.parseInt(str);
		employee.setSalary(salary);
		arraylist.add(employee);
	}
	
	//遍历所有员工信息
	public void employeexianshi()
	{
		System.out.println("显示所有员工信息：");
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			System.out.println("员工编号："+temp.getNumber()+"，员工名字："+temp.getName()+"，薪水："+temp.getSalary());
		}
	}
	
	//根据员工号显示员工信息
	public void employeexuanze() throws Exception
	{
		System.out.println("请输入要查找的员工号：");
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		String str=buf.readLine();
		int num=Integer.parseInt(str);
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			if(num==temp.getNumber())
			{
				System.out.println("员工编号："+temp.getNumber()+"，员工名字："+temp.getName()+"，薪水："+temp.getSalary());
				break;
			}
		}
	}
	
	//可以修改员工的薪水
	public void employeexinshui() throws Exception
	{
		System.out.println("请输入要修改的员工姓名：");
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		String name=buf.readLine();
		System.out.println("请输入新的薪水：");
		String str = null;
		str = buf.readLine();
		int salary=Integer.parseInt(str);
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			if(temp.getName().equals(name))
			{
				temp.setSalary(salary);
				//先在集合中删除该员工
				arraylist.remove(i);
				//再在原位置加上修改后的员工
				arraylist.add(i,temp);
				//输出该员工新的信息
				System.out.println("员工编号："+temp.getNumber()+"，员工名字："+temp.getName()+"，薪水："+temp.getSalary());
				break;
			}
		}
	}
	
	//员工离职时，删除员工
	public void employeeshanchu() throws Exception
	{
		System.out.println("请输入要删除的员工的名字：");
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		String name = null;
		name = buf.readLine();
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			if(temp.getName().equals(name))
			{
				arraylist.remove(temp);
				break;
			}
		}
	}
	
	//员工薪水排序
	public void employeepaixu()
	{
		int salary[]=new int[arraylist.size()];
		//提取薪水
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			salary[i]=temp.getSalary();
		}
		//将薪水排序
		for(int j=1;j<arraylist.size();j++)
		{
			int salary1=salary[j];
			int k=j-1;
			while(k>=0&&salary1<salary[k])
			{
				salary[k+1]=salary[k];
				k--;
			}
			salary[k+1]=salary1;
		}
		//将薪水与集合结合起来
		for(int i=0;i<arraylist.size();i++)
		{
			for(int j=0;j<arraylist.size();j++)
			{
				Employee temp=(Employee)arraylist.get(j);
				if(salary[i]==temp.getSalary())
				{
					System.out.println("员工编号："+temp.getNumber()+"，员工名字："+temp.getName()+"，薪水："+temp.getSalary());
					break;
				}
			}
		}
	}
	
	//统计平均工资和最低、最高工资
	public void employeetongji()
	{
		int salary[]=new int[arraylist.size()];
		//提取薪水
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			salary[i]=temp.getSalary();
		}
		//计算平均工资和找出最低、最高工资
		int max=salary[0];
		int min=salary[0];
		int tongji=0;
		for(int i=0;i<salary.length;i++)
		{
			tongji+=salary[i];
			if(min>salary[i])
			{
				min=salary[i];
			}
			if(max<salary[i])
			{
				max=salary[i];
			}
		}
		System.out.println("所有员工的平均工资为："+tongji/salary.length);
		//找到最低、最高工资对应的员工
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			if(min==temp.getSalary())
			{
				System.out.println("最低工资的员工编号："+temp.getNumber()+"，员工名字："+temp.getName()+"，薪水："+temp.getSalary());
			}
			if(max==temp.getSalary())
			{
				System.out.println("最高工资的员工编号："+temp.getNumber()+"，员工名字："+temp.getName()+"，薪水："+temp.getSalary());
			}
		}
	}
}

//定义雇员
class Employee
{
	int number;
	String name;
	int salary;
	public Employee(int number,String name,int salary)
	{
		this.number=number;
		this.name=name;
		this.salary=salary;
	}
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
}