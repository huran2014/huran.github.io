/**
 * 这个类用来进行数据预处理
 * 将文件夹内的所有文件进行读取，并以qq发送信息日期为分隔获取每个公司发送的信息
 */
package com.stczwd.HANLPTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stczwd.database.mysql.MysqlConnect;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.seg.common.Term;

public class HANLPStandardTest {
	private static String rootPath = "C:/Users/minelab/Desktop/大件物流群-宝妹货运.txt";
	private static File file = new File(Paths.get(rootPath).getParent()+"/词典调试文档.txt");
	private static BufferedWriter writer = null;
	private static int num = 0;
	/**
	 * 初始化构造方法，用来接收Path路径
	 * @param path
	 */
	public HANLPStandardTest(String path) {
		this.rootPath = path;
	}

	public static void main(String[] args) {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			fileCheck(rootPath);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 遍历文件夹，如果有文件夹则继续遍历，如果是文件则文本分析
	 * @param rootPath 文档分析的主目录
	 */
	public static void fileCheck(String rootPath) {
		File rootDirectory = new File(rootPath);
		if (!rootDirectory.isDirectory()) {
			fileRead(rootPath);
		}
		else{
			for (File file : rootDirectory.listFiles()) {
				int dotSplit = file.getName().lastIndexOf(".");
				String fileTypeName = file.getName().substring(dotSplit+1, file.getName().length());
				if (fileTypeName.equals("txt")) {
					fileRead(file.getAbsolutePath());
				}
			}
		}
	}

	/**
	 * 文件读取方法，通过阅读每一行的数据，辨析公司名称和qq号。
	 * 然后通过每条qq信息间均有回车换行的内容来实现内容区分
	 * @param filePath 读取文件路径
	 */
	public static void fileRead(String filePath) {
		File file = new File(filePath);
		if (file.isDirectory()) {
			System.err.println("This file is a Directory:"+file.getName());
			return;
		}
		try {
			System.out.println("当前文档是:\t"+file.getName());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String lineString = null;
			while ((lineString = bufferedReader.readLine())!=null) {
				String[] company = segmentregexCompany(lineString);
				if (company.length>=2) {
					String companyName = company[0];
					String companyQQ = company[1];
					//					System.out.println("公司名称是:\t"+companyName+"公司QQ是:\t"+companyQQ);
					//					writer.write("公司名称是:"+companyName+"\t公司QQ是:"+companyQQ+"\r\n");
					HashMap<String, String> map = new HashMap<>();
					map.put("companyName", companyName);
					map.put("companyQQ", companyQQ);
					MysqlConnect mysqlConnect = new MysqlConnect("123.57.223.22", "stczwd");
					HashMap<String, String> mapSelect = new HashMap<String,String>();
					mapSelect.put("CompanyQQ", companyQQ);
					if (!mysqlConnect.databaseSelectBoolean("CompanyItem", mapSelect)) {
						mysqlConnect.databaseInsert("CompanyItem", map);
					}
					mysqlConnect.databaseclose();
					while ((lineString = bufferedReader.readLine()).length()>2) {
						segmentHANLP(lineString,companyName,companyQQ);
					}
				}
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分析公司发布的每一行信息，获取始发地到目的地货物明确格式的信息
	 * @param segment 该公司发布的每一条信息
	 * @throws IOException 
	 */
	public static void segmentHANLP(String segment,String companyName,String companyQQ) throws IOException {
		Boolean segmentFlag = false;
		segment = changeregex(segment, "—{1,}", "一");
		segment = changeregex(segment, "一{1,}", "-");
		segment = changeregex(segment, "-{1,}", "=");
		segment = changeregex(segment, "={1,}", "到");
		segment = changeregex(segment, " ", "");
		segment = changeregex(segment, "[\\，\\；\\.\\【\\】\\：\\:\\。]", "");
		List<Term> termList = HanLP.segment(segment);
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < termList.size(); i++) {
			Term term = termList.get(i);
			//			writer.write(term.word+"\t"+term.nature+"\r\n");
			//略过非重要属性成员
			if (term.nature != Nature.ns && term.nature != Nature.ng && 
					term.nature != Nature.v && term.nature != Nature.w && term.nature != Nature.m) {
				continue;
			}
			//略过非“到”的动词
			if (term.nature == Nature.v && !term.word.equals("到")) {
				continue;
			}
			if (i+1 < termList.size()) {
				Term termNext = termList.get(i+1);
				//只截取每个地址中的最后一个地址，尽量将范围缩小
				if(term.nature != Nature.ns || termNext.nature != Nature.ns)
					stringBuilder.append(term.word);
				//读取到ng属性的值时，说明运送货物的说明即将开始，要从这里开始计算，直到读取到地址信息位置
				if (term.nature == Nature.ng ) {
					segmentFlag = true;
				}
				if (segmentFlag == true && termNext.nature == Nature.ns) {
					//					System.out.println(stringBuilder.toString());
					MysqlStoreMessage(stringBuilder.toString(),companyName,companyQQ);
					//					writer.write(stringBuilder.toString()+"\r\n");
					System.out.println((++num)+"\r\n");
					stringBuilder.setLength(0);
					segmentFlag = false;
				}
			}
			else {
				stringBuilder.append(term.word);
			}
		}
		//将最后所有全部打印出来
		if (stringBuilder.length()>0) {
			//			writer.write(stringBuilder.toString()+"\r\n");
			MysqlStoreMessage(stringBuilder.toString(),companyName,companyQQ);
			System.out.println((++num)+"\r\n");
			stringBuilder.setLength(0);
			segmentFlag = false;
		}
		stringBuilder.setLength(0);
	}

	/**
	 * 分析每个公司的名称和qq号信息
	 * @param segment 该公司发布消息时的前缀信息
	 * @return 返回该公司的名称和qq号
	 */
	public static String[] segmentregexCompany(String segment) {
		//判断是否有日期
		Pattern pattern = Pattern.compile("[0-9]{4}-[0-1][0-9]-[0-3][0-9] [0-9]{1,2}:[0-5][0-9]:[0-5][0-9]");  
		Matcher matcher = pattern.matcher(segment);  
		if (matcher.find()) {
			//剔除日期内容
			segment = changeregex(segment, "[0-9]{4}-[0-1][0-9]-[0-3][0-9] [0-9]{1,2}:[0-5][0-9]:[0-5][0-9]", "");
			//			System.out.println(segment);
			//提取公司名称
			pattern = Pattern.compile("(.*)\\(");
			matcher = pattern.matcher(segment);
			//提取公司的qq号
			pattern = Pattern.compile("\\([0-9]{6,}");
			Matcher matcherQQ = pattern.matcher(segment);
			if (matcher.find() && matcherQQ.find()) {
				String companyName = matcher.group(0).substring(0, matcher.group(0).length()-1);
				String companyQQ = matcherQQ.group(0).substring(1, matcherQQ.group(0).length());
				if (companyName.length()>2) {
					return new String[]{companyName,companyQQ};
				}
			}
		}
		return new String[]{};
	}


	/**
	 * 正则替换方法，用来进行正则替换
	 * @param regexString 需要正则替换的字符串
	 * @param regex 正则表达式
	 * @param replaceString 要替换的内容
	 * @return 替换后的字符串
	 */
	public static String changeregex(String regexString, String regex, String replaceString) {
		Pattern pattern = Pattern.compile(regex);  
		Matcher matcher = pattern.matcher(regexString);  
		return matcher.replaceAll(replaceString);
	}

	/**
	 * 数据库存储信息方法。将摘出的信息进行再次拆分，获取每一段的信息，然后将这些信息存放到数据库中
	 * @param message 需要拆分和存放到数据库中的内容
	 */
	public static void MysqlStoreMessage(String message, String companyName, String companyQQ) {
		List<Term> termList = HanLP.segment(message);
		if(termList.size()<=4) return;
		String FromPath = termList.get(0).nature==Nature.ns? termList.get(0).word:null;
		String destination = termList.get(2).nature==Nature.ns? termList.get(2).word:null;
		//删除错误信息
		if (FromPath==null || destination==null) return;
		StringBuilder stringBuilder= new StringBuilder();
		//获取全部的货物信息
		for (int i=3;i<termList.size();i++) 
			stringBuilder.append(termList.get(i).word);
		MysqlConnect mysql = new MysqlConnect("123.57.223.22","stczwd");
		//获取company所属的id
		ResultSet resultSet = mysql.databasecheck("select id From CompanyItem where `CompanyQQ`=\""+companyQQ+"\"");
		try {
			resultSet.next();
			String companyID = resultSet.getString(1);
			String sqlCheckString = "select * from Logistics where `id`=\""+companyID+"\" and `FromPath`=\""+FromPath+"\" and `Destination`=\""+destination+"\" and `Cargo`=\""+stringBuilder.toString()+"\"";
			System.out.println(sqlCheckString);
			if (!mysql.dataExitCheck(sqlCheckString)) {
				String sqlOperation = "insert into Logistics(id,FromPath,Destination,Cargo) values(\""+companyID+"\",\""+FromPath+"\",\""+destination+"\",\""+stringBuilder.toString()+"\")";
				mysql.databaseoperation(sqlOperation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mysql.databaseclose();
	}

	/**
	 * 检查字符串编码方法
	 * @param str 待检查的字符串
	 * @return 返回字符串编码格式
	 */
	public  static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s = encode;
				return s;
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) {
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return "";
	}

	/**
	 * 字符串编码转换的实现方法
	 * @param str  待转换编码的字符串
	 * @param oldCharset 原编码
	 * @param newCharset 目标编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String changeCharset(String str, String oldCharset, String newCharset)
			throws UnsupportedEncodingException {
		if (str != null) {
			//用旧的字符编码解码字符串。解码可能会出现异常。
			byte[] bs = str.getBytes(oldCharset);
			//用新的字符编码生成字符串
			return new String(bs, newCharset);
		}
		return null;
	}
}


