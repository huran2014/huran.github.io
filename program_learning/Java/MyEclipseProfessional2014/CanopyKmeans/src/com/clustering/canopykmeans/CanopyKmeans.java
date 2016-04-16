package com.clustering.canopykmeans;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.conversion.InputDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.ClassUtils;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure;
import org.apache.mahout.utils.clustering.ClusterDumper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class CanopyKmeans
{
	private static final Logger log = LoggerFactory.getLogger(CanopyKmeans.class);
	
	public static void main(String[] args)  throws Exception
	{
		// TODO Auto-generated method stub
		if(args.length>0)
		{
			log.info("Running with only user-supplied arguments");
			run(args);
		}
	}
	
	/**
	 * 输入参数处理部分
	 * -i input -o output -t1 10 -t2 1 -m measure -d 0.5 -mx 5
	 * @param args
	 * @throws Exception 
	 * @throws IllegalArgumentException 
	 */
	public static void run(String[] args) throws IllegalArgumentException, Exception
	{
		//定义各参数默认值
		String input = "hdfs://123.57.223.22:9000/user/hadoop/canopykmeans/data/";
		String output = "hdfs://123.57.223.22:9000/user/hadoop/canopykmeans/output/";
		String measureclass = SquaredEuclideanDistanceMeasure.class.getName();
		double t1 = 10;
		double t2 = 1;
		double convergenceDelta = 0.5;
		int maxIterations = 5;
		
		//遍历各个参数属性
		for (int i=0;i<args.length;i++)
		{
			//判断参数类型，并进行相应处理
			if (args[i].equals("-i"))//输入路径
			{
				input = "hdfs://123.57.223.22:9000/user/hadoop/" + args[i+1];
				i++;//跳过下一个参数
			}
			else if (args[i].equals("-o"))//输出路径
			{
				output = "hdfs://123.57.223.22:9000/user/hadoop/" + args[i+1];
				i++;//跳过下一个参数
			}
			else if (args[i].equals("-t1"))//t1距离值
			{
				t1 = Double.parseDouble(args[i+1]) ;
				i++;//跳过下一个参数
			}
			else if (args[i].equals("-t2"))//t2距离值
			{
				t2 = Double.parseDouble(args[i+1]) ;
				i++;//跳过下一个参数
			}
			else if (args[i].equals("-m"))//距离计算方法measure
			{
				measureclass = args[i+1];
				i++;//跳过下一个参数
			}
			else if (args[i].equals("-d"))//收敛度
			{
				convergenceDelta = Double.parseDouble(args[i+1]);
				i++;//跳过下一个参数
			}
			else if (args[i].equals("-mx"))
			{
				maxIterations = Integer.parseInt(args[i+1]);
				i++;//跳过下一个参数
			}
		}
		
		//删除输出目录
		FileSystem fs = FileSystem.get(URI.create(output), new Configuration());
		if (fs.exists(new Path(output)))
			fs.delete(new Path(output), true);
		
		//设置measure距离算法
	    DistanceMeasure measure = (DistanceMeasure)ClassUtils.instantiateAs(measureclass, DistanceMeasure.class);
	    
	    //运行总程序
	    run(new Configuration(), new Path(input), new Path(output), measure, t1, t2, convergenceDelta, maxIterations);
	}
	
	//核心算法部分
	public static void run(Configuration conf, Path input, Path output, DistanceMeasure measure, double t1, double t2, double convergenceDelta, int maxIterations)
		    throws Exception
	{
		//基于output父路径，生成data子路径
		Path directoryContainingConvertedInput = new Path(output, "data");
		log.info("Preparing Input");
		//对输入文件进行处理，使输入文件变为（key是Text，value是VectorWritable的）SequenceFileOutput文件
		InputDriver.runJob(input, directoryContainingConvertedInput, "org.apache.mahout.math.RandomAccessSparseVector");
		log.info("Running Canopy to get initial clusters");
		//基于output父路径，生成canopies子路径
		Path canopyOutput = new Path(output, "canopies");
		//首先进行canopy初始簇中心的选取
		CanopyDriver.run(new Configuration(), directoryContainingConvertedInput, canopyOutput, measure, t1, t2, false, 0D, false);
		//然后进行kmeans簇中心优化
		log.info("Running KMeans");
		KMeansDriver.run(conf, directoryContainingConvertedInput, new Path(canopyOutput, "clusters-0-final"), output, convergenceDelta, maxIterations, true, 0D, false);
		//根据簇中心完成聚类
		ClusterDumper clusterDumper = new ClusterDumper(new Path(output, "clusters-*-final"), new Path(output, "clusteredPoints"));		
		clusterDumper.printClusters(null);
	  }
}