/**
 * 文章分词统计技术
 */
package com.Top_K;

import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
//import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class wordcount {
	private static String work_path;

	@SuppressWarnings("static-access")
	public wordcount(String work_path) {
		super();
		this.work_path = work_path;
	}

	public String getWork_path() {
		return work_path;
	}

	@SuppressWarnings("static-access")
	public void setWork_path(String work_path) {
		this.work_path = work_path;
	}

	public static void wordcount() throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		
		String src = "hdfs://master:9000/user/hadoop/"+work_path+"/input";
		String dst = "hdfs://master:9000/user/hadoop/"+work_path+"/wordcount_output";
		
		//检查输出路径是否存在，存在的话删除
		FileSystem fs = FileSystem.get(URI.create(dst), new Configuration());
		if (fs.exists(new Path(dst)))
		{
			fs.delete(new Path(dst), true);
		}
		
		//定义新的Job
		Job job = new Job(new Configuration(), "wordcount");
		job.setJarByClass(wordcount.class);
		
		//设置输入输出路径
		FileInputFormat.addInputPath(job, new Path(src));
		FileOutputFormat.setOutputPath(job, new Path(dst));
		
		//定义job的mapper和reducer函数
		job.setMapperClass(WordcountMapper.class);
		job.setReducerClass(WordcountReducer.class);
		
		//定义最终输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//等待程序结束
		//System.exit(job.waitForCompletion(true)? 0:1);
		System.out.println("The job of wordcount is :");
		System.out.println(job.waitForCompletion(true)? 0:1);
	}

	/**
	 * 自定义的mapper类
	 * @author wangchao
	 * 进行二次切分，先切分空格，再切分逗号
	 */
	public static class WordcountMapper extends Mapper<LongWritable, Text, Text, IntWritable>
	{
		public void map(LongWritable k1, Text v1, Context context) throws IOException, EOFException, InterruptedException
		{
			String words = v1.toString();
			//◆文字替换（全部）
			Pattern pattern = Pattern.compile("[^a-zA-Z ]");
			Matcher matcher = pattern.matcher(words);
			//替换所有符合正则的数据
			words = matcher.replaceAll("");
			String[] splits = words.split(" ");
			for(String split : splits)
			{
				context.write(new Text(split), new IntWritable(1));
//				String[] keys = split.split(",");
//				for(String key : keys)
//					context.write(new Text(key),new IntWritable(1));
			}
		}
	}
	
	/**
	 * 自定义的reducer类
	 * @author wangchao
	 * 对每个单词进行统计计数
	 * 保留arraylist数组
	 */
	public static class WordcountReducer extends Reducer<Text, IntWritable, Text, IntWritable>
	{
		public void reduce(Text k1, Iterable<IntWritable> v1, Context context) throws IOException, InterruptedException
		{
			int value = 0;
			for (IntWritable v : v1)
			{
				value += v.get();
			}			
			context.write(k1, new IntWritable(value));
		}
	}
}
