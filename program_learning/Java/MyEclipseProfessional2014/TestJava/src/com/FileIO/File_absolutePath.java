package com.FileIO;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.common.iterator.FileLineIterator;

import com.google.common.io.Closeables;


public class File_absolutePath {
	  private static final char COMMENT_CHAR = '#';

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File_get fg=new File_get("pom.xml");
		if(fg.checknotnull())
		{
			if(fg.getDataFile().exists())
				System.out.println(fg.getDataFile().getAbsolutePath());
			else
				System.out.println("No such file!");
		}
		System.out.println("dataFile.length()="+fg.getDataFile().length());
		System.out.println(fg.getDataFile().getName().indexOf('.'));
		
		FileLineIterator iterator = new FileLineIterator(fg.getDataFile());
	    //读取第一行数据。peek方法默认返回第一个对应的对象
	    String firstLine = iterator.peek();
	    while (firstLine.isEmpty() || firstLine.charAt(0) == COMMENT_CHAR) {
	    	System.out.println(firstLine);
	      iterator.next();
	      firstLine = iterator.peek();
	    }
	    System.out.println(firstLine);
	    Closeables.close(iterator, true);
	}

}

class File_get
{
	private File dataFile;
	
	public File getDataFile() {
		return dataFile;
	}
	public void setDataFile(File dataFile) {
		this.dataFile = dataFile;
	}
	
	public File_get(String dataPath) {
		super();
		this.dataFile=new File(dataPath);
	}
	
	public boolean checknotnull()
	{
		if(this.dataFile.getAbsoluteFile()==null)
			return false;
		return true;
	}
	
	public boolean checkfilenotull()
	{
		if(this.dataFile.exists())
			return false;
		return true;
	}
}