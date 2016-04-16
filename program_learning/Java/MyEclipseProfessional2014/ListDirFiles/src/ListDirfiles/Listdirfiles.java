package ListDirfiles;
import java.io.*;//导入java的io接口包
import java.net.URI;//导入Java的URI地址包

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;//导入HDFS的文件系统包
import org.apache.hadoop.conf.Configuration;//导入hadoop的配置包
import org.apache.hadoop.fs.Path;//导入HDFS的路径包

public class Listdirfiles {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		listdirfiles(args[0]);
	}
	
	public static void listdirfiles(String dst) throws IOException
	{
		String url = "hdfs://123.57.223.22:9000/user/hadoop/";
		FileSystem fs = FileSystem.get(URI.create(dst), new Configuration());
		url += dst;
		FileStatus[] files = fs.listStatus(new Path(url));
		for (FileStatus file : files)
		{
			String type = file.isDir()? "目录：":"文件：";
			String name = file.getPath().getName();
			System.out.println(type + " " + name);
		}
	}

}
