package jihe;
import java.util.*;

public class Listjiehelei {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//����չʾLinkedList���÷���Vector�������÷�������ͬ��ջStack���÷�Ҳ���ƣ�����ֻ�ܼӵ���ǰ�棩
		LinkedList linkedlist=new LinkedList();
		Employee employee1=new Employee(1,"������",7000);
		Employee employee2=new Employee(2,"������",6500);
		Employee employee3=new Employee(3,"��   ��",6000);
		//ΪLinkedList��Ӷ���Object���ͣ�
		linkedlist.add(employee2);
		linkedlist.addFirst(employee1);
		linkedlist.addLast(employee3);
		linkedlist.add(employee1);
		//����
		for(int i=0;i<linkedlist.size();i++)
		{
			Employee temp=(Employee)linkedlist.get(i);
			System.out.println("Ա����ţ�"+temp.getNumber()+"��Ա�����֣�"+temp.getName()+"��нˮ��"+temp.getSalary());
		}
	}

}
