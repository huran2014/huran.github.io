package shuzu;
import java.io.*;

public class lessen1 {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
	    System.out.println("������Ҫ��ѯ��С����");
		String str;
		BufferedReader buf;
		buf=new BufferedReader(new InputStreamReader(System.in));
		str=buf.readLine();
		float pingjunzhi=0;
		Dog dogs[]=new Dog[4];
		dogs[0]=new Dog();
		dogs[1]=new Dog();
		dogs[2]=new Dog();
		dogs[3]=new Dog();
		dogs[0].setName("����");
		dogs[0].setWeight(4.5f);
		dogs[1].setName("�װ�");
		dogs[1].setWeight(5.6f);
		dogs[2].setName("�ں�");
		dogs[2].setWeight(7.8f);
		dogs[3].setName("���");
		dogs[3].setWeight(9.0f);
		Dog maxdog=new Dog();
		maxdog=dogs[0];
		Dog mindog=new Dog();
		mindog=dogs[0];
		Dog jieguodog=new Dog();
		for(int i=0;i<dogs.length;i++)
		{
			pingjunzhi+=dogs[i].weight;
			if(dogs[i].weight>=maxdog.weight)
				maxdog=dogs[i];
			else mindog=dogs[i];
			if(str.equals(dogs[i].name)) jieguodog=dogs[i];
		}
		System.out.println("С����ƽ�������ǣ�"+pingjunzhi/dogs.length+"Kg");
		System.out.println("���ص�С���ǣ�"+maxdog.name+"���������ǣ�"+maxdog.weight+"Kg");
		System.out.println("�����С���ǣ�"+mindog.name+"���������ǣ�"+mindog.weight+"Kg");
		System.out.println("������С���������ǣ�"+jieguodog.name+"���������ǣ�"+jieguodog.weight+"Kg");
	}

}

class Dog
{
	public String name;
	public float weight;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	
}


