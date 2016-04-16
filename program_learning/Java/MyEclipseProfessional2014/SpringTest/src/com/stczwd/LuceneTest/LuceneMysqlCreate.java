package com.stczwd.LuceneTest;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import stczwd.database.mysql.MysqlConnect;

public class LuceneMysqlCreate {

	public static void main(String[] args) {

		//创建Mysql连接
		MysqlConnect mysqlConnect = new MysqlConnect("115.29.151.149", "mystudy");
		ResultSet resultSet = mysqlConnect.databasecheck("select * from program_new");

		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		try {
			Directory directory = FSDirectory.open(Paths.get("lucene/mysql"));
			IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
			Document document = null;
			while (resultSet.next()) {
				document = new Document();
				document.add(new TextField("频道名称", resultSet.getString("频道名称"), Store.YES));
				document.add(new TextField("节目名称", resultSet.getString("节目名称"), Store.YES));
				document.add(new TextField("主持人", resultSet.getString("主持人"), Store.YES));
				indexWriter.addDocument(document);
			}
			indexWriter.commit();
			indexWriter.close();
			directory.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
