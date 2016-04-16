/**
 *实现了使用FileSystem以标准输出格式显示Hadoop文件系统中的文件
 */
package filesystemcat;

import java.io.IOException;
import java.net.URI;  //导入java的uri解析包

import org.apache.hadoop.conf.Configuration;//导入hadoop的配置信息
import org.apache.hadoop.fs.FileSystem;  //FileSystem类，是一个与文件系统交互的抽象类，其对HDFS的操作由不同的具体实现子类来实现。
import org.apache.hadoop.fs.FSDataInputStream; //
import org.apache.hadoop.fs.Path;//导入hadoop的hdfs存储信息
import org.apache.hadoop.io.IOUtils;  //导入输入输出工具类

public class FileSystemCat
{

	public static void main(String[] args) throws IOException 
	{
		readfilesystem(args[0]);
	}
	
	public static void readfilesystem(String p) throws IOException
	{
		//生成新的配置
		//Configuration对象封装了客户端或服务器的配置，通过配置文件读取类路径来获取FileSystem实例
		Configuration conf = new Configuration();
		//读取第一个后缀输入将它转换为uri
		String uri = "hdfs://123.57.223.22:9000/user/hadoop/";
		//获取FileSystem实例
		//URI.create(uri)通过解析给定的字符串创建 URI。
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		//定义文档读取对象
//		InputStream in = null;
		FSDataInputStream fsdata = null;
		try{
			//根据uri打开文档
			String path = uri+p;
			fsdata = fs.open(new Path(path));
			//将读取数据放入输出流
			// 参数说明
	        // in - 输入源--->读取HDFS中的文档内容
	        // System.out - 输出源--->输出到界面
	        // 4096 - 缓冲区大小
	        // true - 是否关闭数据流，如果是false，就在finally里关掉
	        //        IOUtils.closeStream(is);
	        //        IOUtils.closeStream(os);
			IOUtils.copyBytes(fsdata, System.out, 4096, false);
		}finally{
			IOUtils.closeStream(fsdata);
		}
	}
}
