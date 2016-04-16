package stczwd.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;

/**
 * mysql连接的实际操作类
 * @author minelab
 */
public class MysqlConnect extends AbstractSqlConnect{
	
	private Connection dbconnection;//创建jdbc驱动信息

	/**
	 * 构造函数，在使用默认账号密码的时候使用，端口采用默认3306端口
	 * @param ip 数据库的ip地址
	 * @param database 所要连接的数据库
	 */
	public MysqlConnect(String ip, String database) {
		this(ip, 3306, database);
	}

	/**
	 * 构造函数，在使用默认账号密码的时候使用
	 * @param ip 数据库的ip地址
	 * @param database 所要连接的数据库
	 */
	public MysqlConnect(String ip, int port, String database) {
		this(ip, port, "root", "12345", database);
	}
	
	/**
	 * 构造函数，在接收数据库信息的同时，创建数据库连接
	 * @param ip 数据库的ip地址
	 * @param port 数据库的端口
	 * @param user 数据库的管理用户名
	 * @param passwd 数据库的管理用户名对应的密码
	 * @param database 所要连接的数据库
	 */
	public MysqlConnect(String ip, int port, String user, String passwd, String database) {
		super(ip, port, user, passwd, database);
		databaseconnect();
	}
	
	/**
	 * 数据库连接方法，用来在构造对象时建立与数据库的链接
	 */
	public void databaseconnect() {
		String jdbcUrl= "jdbc:mysql://"+super.getIp()+":"+super.getPort()+"/"+super.getDatabase()+"?"
                + "user="+super.getUser()+"&password="+super.getPasswd()
                //确保长连接正常运作，确保mysql连接数限制，保证mysql连接正常
                +"&autoReconnect=true&failOverReadOnly=false&maxReconnects=10"
                //设置通道字符编码是utf-8模式
				+"&useUnicode=true&characterEncoding=utf-8";
		try {
			Class.forName("com.mysql.jdbc.Driver");//动态加载mysql驱动
			dbconnection = DriverManager.getConnection(jdbcUrl);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数据表的插入
	 * @param table 插入的表名
	 * @param map 所要插入信息的column和value的对应关系
	 */
	public void databaseInsert(String table,Map<String,?> map) {
		try {
			// Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
			Statement stmt = dbconnection.createStatement();
			//创建stringBuilder对象，用来叠加完善插入部分数据
			StringBuilder columnBuilder = new StringBuilder();
			StringBuilder valueBuilder = new StringBuilder();
			//根据传入的Map完善sql的插入语句
			for (Entry<String, ?> entry : map.entrySet()) {
				//构造()内部的column部分
				columnBuilder.append(entry.getKey()+",");
				valueBuilder.append("\""+entry.getValue()+"\",");
			}
			//删除多余的，
			columnBuilder.deleteCharAt(columnBuilder.length()-1);
			valueBuilder.deleteCharAt(valueBuilder.length()-1);
			//整合插入语句
			String operation = "insert into "+table+"("+columnBuilder.toString()+") values("+valueBuilder.toString()+")";
	        //根据传入的mysql命令，实现mysql的操作
	        stmt.executeUpdate(operation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数据表的查找
	 * @param table 查找的表名
	 * @param map 所要查找信息的column和value的对应关系
	 */
	public Boolean databaseSelectBoolean(String table,Map<String,?> map) {
		try {
			// Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
			Statement stmt = dbconnection.createStatement();
			//创建stringBuilder对象，用来叠加完善插入部分数据
			StringBuilder selectBuilder = new StringBuilder();
			//根据传入的Map完善sql的插入语句
			for (Entry<String, ?> entry : map.entrySet()) {
				//构造()内部的column部分
				selectBuilder.append("`"+entry.getKey()+"`=\""+entry.getValue()+"\" and");
			}
			//删除多余的，
			selectBuilder.delete(selectBuilder.length()-4, selectBuilder.length());
			//整合插入语句
			String operation = "select * from "+table+" where "+selectBuilder.toString();
			System.err.println(operation);
	        //根据传入的mysql命令，实现mysql的操作
	        ResultSet result = stmt.executeQuery(operation);
	        if (result.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return false;
	}

	/**
	 * 数据表的查找
	 * @param table 查找的表名
	 * @param map 所要查找信息的column和value的对应关系
	 */
	public ResultSet databaseSelectResultSet(String table,Map<String,?> map) {
		try {
			// Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
			Statement stmt = dbconnection.createStatement();
			//创建stringBuilder对象，用来叠加完善插入部分数据
			StringBuilder selectBuilder = new StringBuilder();
			//根据传入的Map完善sql的插入语句
			for (Entry<String, ?> entry : map.entrySet()) {
				//构造()内部的column部分
				selectBuilder.append("`"+entry.getKey()+"`=\""+entry.getValue()+"\" and");
			}
			//删除多余的，
			selectBuilder.delete(selectBuilder.length()-4, selectBuilder.length());
			//整合插入语句
			String operation = "select * from "+table+" where "+selectBuilder.toString();
			System.err.println(operation);
	        //根据传入的mysql命令，实现mysql的操作
	        return stmt.executeQuery(operation);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}


	/**
	 * 数据表的创建、插入、更新、删除
	 * @param operation 数据库操作语句，查询除外
	 */
	public void databaseoperation(String operation) {
		try {
			// Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
			Statement stmt = dbconnection.createStatement();
	        //根据传入的mysql命令，实现mysql的操作
	        int result = stmt.executeUpdate(operation);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * 数据库的查询方法
	 * @param checkoperation 数据库的查询语句
	 * @return 返回查询的结果
	 */
	public ResultSet databasecheck(String checkoperation) {
		ResultSet rs=null;
		try {
			// Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
			Statement stmt = dbconnection.createStatement();
	        
	        //根据传入的mysql命令，实现mysql的操作
	        rs = stmt.executeQuery(checkoperation);
	        //while(rs.next())
	        //{
	        //	System.out.println(rs.getString(1) + "\t" + rs.getString(2));
	        //}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 数据库的查询方法，只需查看有无内容
	 * @param checkoperation 数据库的查询语句
	 * @return 返回查询的结果
	 */
	public Boolean dataExitCheck(String checkoperation) {
		ResultSet rs=null;
		try {
			// Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
			Statement stmt = dbconnection.createStatement();
	        
	        //根据传入的mysql命令，实现mysql的操作
	        rs = stmt.executeQuery(checkoperation);
	        if(rs.next()) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 数据库的关闭
	 */
	public void databaseclose() {
		try {
			dbconnection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
