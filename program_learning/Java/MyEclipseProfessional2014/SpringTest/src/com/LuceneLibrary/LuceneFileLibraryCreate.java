package com.LuceneLibrary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;

public class LuceneFileLibraryCreate {

	public static void main(String[] args) {

		//创建分词器和写入配置
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		try {
			//保存词典路径
			Directory directory = FSDirectory.open(Paths.get("lucene/File"));
			IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
			Document document = null;

			/**
			 * 读取文件夹
			 * 一层层分析：
			 * 1、分析totalFile是否为目录
			 * 2、获取totalFile目录下的所有文件
			 * 3、逐个判断是否为目录
			 * 	3.1  获取目录下的所有文件
			 * 	3.2 遍历每个文件
			 * 		3.2.1 获取文件内容的迭代器，以行为单位
			 * 		3.2.2 依次获取每一行的内容，并将其和文件名、父目录名保存到词典中
			 */
			//获取总路径
			File totalFile = new File("C:/Users/minelab/Desktop/中国");
			if (!totalFile.isDirectory()) {
				System.err.println("总路径错误，总路径是个文件");
				new Exception().printStackTrace();;
				System.exit(-1);
			}
			//遍历总路径中的每个文件夹
			for(File provinceFile : totalFile.listFiles()) {
				if (!totalFile.isDirectory()) {
					System.err.println("总路径错误，总路径是个文件");
					new Exception().printStackTrace();;
					System.exit(-1);
				}
				//遍历省路径中的每个文件
				for (File cityFile : provinceFile.listFiles()) {
					//遍历每一行数据
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(cityFile),"GBK"));
					String place=null;
					while ((place=bufferedReader.readLine())!= null) {
						document = new Document();
						document.add(new TextField("place", place, Store.YES));
						document.add(new TextField("city", cityFile.getName(), Store.YES));
						document.add(new TextField("province", Paths.get(cityFile.getParent()).getFileName().toString(), Store.YES));
						indexWriter.addDocument(document);
					}
				}
			}

			indexWriter.commit();
			indexWriter.close();
			directory.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
