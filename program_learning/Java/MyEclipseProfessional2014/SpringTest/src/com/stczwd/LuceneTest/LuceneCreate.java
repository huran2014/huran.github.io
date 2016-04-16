package com.stczwd.LuceneTest;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import javassist.bytecode.analysis.Analyzer;

public class LuceneCreate {

	public static void main(String[] args) {

		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		try {
			Directory directory = FSDirectory.open(Paths.get("lucene/test"));
			IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
			Document document1 = new Document();
			document1.add(new StringField("id", "stc", Store.YES));
			document1.add(new TextField("content", "极客学院", Store.YES));
			document1.add(new IntField("num", 1, Store.YES));
			indexWriter.addDocument(document1);
			
			Document document2 = new Document();
			document2.add(new StringField("id", "zwd", Store.YES));
			document2.add(new TextField("content", "lucene学习", Store.YES));
			document2.add(new IntField("num", 2, Store.YES));
			indexWriter.addDocument(document2);
			
			indexWriter.commit();
			indexWriter.close();
			directory.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
