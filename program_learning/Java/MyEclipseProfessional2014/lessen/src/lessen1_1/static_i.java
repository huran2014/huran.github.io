package lessen1_1;

public class static_i {
	static int i=1;
	static
	{
		//��仰ֻ�ᱻִ��һ��
		System.out.println("������ֻ������һ��");
		i++;
	}
	public static_i()
	{
		System.out.println("����������������");
		i++;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		static_i s1=new static_i();
		System.out.println(s1.i);
		static_i s2=new static_i();
		System.out.println(s2.i);
	}

}
