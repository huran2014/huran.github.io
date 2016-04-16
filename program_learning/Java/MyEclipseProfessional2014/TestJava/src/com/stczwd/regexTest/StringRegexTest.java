package com.stczwd.regexTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRegexTest {

	public static void main(String[] args) {
		segmentregexCompany("阳光物流(979172205)");
	}

	public static void segmentregexCompany(String segment) {
		//提取公司名称
		Pattern pattern = Pattern.compile("(.*)\\(");
		Matcher matcher = pattern.matcher(segment);
		//提取公司的qq号
		Pattern patternQQ = Pattern.compile("\\([0-9]{6,}");
		Matcher matcherQQ = patternQQ.matcher(segment);
		if (matcher.find() && matcherQQ.find()) {
			String companyName = matcher.group(0).substring(0, matcher.group(0).length()-1);
			String companyQQ = matcherQQ.group(0).substring(1, matcherQQ.group(0).length());
			System.out.println(companyName);
		}
		else {
			System.out.println("No matcher!");
		}
//		return new String[]{};
	}
}
