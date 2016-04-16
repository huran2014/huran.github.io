package com.stczwd.HANLPTest;

import com.hankcs.hanlp.tokenizer.SpeedTokenizer;

public class HANLPSpeedTokenTest {

	public static void main(String[] args) {
		/**
		 * 演示极速分词，基于AhoCorasickDoubleArrayTrie实现的词典分词，适用于“高吞吐量”“精度一般”的场合
		 * @author hankcs
		 */
		//String text = "江西鄱阳湖干枯，中国最大淡水湖变成大草原";
		String text = "浙江绍兴到德州临邑60挖机广东龙川到茂名50铲上海到宜宾150挖机上海到库尔勒240+斗上海到湘西吉卫镇200+锤赣州兴国到莆田2台翻斗";
		System.out.println(SpeedTokenizer.segment(text));
		long start = System.currentTimeMillis();
		int pressure = 1000000;
		for (int i = 0; i < pressure; ++i)
		{
			SpeedTokenizer.segment(text);
		}
		double costTime = (System.currentTimeMillis() - start) / (double)1000;
		System.out.printf("分词速度：%.2f字每秒", text.length() * pressure / costTime);

	}

}
