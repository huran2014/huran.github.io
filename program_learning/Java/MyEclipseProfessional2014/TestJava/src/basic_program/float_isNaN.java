package basic_program;

public class float_isNaN {

	private static float test_number = (float) 1.6;
	private static float test_number1 = (float) (0.0/0.0);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//NaN=not a number
		System.out.println(Float.isNaN(test_number));
		System.out.println(Float.isNaN(test_number1));
		System.out.println(test_number1);

		//INFINITY无穷大
		//Float.NEGATIVE_INFINITY=-1.0/0.0，负无穷大
	    float maxPrefValue = Float.NEGATIVE_INFINITY;
		//Float.NEGATIVE_INFINITY=1.0/0.0，正无穷大
	    float minPrefValue = Float.POSITIVE_INFINITY;
	    System.out.println("Float.NEGATIVE_INFINITY = "+(long)Float.NEGATIVE_INFINITY);
	    System.out.println("Float.POSITIVE_INFINITY = "+(long)Float.POSITIVE_INFINITY);
	}
	
}
