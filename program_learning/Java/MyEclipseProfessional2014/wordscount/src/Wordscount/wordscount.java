package Wordscount;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class wordscount {
	
	/**
	 * 主函数部分
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/**
		 * 设置输入输出路径
		 */
		String src = "hdfs://123.57.223.22:9000/user/hadoop/" + args[0];
		String dst = "hdfs://123.57.223.22:9000/user/hadoop/" + args[1];
		
		/**
		 * 最新任务设定
		 */
//		Job job = new Job();
//		job.setJobName("WordsCount");
//		上面两行等同于下面这一行
		Job job = new Job(new Configuration(), "wordscount");
		job.setJarByClass(wordscount.class);
		
		/**
		 * 设置输入输出路径
		 */
		FileInputFormat.addInputPath(job, new Path(src));
		FileOutputFormat.setOutputPath(job, new Path(dst));
		
		/**
		 * 判断输出路径是否存在，如果存在的话删除原文件夹。
		 */
		FileSystem fs= FileSystem.get(URI.create(dst), new Configuration());
		if (fs.exists(new Path(dst)))
			fs.delete(new Path(dst), true);
		
		/**
		 * 设置mapper和reducer
		 */
		job.setMapperClass(WordscountMapper.class);
		job.setReducerClass(WordscountReducer.class);
		
		/**
		 * 设置最终输出类型
		 */
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		/**
		 * 等待程序结束
		 */
		System.exit(job.waitForCompletion(true)? 0:1);
	}
	
	/**
	 * mapper自定义类部分
	 */
	static class WordscountMapper extends Mapper<IntWritable, Text, Text, IntWritable>
	{
		protected void map(IntWritable k1, Text v1, Context context) throws IOException, InterruptedException
		{
			String[] splits = v1.toString().split(" ");
			for (String split : splits)
			{
				context.write(new Text(split), new IntWritable(1));
			}
		}
	}
	
	/**
	 * reducer自定义类部分
	 */
	static class WordscountReducer extends Reducer<Text, IntWritable, Text, IntWritable>
	{
		protected void reduce(Text k1, Iterable<IntWritable> v1, Context context) throws IOException, InterruptedException
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
