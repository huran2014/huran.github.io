package com.stczwd.LuceneTest;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RegexpQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneMysqlQuery {
	private static String queryString = "海淀";

	/**  
	 * @param args
	 * @Author:lulei  
	 * @Description:  
	 */
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub  
		Directory directory = null;
		try {
			//索引硬盘存储路径
//			directory = FSDirectory.open(new File("D://index/test"));
			directory = FSDirectory.open(Paths.get("lucene/File"));
			//读取索引
			DirectoryReader dReader = DirectoryReader.open(directory);
			//创建索引检索对象
			IndexSearcher searcher = new IndexSearcher(dReader);
			//分词技术
//			Analyzer analyzer = new IKAnalyzer();
			Analyzer analyzer = new StandardAnalyzer();
			//创建Query
			
			//单条件单field查询
			QueryParser parser = new QueryParser("place", analyzer);
			Query query = parser.parse(queryString);
			
			//单条件多field查询
			MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(new String[]{"频道名称","节目名称","主持人"}, analyzer);
			Query multiFieldQuery = multiFieldQueryParser.parse(queryString);
			
			//多条件查询
			QueryParser parser2 = new QueryParser("节目名称", analyzer);
			Query query2 = parser2.parse(queryString);
			QueryParser parser3 = new QueryParser("主持人", analyzer);
			Query query3 = parser3.parse(queryString);
			
			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(query2,Occur.SHOULD);
			booleanQuery.add(query3,Occur.SHOULD);

			//单条件单field过滤查询
			QueryParser filterParser = new QueryParser("频道名称", analyzer);
			Query filterQuery = filterParser.parse(queryString);
			
			//正则查询
			String regex = "CCTV";
			Term term = new Term("主持人",regex);
			RegexpQuery regexpQuery = new RegexpQuery(term);
			
			//检索索引，获取符合条件的前10条记录
			TopDocs topDocs = searcher.search(query, 10);
			if (topDocs != null) {
				System.out.println("符合条件的文档总数为：" + topDocs.totalHits);
				//循环输出符合条件的文档
				for (int i = 0; i < topDocs.scoreDocs.length; i++) {
					//topDocs.scoreDocs[i].doc 为文档在索引中的id
					Document doc = searcher.doc(topDocs.scoreDocs[i].doc);
					System.out.println("place = " + doc.get("place"));
					System.out.println("city = " + doc.get("city"));
					System.out.println("province = " + doc.get("province"));
				}
			}
			//关闭资源
			directory.close();
			dReader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}

	}

}
