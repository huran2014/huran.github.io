package com.FileIO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileIOinout {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileOperation fo = new FileOperation("C:\\Users\\minelab\\Desktop\\ggj\\wangyi_news\\news.php");
		fo.FileRead();
	}

}

class FileOperation
{
	private File dataFile;

	public FileOperation(File dataFile) {
		super();
		this.dataFile = dataFile;
	}

	public FileOperation(String FilePath) {
		super();
		this.dataFile = new File(FilePath);
	}
	
	public void FileRead()
	{
		/**
		 * 将文件转换成输入流
		 * FileInputStream是字节流
		 * InputSreamReader是字符流
		 * try/catch是单独的作用域，里面创建的变量外部无法使用
		 * 关闭的时候要先关闭的后关闭，后打开的先关闭
		 */
		try {
			InputStreamReader is = new InputStreamReader(new FileInputStream(this.dataFile),"utf-8");
			//将字符流InputStreamReader转换成带有缓冲的输入流
			BufferedReader bf = new BufferedReader(is);
			String Fileline=null;
			File newFile = new File("C:\\Users\\minelab\\Desktop\\ggj\\wangyi_news\\news.php.bak");
			while((Fileline = bf.readLine())!=null)
			{
				//如果程序中没有注释字符，则进行打印
				if (Fileline.indexOf('/')<0)
				{
					System.out.println(Fileline);
					this.FileWrite(Fileline,newFile);
				}
			}
			bf.close();
			is.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void FileWrite(String line, File newFile)
	{
		try {
			OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(newFile,true),"utf-8");
			BufferedWriter bfw = new BufferedWriter(os);
			bfw.write(line+"\n");
			bfw.close();
			os.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}