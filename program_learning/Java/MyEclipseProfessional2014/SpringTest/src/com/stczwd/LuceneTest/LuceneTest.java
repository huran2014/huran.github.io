package com.stczwd.LuceneTest;

import java.io.File;  
import java.io.FileReader;  
import java.io.IOException;  
import java.nio.file.Paths;  
  
import org.apache.lucene.analysis.Analyzer;  
import org.apache.lucene.analysis.standard.StandardAnalyzer;  
import org.apache.lucene.document.Document;  
import org.apache.lucene.document.Field;  
import org.apache.lucene.document.LongField;  
import org.apache.lucene.document.StringField;  
import org.apache.lucene.document.TextField;  
import org.apache.lucene.index.IndexWriter;  
import org.apache.lucene.index.IndexWriterConfig;  
import org.apache.lucene.store.Directory;  
import org.apache.lucene.store.FSDirectory;  
import org.apache.lucene.store.RAMDirectory;  
  
/** 
 * @author: IT学习者 
 * @官网 www.itxxz.com 
 * @version: 2015年5月28日 下午8:54:48 
 */  
public class LuceneTest {  
  
    /** 
     * @author: IT学习者 
     * @官网 www.itxxz.com 
     * @version: 2015年5月28日 下午8:54:48 
     */  
    public static void main(String[] args) {  
        createIndex();  
    }  
  
    public static void createIndex() {  
  
        IndexWriter writer = null;  
  
        try {  
            // Directory directory = new RAMDirectory();  
            Directory directory = FSDirectory  
                    .open(Paths.get("lucene/library"));   
            Analyzer analyzer = new StandardAnalyzer();  
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);  
  
            writer = new IndexWriter(directory, iwc);  
            Document document = null;  
            File f = new File("lucene/data");  
            for (File file : f.listFiles()) {  
                System.out.println("filename:" + file.getName());  
                document = new Document();  
                document.add(new LongField("modified", f.lastModified(),  
                        Field.Store.NO));  
                document.add(new TextField("contents", new FileReader(file)));  
                document.add(new StringField("path", file.toString(),  
                        Field.Store.YES));  
                writer.addDocument(document);  
            }  
            writer.close();
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } finally {  
            if (writer != null) {  
                try {  
                    writer.close();  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
        }  
  
    }  
  
}