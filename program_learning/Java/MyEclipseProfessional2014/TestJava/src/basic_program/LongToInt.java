package basic_program;

public class LongToInt {

	public static void main(String[] args) {
		//		long num = 2147489999L;
		//		long num = 2147483648L;
		//		long num = 4294967295L;
		//		System.out.println(hammingWeight((int)num));
		System.out.println(sumClimbStairs(44, 0));
	}

	public static long sumClimbStairs(int n,int count) {
		System.out.print("n="+n+"\tcount="+count);
		long nsum=1;
		long countsum=1;
		int nloop = n;
		int loop=count;
		while(loop!=0) {
			nsum *= nloop--;
			countsum *= loop--;
		}
		System.out.println("\t\t"+nsum/countsum);
		if((n-count)/2!=0)
			return nsum/countsum + sumClimbStairs(n-1,count+1);
		else
			return nsum/countsum;
	}

	public static int hammingWeight(int n) {
		if(n<0) {
			/**
			 * 100000000000111，对于负数，其前缀是1代表负数，后面是具体的数据。
			 * 在Java中，虽然int的值可以为负，但是仍然可以通过减去Integer.MAX_VALUE+1的方法获得后面的111.这样就很容易获得后面数据的1的数量了
			 * 解题方法是右移的方法，每次向右移一位，并获取这一位的值。
			 * Integer.MAX_VALUE/2+1为1000000000000右移一位的结果
			 */
			return (n-Integer.MAX_VALUE-1)%2+hammingWeight((Integer.MAX_VALUE/2+1)+(n-Integer.MAX_VALUE-1)/2);
		}
		if(n/2==0)
			return n;
		return n%2 + hammingWeight(n/2);
	}
}