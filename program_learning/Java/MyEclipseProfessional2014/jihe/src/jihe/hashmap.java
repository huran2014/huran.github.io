/*
 * ����HashMap��һ���÷�
 */
package jihe;
import java.util.*;

public class hashmap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//���Ƚ���һ��HashMap
		HashMap hashmap=new HashMap();
		//���弸������
		Employee employee1=new Employee(1,"������",7000);
		Employee employee2=new Employee(2,"������",6500);
		Employee employee3=new Employee(3,"��   ��",6000);
		//��Ӷ���-->��Ҫ����������󣬵�һ����key�������Ǹ�object�ࣩ���ڶ���Ҳ�Ǹ�����object�ࣩ�����������໥����
		hashmap.put("001", employee1);
		hashmap.put("002", employee2);
		hashmap.put("003", employee3);//�˴�key����002����Ḳ��ǰһ��
		 //��ȡĳ��Ա��
		System.out.println("��ȡĳ��Ա��");
		if(hashmap.containsKey("001"))
		{
			System.out.println("��Ա������");
			Employee temp=(Employee)hashmap.get("001");
			System.out.println("Ա����ţ�"+temp.getNumber()+"��Ա�����֣�"+temp.getName()+"��нˮ��"+temp.getSalary());
		}
		//��������Ա��
		System.out.println("��������Ա��");
		//����Iterator��������
		Iterator iterator=hashmap.keySet().iterator();
		//����ѭ��������iterator.hasNext����Booleanֵ
		while(iterator.hasNext())
		{
			//ȡ��key
			String key=iterator.next().toString();
			//ͨ��keyȡ��value
			Employee temp=(Employee)hashmap.get(key);
			System.out.println("Ա����ţ�"+temp.getNumber()+"��Ա�����֣�"+temp.getName()+"��нˮ��"+temp.getSalary());
		}
	}

}
