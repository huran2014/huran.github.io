package stczwd.mysql_connect;

import java.sql.ResultSet;
import java.sql.SQLException;

import stczwd.database.mysql.MysqlConnect;

public class MysqlTest {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		MysqlConnect mysql = new MysqlConnect("123.57.223.22","stczwd");
//		String sqloperation="insert into friends(name,age) values(\"Hawords\",22)";
//		mysql.databaseoperation(sqloperation); 
		ResultSet itemSimilarityCheckresult=mysql.databasecheck("select * from ItemSimilarity where `ItemID1`=104 and `ItemID2`=107");
//		if(itemSimilarityCheckresult.previous())
//			System.out.println(itemSimilarityCheckresult.getInt("age"));
		System.out.println(itemSimilarityCheckresult);
		mysql.databaseclose();
	}

}
