/*
 * arraylist��ʹ��
 */
package jihe;
import java.util.*;

public class arraylist {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//���ȶ���һ��arraylist����
		ArrayList array=new ArrayList();
		//��ʾarray�Ĵ�С
		System.out.println("�ս���ʱarray�Ĵ�СΪ��"+array.size());
		//��array����Ӷ�������ΪObject�������඼�Ǵ�Object���������ģ���
		//���ȶ��弸��ְԱ����
		Clerk clerk1=new Clerk("������",23,7000);
		Clerk clerk2=new Clerk("������",23,6500);
		Clerk clerk3=new Clerk("��   ��",23,6000);
		//��array�����һ������
		array.add(clerk1);
		array.add(clerk2);
		array.add(clerk3);
		//���arraylist�Ĵ�С
		System.out.println("��ʱarray�Ĵ�СΪ��"+array.size());
		//�������ж���
		for(int i=0;i<array.size();i++)
		{
			//��ȡarraylist�еĶ��󣬵�����arraylist��ΪObject�࣬���뽫��ǿ��ת��ΪClerk��
			Clerk temp=(Clerk)array.get(i);
			System.out.println("��"+(i+1)+"��ְԱ�ǣ�"+temp.getName()+"�����䣺"+temp.getAge()+"��нˮ��"+temp.getSallery()+"/��");
		}
		//ע�⣺�ڼ������ǿ����ظ����ͬһ������ģ��磺
		array.add(clerk1);
		System.out.println("**********���ͬ������**************");
		//�������ж���
		for(int i=0;i<array.size();i++)
		{
			//��ȡarraylist�еĶ��󣬵�����arraylist��ΪObject�࣬���뽫��ǿ��ת��ΪClerk��
			Clerk temp=(Clerk)array.get(i);
			System.out.println("��"+(i+1)+"��ְԱ�ǣ�"+temp.getName()+"�����䣺"+temp.getAge()+"��нˮ��"+temp.getSallery()+"/��");
		}
		//��arraylist��ɾ������
		array.remove(3);
		System.out.println("======ɾ��һ������=======");
		//�������ж���
		for(int i=0;i<array.size();i++)
		{
			//��ȡarraylist�еĶ��󣬵�����arraylist��ΪObject�࣬���뽫��ǿ��ת��ΪClerk��
			Clerk temp=(Clerk)array.get(i);
			System.out.println("��"+(i+1)+"��ְԱ�ǣ�"+temp.getName()+"�����䣺"+temp.getAge()+"��нˮ��"+temp.getSallery()+"/��");
		}
	}
}

//����ְԱ��
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