package stczwd.Polymorphism;

public class PolymorphismClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Object cat = new Animal();
		//查看是否会定位到这个类型
		System.out.println(cat.getClass());
	}

}

class Animal
{
	public void call()
	{
		
	}
}