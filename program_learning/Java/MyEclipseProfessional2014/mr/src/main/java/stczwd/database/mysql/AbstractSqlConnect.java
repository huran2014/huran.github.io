package stczwd.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接的抽象对象，用来存储数据库信息和优化数据库方法
 * @author minelab
 */
public abstract class AbstractSqlConnect implements AbstractDatabaseConnect{
	
	private String ip;
	private int port;
	private String user;
	private String passwd;
	private String database;
	
	/**
	 * 构造函数，在接收数据库信息的同时，创建数据库连接
	 * @param ip 数据库的ip地址
	 * @param port 数据库的端口
	 * @param user 数据库的管理用户名
	 * @param passwd 数据库的管理用户名对应的密码
	 * @param database 所要连接的数据库
	 */
	public AbstractSqlConnect(String ip, int port, String user, String passwd, String database) {
//		super();
		//对输入的json数据进行解析
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.passwd = passwd;
		this.database = database;
	}

	/**
	 * 
	 * @return 返回数据库所在的ip地址
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 
	 * @return 返回数据库所在的端口
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 
	 * @return 返回数据库管理账户
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @return 返回数据库管理账户的登录密码
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * 
	 * @return 返回要操作的数据库的名称
	 */
	public String getDatabase() {
		return database;
	}
}
