/**
 * topk最主要部分
 * 根据Wordcount统计的单词数量，统计出最高的k值
 * 使用最小堆排序法
 */
package com.Top_K;

import java.io.EOFException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class top_k 
{
	private static String work_path;
	private static int k=10;
	static Logger log = Logger.getLogger(top_k.class.getName());  

    public static int getK() {
		return k;
	}

	@SuppressWarnings("static-access")
	public top_k(String work_path,int k) {
		super();
		this.work_path = work_path;
		this.k = k;
	}

	public static void topksork() throws IOException, ClassNotFoundException, InterruptedException {
		// TODO Auto-generated method stub
		
		String src = "hdfs://master:9000/user/hadoop/"+work_path+"/wordcount_output/";
		String dst = "hdfs://master:9000/user/hadoop/"+work_path+"/topk_output";
		
		//检查输出路径是否存在，存在的话删除
		FileSystem fs = FileSystem.get(URI.create(dst), new Configuration());
		if (fs.exists(new Path(dst)))
		{
			fs.delete(new Path(dst), true);
		}
		
		//定义新的Job
		Job job = new Job(new Configuration(), "topk");
		job.setJarByClass(top_k.class);
		
		//设置输入输出路径
		FileInputFormat.addInputPath(job, new Path(src));
		FileOutputFormat.setOutputPath(job, new Path(dst));
		
		//定义job的mapper和reducer函数
		job.setMapperClass(TopkMapper.class);
		job.setReducerClass(TopkReducer.class);
		
		//定义最终输出类型
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		//等待程序结束
		//System.exit(job.waitForCompletion(true)? 0:1);
		System.out.println("The job of topk sort is ");
		System.out.println(job.waitForCompletion(true)? 0:1);
	}

	/**
	 * 自定义的mapper类
	 * @author wangchao
	 * 根据设置的k值大小，获取前k个值
	 */
	public static class TopkMapper extends Mapper<LongWritable, Text, Text, IntWritable>
	{
		private static HashMap<Integer, Text> map_m = new HashMap<Integer, Text>();
		private static ArrayList<Integer> topk_m = new ArrayList<Integer>();
		
		public void map(LongWritable k1, Text v1, Context context) throws IOException, EOFException, InterruptedException
		{
			if(v1.getLength()>2)
			{			
				//交换一下键值对信息，并把信息保存到Hashmap里面
				String[] splits = v1.toString().split("\t");
				String value = splits[0];
				int key=0;
				for(int i=1;i<splits.length;i++){
					if(i==splits.length-1)
						key = Integer.parseInt(splits[splits.length-1]);
					else
						value += splits[i];
				}
				map_m.put(key, new Text(value));
				
				//直接对arraylist进行插入排序，使其总长度总小于或等于k
				//如果插入后长度超过k，就删除排序后的最后一个值。
				topk_m.add(topk_m.size(), key);
				if(topk_m.size()>1);
					quickSort(0,topk_m.size()-1);
				if (topk_m.size() > top_k.getK())
				{
					topk_m.remove(topk_m.size()-1);
					log.info("delete one member.\n");
				}
				log.info(""+topk_m.size());
			}
		}
		
		/**
		 * 在MapTask中Map阶段有4个和新方法，setup()、map()、cleanup()、run()
		 * setup()方法用来执行一些map()运行之前的操作
		 * map()方法是用户重写的执行部分
		 * cleanup()方法是用来最后处理一些清理工作用的。
		 * run()方法用来规定map阶段的顺序 setup-map-cleanup
		 * 本次所做的top_k值得输出就在cleanup中执行
		 * @throws InterruptedException 
		 * @throws IOException 
		 */
		protected void cleanup(TopkMapper.Context context) throws IOException, InterruptedException
		{
			for(int i=0;i<topk_m.size();i++)
			{
				context.write(map_m.get(topk_m.get(i)), new IntWritable(topk_m.get(i)));
//				context.write(new Text(""+i), new IntWritable(topk_m.get(i)));
			}
		}
		
	    /**
	     * 先按照数组为数据原型写出算法，再写出扩展性算法。
	     * @return 
	     */  
	    public static void quickSort(int left,int right){  
	        int pivot;  
	        if (left < right) {  
	            //pivot作为枢轴，较之小的元素在左，较之大的元素在右  
	            pivot = partition(left, right);  
	            //对左右数组递归调用快速排序，直到顺序完全正确  
	            quickSort(left, pivot - 1);  
	            quickSort(pivot + 1, right);  
	        }
	    }  
	      
	    public static int partition(int left,int right){  
	        int pivotkey = (Integer)topk_m.get(left);
	        //枢轴选定后永远不变，最终在中间，前小后大  
	        while (left < right) {  
	            while (left < right && (Integer)topk_m.get(right) <= pivotkey) --right;  
	            //将比枢轴小的元素移到低端，此时right位相当于空，等待低位比pivotkey大的数补上  
	            topk_m.set(left, topk_m.get(right));
	            while (left < right && (Integer)topk_m.get(left) >= pivotkey) ++left;  
	            //将比枢轴大的元素移到高端，此时left位相当于空，等待高位比pivotkey小的数补上  
	            topk_m.set(right, topk_m.get(left));
	        }  
	        //当left == right，完成一趟快速排序，此时left位相当于空，等待pivotkey补上  
	        topk_m.set(left, pivotkey);  
	        return left;  
	    }  
	}
	
	/**
	 * 自定义的reducer类
	 * @author wangchao
	 * 对数据进行排序和取序列。具体操作和mapper相同
	 */
	public static class TopkReducer extends Reducer<Text, IntWritable, Text, IntWritable>
	{
		private static HashMap<Integer, String> map = new HashMap<Integer, String>();
		private static ArrayList<Integer> topk = new ArrayList<Integer>();
		
		public void reduce(Text k1, IntWritable v1, Context context) throws IOException, InterruptedException
		{
			//直接对arraylist进行插入排序，使其总长度总小于或等于k
			//如果插入后长度超过k，就删除排序后的最后一个值。
			map.put(v1.get(), k1.toString());
			topk.add(topk.size(), v1.get());
			if(topk.size()>1);
				quickSort(0,topk.size()-1);
			if (topk.size() > top_k.getK())
				topk.remove(topk.size()-1);
		}
		
		/**
		 * Reduce阶段也存在cleanup函数，也可以在这里进行操作
		 * @throws InterruptedException 
		 * @throws IOException 
		 */
		protected void cleanup(TopkReducer.Context context) throws IOException, InterruptedException
		{
			for(int i=0;i<topk.size();i++)
			{
				context.write(new Text("The word:"+map.get(topk.get(i))), new IntWritable(topk.get(i)));
			}
		}
		
	    /**
	     * 先按照数组为数据原型写出算法，再写出扩展性算法。
	     * @return 
	     */  
	    public static void quickSort(int left,int right){  
	        int pivot;  
	        if (left < right) {  
	            //pivot作为枢轴，较之小的元素在左，较之大的元素在右  
	            pivot = partition(left, right);  
	            //对左右数组递归调用快速排序，直到顺序完全正确  
	            quickSort(left, pivot - 1);  
	            quickSort(pivot + 1, right);  
	        }
	    }  
	      
	    public static int partition(int left,int right){  
	        int pivotkey = (Integer)topk.get(left);
	        //枢轴选定后永远不变，最终在中间，前小后大  
	        while (left < right) {  
	            while (left < right && (Integer)topk.get(right) <= pivotkey) --right;
	            //将比枢轴小的元素移到低端，此时right位相当于空，等待低位比pivotkey大的数补上  
	            topk.set(left, topk.get(right));
	            while (left < right && (Integer)topk.get(left) >= pivotkey) ++left;  
	            //将比枢轴大的元素移到高端，此时left位相当于空，等待高位比pivotkey小的数补上  
	            topk.set(right, topk.get(left));
	        }  
	        //当left == right，完成一趟快速排序，此时left位相当于空，等待pivotkey补上  
	        topk.set(left, pivotkey);  
	        return left;  
	    }  
	}
}