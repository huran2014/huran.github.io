package com.authorlog;

import java.io.EOFException;
import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AuthLog {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		
		//定义输入输出路径
//		String src = "hdfs://123.57.223.22:9000/user/hadoop/" + args[0];
//		String dst = "hdfs://123.57.223.22:9000/user/hadoop/" + args[1];
		String src = "hdfs://123.57.223.22:9000/user/hadoop/authlog/data";
		String dst = "hdfs://123.57.223.22:9000/user/hadoop/authlog/output";
		
		//检查输出路径是否存在，存在的话删除
		FileSystem fs = FileSystem.get(URI.create(dst), new Configuration());
		if (fs.exists(new Path(dst)))
		{
			fs.delete(new Path(dst), true);
		}
		
		//定义新的Job
		Job job = new Job(new Configuration(), "author_log");
		job.setJarByClass(AuthLog.class);
		
		//设置输入输出路径
		FileInputFormat.addInputPath(job, new Path(src));
		FileOutputFormat.setOutputPath(job, new Path(dst));
		
		//定义job的mapper和reducer函数
		job.setMapperClass(AuthlogMapper.class);
		job.setReducerClass(AuthlogReducer.class);
		
		//设置reducer分区类型和数量
		job.setNumReduceTasks(2);
		job.setPartitionerClass(AuthLogPartitioner.class);
		
		//定义最终输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(AuthorWritable.class);
		
		//等待程序结束
		System.exit(job.waitForCompletion(true)? 0:1);

	}

	//自定义的mapper类
	public static class AuthlogMapper extends Mapper<LongWritable, Text, Text, AuthorWritable>
	{
		public void map(LongWritable k1, Text v1, Context context) throws IOException, EOFException, InterruptedException
		{
			String[] splits = v1.toString().split(" ");
			Text  key = new Text(splits[10]);
			AuthorWritable value = new AuthorWritable(splits[0], splits[1], 1);
			//System.out.println(key + "\t" + value.toString());
			context.write(key,value);
		}
	}
	
	//自定义的reducer类
	public static class AuthlogReducer extends Reducer<Text, AuthorWritable, Text, AuthorWritable>
	{
		public void reduce(Text k1, Iterable<AuthorWritable> v1, Context context) throws IOException, InterruptedException
		{
			AuthorWritable author = new AuthorWritable("","",0);
			for ( AuthorWritable value_temp : v1 )
			{
				
				/**
				 * 问题出现在这里，value在经过shuffle后，不知道为什么成为了1个整体
				 * 所以在这里先事先处理一下数据
				 */
				String str_temp = value_temp.getMonth();
				AuthorWritable value = new AuthorWritable();
				value.setMonth(str_temp.substring(0, 3));
				value.setDay(str_temp.substring(3, 5));
				value.setLogin("1");
//			for (AuthorWritable value : v1)
//			{
//				if (value.getMonth()!=null)
//				{
//					System.out.println(author.getMonth() + "\t" + value.getMonth());
//					System.out.println(author.getDay() + "\t" + value.getDay());
//					System.out.println(author.getLogin() + "\t" + value.getLogin());
//					if (value.getMonth().equals(author.getMonth()) && value.getDay().equals(author.getDay()))
//						System.out.println("OK");
//				}
				
				if (value.getMonth().equals(author.getMonth()) && value.getDay().equals(author.getDay()))
				{
					int times = Integer.parseInt(author.getLogin()) + Integer.parseInt(value.getLogin());
					author.setLogin(String.valueOf(times));
//					author.setLogin(author.getLogin()+value.getLogin());//同月同日内增加登陆次数
				}
				else//非同月同天
				{
					if (author.getMonth().length()>=3)//排除空结果
					{
						context.write(k1, author);
					}
					//记录下非相同的信息，并进入下次循环
					author.setMonth(value.getMonth());
					author.setDay(value.getDay());
					author.setLogin(value.getLogin());
				}
			}
			context.write(k1, author);
		}
	}

	//针对mapper输出所做的分区
	static class AuthLogPartitioner extends Partitioner<Text, AuthorWritable>
	{
		//设置根据用户名不同，分配不同的任务
		@Override
		public int getPartition(Text arg0, AuthorWritable arg1, int arg2) {
			// TODO Auto-generated method stub
			if (arg0.getBytes().toString().equals("hadoop")) 
				return 0;
			if (arg0.getBytes().toString().equals("root")) 
				return 1;
			return arg2;
		}
	}
}
