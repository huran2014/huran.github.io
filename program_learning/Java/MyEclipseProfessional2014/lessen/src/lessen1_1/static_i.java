package lessen1_1;

public class static_i {
	static int i=1;
	static
	{
		//这句话只会被执行一次
		System.out.println("在这里只运行了一次");
		i++;
	}
	public static_i()
	{
		System.out.println("在这里运行了两次");
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
