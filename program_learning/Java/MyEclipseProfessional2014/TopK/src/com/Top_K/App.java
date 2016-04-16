package com.Top_K;

import java.io.IOException;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Hello world!
 *
 */
public class App 
{
	/**
	 * 通过运行参数获得工作目录和topk的k值
	 * @param args
	 * @throws ParseException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
    public static void main( String[] args ) throws ParseException, IOException, ClassNotFoundException, InterruptedException
    {
        //首先分析参数和目录属性
    	// Create a Parser 
		CommandLineParser parser = new BasicParser( );
		Options options = new Options( );
		options.addOption("h", "help", false, "Print this usage information");
		options.addOption("w", "work_path", true, "set the word_path of topk environment，the default is top_k" );
		options.addOption("k", "k", true, "select k words from resource, the default is 5");
		// Parse the program arguments
		CommandLine commandLine = parser.parse( options, args );
		// Set the appropriate variables based on supplied options
		String work_path = "top_k";
		int k = 5;
		
		if( commandLine.hasOption('h') ) 
		{
			System.out.println( "Help Message");
		    System.exit(0);
		}
		if( commandLine.hasOption('w') ) 
		{
		    work_path = commandLine.getOptionValue('w');
		}
		if( commandLine.hasOption('k') ) 
		{
			k = Integer.parseInt(commandLine.getOptionValue('k'));
		}
		
		//获取参数后运行主程序
		run(work_path,k);
    }
    
    @SuppressWarnings("static-access")
	public static void run(String work_path, int k) throws IOException, ClassNotFoundException, InterruptedException
    {
    	
//    	//根据工作目录定义输出目录
//    	String dst = "hdfs://master:/usr/hadoop/" + work_path + "/topk_output";
    	
    	//定义WordCount,对文档内的单词进行分析和统计
    	wordcount word_count=new wordcount(work_path);
    	word_count.wordcount();//进行文档处理
    	
    	//Topk进行分析，获得一个
    	top_k top_k = new top_k(work_path,k);
    	top_k.topksork();
    	
    }
}
