package com.stczwd.regexTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class vmwareRegex {

	public static void main(String[] args) {
		String segment = "输入：一段文本。这段文本可能包含多个浮点数。要求把文本中的浮点数都找到。浮点数可能是3.14159这样的，也可能是-0.234或者+23.98这样的，整数，比如234和187不认为是浮点数。不合规的表达，比如++456，或者0.0.234不认为是浮点数。输出：找到的全部浮点数。每行输出一个数。";
		List<String> resultList = floatRegex(segment);
		Iterator<String> iterator = resultList.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

	}

	public static List<String> floatRegex(String segment) {
		List<String> resultList = new ArrayList<>();
		Pattern pattern = Pattern.compile("[^\\.][\\+\\-]?[0-9]+\\.[0-9]+[^\\.]");
		Matcher matcher = pattern.matcher(segment);
		while(matcher.find()) {
			resultList.add(matcher.group().replaceAll("[^\\.\\+\\-\\d]", ""));
		}
		return resultList;
	}
}
