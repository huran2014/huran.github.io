package test;
import java.io.*;

public class str_matches {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String Str = new String("Welcome to Tutorialspoint.com");//����Ҫ�ַ�����װ
		String Str="Welcome to Tutorialspoint.com";
		System.out.print("Return Value :" );
		System.out.println(Str.matches("(.*)Tutorials(.*)"));
		
		System.out.print("Return Value :" );
		System.out.println(Str.matches("Tutorials"));
		
		System.out.print("Return Value :" );
		System.out.println(Str.matches("Welcome(.*)"));
	}

}