package stczwd.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		// TODO Auto-generated constructor stub
	}

	/**
	 * 构造函数，在使用默认账号密码的时候使用
	 * @param ip 数据库的ip地址
	 * @param database 所要连接的数据库
	 */
	public MysqlConnect(String ip, int port, String database) {
		this(ip, port, "root", "12345", database);
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		String jdbcUrl= "jdbc:mysql://"+super.getIp()+":"+super.getPort()+"/"+super.getDatabase()+"?"
                + "user="+super.getUser()+"&password="+super.getPasswd()+"&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
                //+"&autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
		try {
			Class.forName("com.mysql.jdbc.Driver");//动态加载mysql驱动
			dbconnection = DriverManager.getConnection(jdbcUrl);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
//	        if(result != -1)
//	        	System.out.println("operation is complated!");
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
//	        while(rs.next())
//	        {
//	        	System.out.println(rs.getString(1) + "\t" + rs.getString(2));
//	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 数据库的关闭
	 */
	public void databaseclose() {
		try {
			dbconnection.close();
//			System.out.println("mysql is closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
