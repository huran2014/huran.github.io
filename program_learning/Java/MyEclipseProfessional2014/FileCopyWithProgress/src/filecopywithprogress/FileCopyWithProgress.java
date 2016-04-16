/**
 *实现了将本地文件复制到Hadoop文件系统中
 *其中，第二个目标地址要写明具体的文件名
 */
package filecopywithprogress;

import java.io.*;//导入java的io包
import java.net.URI;//导入java的URI包
import org.apache.hadoop.conf.Configuration;//导入hadoop的配置信息
import org.apache.hadoop.fs.Path;//导入hadoop的hdfs存储信息
import org.apache.hadoop.fs.FileSystem;  //FileSystem类，是一个与文件系统交互的抽象类，其对HDFS的操作由不同的具体实现子类来实现。
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class FileCopyWithProgress
{
	public static void main(String[] args) throws Exception
	{
		filecopyfromlocal(args[0],args[1]);
	}
	
	//上传文档
	public static void filecopyfromlocal(String p1, String p2) throws IOException
	{

		//通过读取第一个后缀输入，获取到本地文件路径
		String localSrc = p1;
		//通过读取第二个后缀输入，获取到HDFS的目标存储路径
		String dst = "hdfs://123.57.223.22:9000/user/hadoop/"+p2;
		//BufferedInputStream是一个带有缓冲区域的InputStream
		//FileInputStream创建字节输入流，将本地文件放入输入流中
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));

		//生成新的配置
		//Configuration对象封装了客户端或服务器的配置，通过配置文件读取类路径来获取FileSystem实例
		Configuration conf = new Configuration();
		//获取FileSystem实例
		//URI.create(det)根据读入的uri字符串获取到URI
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		//创建新的输出文档]
		//Path()生成新的path路径
		//Progressable()，重载方法，用来传递毁掉接口
		OutputStream out = fs.create(new Path(dst), new Progressable()
		{
			//每次调用progress()，都会将64KB的数据包写入datanode管线，然后打印一个时间点来显示整个运行过程
			public void progress()
			{
				System.out.print(".");
			}
		});

		//将读取数据放入输出流
		// 参数说明
       // in - 输入源--->读取本地中的文档内容
       // out - 输出源--->输出到HDFS中
       // 4096 - 缓冲区大小
       // true - 是否关闭数据流，如果是false，就在finally里关掉
       //        IOUtils.closeStream(is);
       //        IOUtils.closeStream(os);
		IOUtils.copyBytes(in, out, 4096, true);
	}
}
