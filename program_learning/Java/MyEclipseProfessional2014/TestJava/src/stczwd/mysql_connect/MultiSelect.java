package stczwd.mysql_connect;

import java.sql.ResultSet;

import stczwd.database.mysql.MysqlConnect;

public class MultiSelect {

	public static void main(String[] args) {
		MysqlConnect mysql = new MysqlConnect("115.29.147.223","stczwd");
		ResultSet itemSimilarityCheckresult=mysql.databasecheck("select * from Course where `课程名称`=`Linux`");
		System.out.println(itemSimilarityCheckresult);
		mysql.databaseclose();
	}

}
