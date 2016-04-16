/*
 * 对符号而言
 * 1、正数的原码、反码、补码都一样
 * 2、负数的反码=原码符号位不变，其他位取反
 * 3、负数的补码=反码+1
 * 4、0的反码、补码都是0
 * 5、Java中没有无符号数，也就是说，Java中的数都是有符号的
 * 6、在计算机运算的时候，都是以补码的方式来运算的
 */
/*
 * 3个移位运算符
 * 1、>>算术右移：低位溢出，符号位不变，用符号位补溢出的高位
 * 2、<<算术左移：符号位不变，低位补0
 * 3、>>>逻辑右移：低位溢出，高位补0
 */
package erjinzhi;

public class yunsuan {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 算术右移 001-》000
		int a = 1 >> 2;
		// 算术右移 101-》111->补码->101
		int b = -1 >> 2;
		// 算术左移 001-》100
		int c = 1 << 2;
		// 算术左移 1001-》1100（负数）
		int d = -1 << 2;
		// 逻辑右移 011-》000
		int e = 3 >>> 2;
		// 取反 ~0010=1101
		int f = ~2;
		// 按位与 0010&0011=0010
		int g = 2 & 3;
		// 按位或 0010|0011=0011
		int h = 2 | 3;
		// 取反 11001-》反码-》10110-》补码-》~10111=01000
		int i = ~-5;
		// 按位与 1101&0111=0101
		int j = 13 & 7;
		// 按位或 101|100=101
		int k = 5 | 4;
		// 按位异或 1011-》补码-》1101^0011-》1110-》补码-》1010
		int l = -3 ^ 3;
		// 输出运算结果：
		System.out.println("1>>2=" + a);
		System.out.println("-1>>2=" + b);
		System.out.println("1<<2=" + c);
		System.out.println("-1<<2=" + d);
		System.out.println("3>>>2=" + e);
		System.out.println("~2=" + f);
		System.out.println("2&3=" + g);
		System.out.println("2|3=" + h);
		System.out.println("~-5=" + i);
		System.out.println("13&7=" + j);
		System.out.println("5|4=" + k);
		System.out.println("-3^3=" + l);
	}

}
