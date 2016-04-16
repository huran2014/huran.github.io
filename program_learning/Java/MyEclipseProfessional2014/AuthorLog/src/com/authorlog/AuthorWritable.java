/**
 * 这是自定义的Writable类型，咱们用来分析系统日志
 * Month：月
 * Day：日
 * login：登陆次数
 */
package com.authorlog;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class AuthorWritable implements WritableComparable<Object> {
	
	//自定义AuthorWritable内包含的数据
	private String month;//登陆的月份
	private String day;//登陆的日子
	//private int login;//当天内登陆的次数
	private String login;
	
	public String getMonth() {
		return month;
	}

	public String getDay() {
		return day;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setDay(String day) {
		this.day = day;
	}

//	public int getLogin() {
//		return login;
//	}
//
//	public void setLogin(int login) {
//		this.login = login;
//	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public AuthorWritable() {
		super();
		// TODO Auto-generated constructor stub
	}

	//构造函数
	public AuthorWritable(String month, String day, int login) {
		super();
		this.month = month;
		this.day = day;
		this.login = String.valueOf(login);
//		this.login = login;
	}

	//序列化方法
	@Override
	public void write(DataOutput out) throws IOException {
		// TODO Auto-generated method stub
		out.writeBytes(this.month);
		out.writeBytes(this.day);
//		out.writeInt(this.login);
		out.writeBytes(this.login);
	}

	//反序列化方法
	public void readFields(DataInput in) throws IOException {
		// TODO Auto-generated method stub
		this.month = in.readLine();
		this.day = in.readLine();
//		this.login = in.readInt();
		this.login = in.readLine();
	}

	//toString方法，用来最终数据显示
	public String toString()
	{
		return month + "\t" +day + "\t" + login;
	}

	//设置排序方法
	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		AuthorWritable other_auth = (AuthorWritable)o;//强制类型转换
//		if (Integer.parseInt(this.getDay()) > Integer.parseInt(other_auth.getDay())) return 1;
//		if (Integer.parseInt(this.getDay()) < Integer.parseInt(other_auth.getDay())) return -1;
		int author_times = Integer.parseInt(this.getMonth().substring(3, 5));
		int other_times = Integer.parseInt(other_auth.getMonth().substring(3, 5));
		if (author_times > other_times) return 1;
		if (author_times < other_times) return -1;
		return 0;
	}
}
