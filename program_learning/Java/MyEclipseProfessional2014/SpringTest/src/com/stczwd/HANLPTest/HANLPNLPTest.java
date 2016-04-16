package com.stczwd.HANLPTest;

import java.util.List;

import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.NLPTokenizer;

public class HANLPNLPTest {

	public static void main(String[] args) {
		List<Term> termList = NLPTokenizer.segment("中国科学院计算技术研究所的宗成庆教授正在教授自然语言处理课程");
		System.out.println(termList);
		List<Term> termList1 = NLPTokenizer.segment("浙江绍兴到德州临邑60挖机广东龙川到茂名50铲上海到宜宾150挖机上海到库尔勒240+斗上海到湘西吉卫镇200+锤赣州兴国到莆田2台翻斗");
		System.out.println(termList1);
	}

}
