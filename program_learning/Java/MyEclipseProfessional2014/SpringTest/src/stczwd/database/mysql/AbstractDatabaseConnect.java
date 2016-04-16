package stczwd.database.mysql;

import java.sql.ResultSet;

/**
 * @author minelab
 * 定义公共的数据库连接接口
 * 用来定义通用的数据库连接方法
 */
public interface AbstractDatabaseConnect {

	/**
	 * 数据库连接接口方法
	 */
	public void databaseconnect();
	
	/**
	 * 数据表的创建、插入、更新、删除
	 * @param operation 数据库操作语句，查询除外
	 */
	public void databaseoperation(String operation) ;
		
	/**
	 * 数据库的查询方法
	 * @param checkoperation 数据库的查询语句
	 * @return 返回查询的结果
	 */
	public ResultSet databasecheck(String checkoperation);
	
	/**
	 * 数据库的关闭
	 */
	public void databaseclose();
}
