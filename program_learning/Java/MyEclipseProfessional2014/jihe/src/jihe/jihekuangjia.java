/*
 * ��˾ְԱнˮ����ϵͳ��ʵ�ֹ��ܣ�
 * 1��������Ա��ʱ������Ա�����뵽����ϵͳ
 * 2�����Ը���Ա���ţ���ʾԱ����Ϣ
 * 3��������ʾ����Ա����Ϣ
 * 4�������޸�Ա����нˮ
 * 5����Ա����ְʱ������Ա���ӹ���ϵͳ��ɾ��
 * 6�����԰���нˮ�ӵ͵���˳������
 * 7������ͳ��Ա����ƽ�����ʺ���͡���߹���
 */
package jihe;
import java.util.*;
import java.io.*;

public class jihekuangjia {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		//���ȶ���һ��ArrayList����
		ArrayList arraylist=new ArrayList();
		Employee employee1=new Employee(1,"������",7000);
		Employee employee2=new Employee(2,"������",6500);
		Employee employee3=new Employee(3,"��   ��",6000);
		//��������ӵ�������
		arraylist.add(employee1);
		arraylist.add(employee2);
		arraylist.add(employee3);
		Employeemanage manage=new Employeemanage(arraylist);
		manage.employeexianshi();
		System.out.println("�����Ƿ�������в�������y/n��");
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		String str=buf.readLine();
		while("y".equals(str))
		{
			System.out.println("��ѡ����Ҫ���еĲ�������1����ӳ�Ա 2���鿴ĳһԱ����Ϣ 3����ʾ����Ա����Ϣ 4���޸�ĳһԱ��нˮ 5��ɾ��Ա�� 6��нˮ���� 7��ƽ�����ʼ������߹��ʣ�");
			str=buf.readLine();
			int num=Integer.parseInt(str);
			if(num==1) manage.employeeadd();
			else if(num==2) manage.employeexuanze();
			else if(num==3) manage.employeexianshi();
			else if(num==4) manage.employeexinshui();
			else if(num==5) manage.employeeshanchu();
			else if(num==6) manage.employeepaixu();
			else if(num==7) manage.employeetongji();
			else System.out.println("û�иò�����������ѡ��");
			System.out.println("�����Ƿ������������y/n��");
			str=buf.readLine();
		}
	}
}

//����ְԱ����
class Employeemanage
{
	ArrayList arraylist=null;
	//���캯�� ===������ʼ����Ա�������淶�÷�����new��
	public Employeemanage(ArrayList arraylist)
	{
		this.arraylist=arraylist;
		//ArrayList=new ArrayList();
	}
	BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
	//���һ����Ա
	public void employeeadd() throws Exception
	{
		Employee employee=new Employee();
		System.out.println("���������Ա���ı�ţ�");
		String str = null;
		str = buf.readLine();
		int number=Integer.parseInt(str);
		employee.setNumber(number);
		System.out.println("���������Ա����������");
		str=buf.readLine();
		employee.setName(str);
		System.out.println("���������Ա����нˮ��");
		str=buf.readLine();
		int salary=Integer.parseInt(str);
		employee.setSalary(salary);
		arraylist.add(employee);
	}
	
	//��������Ա����Ϣ
	public void employeexianshi()
	{
		System.out.println("��ʾ����Ա����Ϣ��");
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			System.out.println("Ա����ţ�"+temp.getNumber()+"��Ա�����֣�"+temp.getName()+"��нˮ��"+temp.getSalary());
		}
	}
	
	//����Ա������ʾԱ����Ϣ
	public void employeexuanze() throws Exception
	{
		System.out.println("������Ҫ���ҵ�Ա���ţ�");
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		String str=buf.readLine();
		int num=Integer.parseInt(str);
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			if(num==temp.getNumber())
			{
				System.out.println("Ա����ţ�"+temp.getNumber()+"��Ա�����֣�"+temp.getName()+"��нˮ��"+temp.getSalary());
				break;
			}
		}
	}
	
	//�����޸�Ա����нˮ
	public void employeexinshui() throws Exception
	{
		System.out.println("������Ҫ�޸ĵ�Ա��������");
		BufferedReader buf=new BufferedReader(new InputStreamReader(System.in));
		String name=buf.readLine();
		System.out.println("�������µ�нˮ��");
		String str = null;
		str = buf.readLine();
		int salary=Integer.parseInt(str);
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			if(temp.getName().equals(name))
			{
				temp.setSalary(salary);
				//���ڼ�����ɾ����Ա��
				arraylist.remove(i);
				//����ԭλ�ü����޸ĺ��Ա��
				arraylist.add(i,temp);
				//�����Ա���µ���Ϣ
				System.out.println("Ա����ţ�"+temp.getNumber()+"��Ա�����֣�"+temp.getName()+"��нˮ��"+temp.getSalary());
				break;
			}
		}
	}
	
	//Ա����ְʱ��ɾ��Ա��
	public void employeeshanchu() throws Exception
	{
		System.out.println("������Ҫɾ����Ա�������֣�");
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
	
	//Ա��нˮ����
	public void employeepaixu()
	{
		int salary[]=new int[arraylist.size()];
		//��ȡнˮ
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			salary[i]=temp.getSalary();
		}
		//��нˮ����
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
		//��нˮ�뼯�Ͻ������
		for(int i=0;i<arraylist.size();i++)
		{
			for(int j=0;j<arraylist.size();j++)
			{
				Employee temp=(Employee)arraylist.get(j);
				if(salary[i]==temp.getSalary())
				{
					System.out.println("Ա����ţ�"+temp.getNumber()+"��Ա�����֣�"+temp.getName()+"��нˮ��"+temp.getSalary());
					break;
				}
			}
		}
	}
	
	//ͳ��ƽ�����ʺ���͡���߹���
	public void employeetongji()
	{
		int salary[]=new int[arraylist.size()];
		//��ȡнˮ
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			salary[i]=temp.getSalary();
		}
		//����ƽ�����ʺ��ҳ���͡���߹���
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
		System.out.println("����Ա����ƽ������Ϊ��"+tongji/salary.length);
		//�ҵ���͡���߹��ʶ�Ӧ��Ա��
		for(int i=0;i<arraylist.size();i++)
		{
			Employee temp=(Employee)arraylist.get(i);
			if(min==temp.getSalary())
			{
				System.out.println("��͹��ʵ�Ա����ţ�"+temp.getNumber()+"��Ա�����֣�"+temp.getName()+"��нˮ��"+temp.getSalary());
			}
			if(max==temp.getSalary())
			{
				System.out.println("��߹��ʵ�Ա����ţ�"+temp.getNumber()+"��Ա�����֣�"+temp.getName()+"��нˮ��"+temp.getSalary());
			}
		}
	}
}

//�����Ա
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