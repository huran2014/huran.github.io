package com.stczwd.HANLPTest;

import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;

public class HANLPNShortTest {

	public static void main(String[] args) {
		Segment nShortSegment = new NShortSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
		Segment shortestSegment = new DijkstraSegment().enableCustomDictionary(false).enablePlaceRecognize(true).enableOrganizationRecognize(true);
		String[] testCase = new String[]{
		        "今天，刘志军案的关键人物,山西女商人丁书苗在市二中院出庭受审。",
		        "刘喜杰石国祥会见吴亚琴先进事迹报告团成员",
		        "浙江绍兴到德州临邑60挖机广东龙川到茂名50铲上海到宜宾150挖机上海到库尔勒240+斗上海到湘西吉卫镇200+锤赣州兴国到莆田2台翻斗",
		        };
		for (String sentence : testCase)
		{
		    System.out.println("N-最短分词：" + nShortSegment.seg(sentence) + "\n最短路分词：" + shortestSegment.seg(sentence));
		}
	}

}
