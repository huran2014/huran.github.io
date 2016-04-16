/**
 * 这个程序是为了清理Hanlp的DATA文件夹内的无关内容
 * 方法是：
 * 1、遍历父目录下的所有文件或者文件夹
 * 2、判断是否为文件，文件的话继续运行，否则进行步骤3
 * 	2.1、判断文件是否为.bin或.data文件，如果是，则删除，如果不是.txt文件，则跳过，否则，进行步骤2.2
 * 	2.2、读取文件内容，提取文本读取迭代器
 * 	2.3、对每行内容进行切分，判断第二属性词是否存在，如果不存在则取消对文档的操作，
 * 	2.4、如果存在第二属性词，则判断是否为ng、ns、m、w,如果不是，则删除这部分内容
 * 3、如果是文件夹，则继续进行文件夹内操作
 */
package com.stczwd.HANLPTest;

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

public class FileDataOperation {
	private static String rootPath="F:/LJQ/Java/Workspaces/MyEclipseProfessional2014/SpringTest/target/classes/data/dictionary";
	public static void main(String[] args) {
//		directoryOperation(rootPath);
//		filedelete(rootPath);
		filedelete2(rootPath);
	}
	
	/**
	 * 
	 */
	public static void directoryOperation(String directoryPath) {
		File directoryFile = new File(directoryPath);
		for (File file : directoryFile.listFiles()) {
			System.out.println("This file is :"+file.getName());
			if (file.isDirectory()) {
				System.out.println("This is a directory");
				directoryOperation(file.getAbsolutePath());
			}
			else {
				//提取文件的后缀名，查看是否是bin文件或者dat文件
				String fileName = file.getName();
				int period = fileName.lastIndexOf('.');
				String fileTypeName = fileName.substring(period+1, fileName.length());
				System.out.println(fileTypeName);
				if (fileTypeName.equals("bin") || fileTypeName.equals("dat")) {
					file.delete();
					System.out.println("删除文件:"+fileName);
				}
				else {
					if (fileTypeName.equals("txt")) {
						System.out.println("开始对"+fileName+"文件进行操作");
						fileOperation(file.getAbsolutePath());
					}
				}
			}
		}
	}
	
	/**
	 * 
	 */
	public static void fileOperation(String datafilePath) {
		try {
			File file = new File(datafilePath);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			File newFile = new File(file.getAbsolutePath()+".1");
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFile)));
			String line = null;
			bufferedWriter.write(bufferedReader.readLine()+"\r\n");
			while ((line = bufferedReader.readLine()) != null) {
				//开始切分每一行，以空格切分
				String[] lineWords = line.split(" ");
				if (lineWords.length<=1) {
					lineWords = line.split("\t");
					if (lineWords.length<=1) {
						continue;
					}
				}
//				System.out.println(file.getName()+":\t"+lineWords[1]);
				if (lineWords[1].equals("ns") || lineWords[1].equals("ng")
						|| lineWords[1].equals("w") || lineWords[1].equals("m")) {
					bufferedWriter.write(line+"\r\n");
				}
				if (lineWords[1].equals("v") && lineWords[0].equals("到")) {
					bufferedWriter.write(line+"\r\n");
				}
			}
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param directoryPath
	 */
	public static void filedelete(String directoryPath) {
		File directoryFile = new File(directoryPath);
		for (File file : directoryFile.listFiles()) {
			System.out.println("This file is :"+file.getName());
			if (file.isDirectory()) {
				System.out.println("This is a directory");
				filedelete(file.getAbsolutePath());
			}
			else {
				//提取文件的后缀名，查看是否是bin文件或者dat文件
				String fileName = file.getName();
				int period = fileName.lastIndexOf('.');
				String fileTypeName = fileName.substring(period+1, fileName.length());
				System.out.println(fileTypeName);
				if (fileTypeName.equals("1")) {
//					file.delete();
//					System.out.println("删除文件:"+fileName);
//					System.out.println(file.getParentFile()+"\\"+fileName.substring(0, period));
					new File(file.getParentFile()+"\\"+fileName.substring(0, period)).delete();
					file.renameTo(new File(file.getParentFile()+"\\"+fileName.substring(0, period)));
				}
			}
		}

	}
	
	/**
	 * 
	 * @param directoryPath
	 */
	public static void filedelete2(String directoryPath) {
		File directoryFile = new File(directoryPath);
		for (File file : directoryFile.listFiles()) {
			System.out.println("This file is :"+file.getName());
			if (file.isDirectory()) {
				System.out.println("This is a directory");
				filedelete2(file.getAbsolutePath());
			}
			else {
				//提取文件的后缀名，查看是否是bin文件或者dat文件
				String fileName = file.getName();
				int period = fileName.lastIndexOf('.');
				String fileTypeName = fileName.substring(period+1, fileName.length());
				System.out.println(fileTypeName);
				if (fileTypeName.equals("1")) {
					file.delete();
//					System.out.println("删除文件:"+fileName);
//					System.out.println(file.getParentFile()+"\\"+fileName.substring(0, period));
//					new File(file.getParentFile()+"\\"+fileName.substring(0, period)).delete();
//					file.renameTo(new File(file.getParentFile()+"\\"+fileName.substring(0, period)));
				}
				if (fileTypeName.equals("bin") || fileTypeName.equals("dat")) {
					file.delete();
					System.out.println("删除文件:"+fileName);
				}
			}
		}

	}
}
