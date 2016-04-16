#!/usr/bin/python
# -*- coding:utf-8 -*-

import time
import re
from scrapy.spider import Spider
from scrapy.http import Request
from scrapy.selector import Selector
from scrapy import log

from wa.items import WaItem

class WaSpider(Spider):
	#log.start("log",loglevel='INFO')
	name="wa"
	allowed_domains = ["http://tieba.baidu.com/"]
	start_urls = [
		"http://tieba.baidu.com/f?kw=%E6%98%8E%E6%98%9F%E5%90%8C%E6%AC%BE&ie=utf-8&pn=0"
	]
	global pn_num
	pn_num=0#每50进入下一个页面
	#items=[]
	
	def parse(self, response):
		
		sel=Selector(response)
		sites = sel.xpath('//*[@id="thread_list"]/li')
		global pn_num
		items = []

		for site in sites:
			#time.sleep(1)

			item = WaItem()
			content = []
			
			title = site.xpath('div/div[2]/div/div[1]/a/text()').extract()
			quize = site.xpath('div/div[2]/div[2]/div/div/text()').extract()
			title_flag=self.regular_elimination(str(title))
			quize_flag=self.regular_elimination(str(quize))
				
			item['quizeTitle'] = [t.encode('utf-8') for t in title]
			item['quizeContent'] = [q.encode('utf-8') for q in quize]			

			pictures = []	
			#提取图片内容
			image_as = site.xpath('div/div[2]/div[2]/div/div[2]/div/div[1]/ul/li')
			#image_as = sel.xpath('//*[@id="fm3803497868"]/li[1]/a/img/@data-original').extract()

			for image_a in image_as:
				image_url = image_a.xpath('a/img/@data-original').extract()
				image_url1 = [i.encode('utf-8') for i in image_url]
				pictures.extend(image_url1)
			#item['quizePic']=str(image_as)
			item['quizePic']=str(pictures)

			#delete the empty question
			title_str = str(title)
			quize_str = str(quize)
			#if(self.regular_elimination(quize_str)==0):
			#	if(self.regular_elimination(title_str)==0):
			#		print "self.regular_elimination(quize_str)=" + str(self.regular_elimination(quize_str))
			#		print "self.regular_elimination(title_str)=" + str(self.regular_elimination(title_str))
			if title_flag == 0 and quize_flag == 0:
					items.append(item)

		#yield items	
		return items
		
		#获取下一页数据
		#link_news = sel.xpath('//*[@id="frs_list_pager"]/a[10]/@href').extract()
		#for link_new in link_news:
			#link_new.lstrip('amp;')
			#print link_new
			#link_new='http://tieba.baidu.com/'+link_new
			

	#定义一个正则匹配函数，能够将语句中的想要剔除的内容删除。
	def regular_elimination(self, content):
		if len(content) <= 5:
			return 1
		elif re.search("q",content):
			if re.search("qq",content):
				return 1
			elif re.search("[0-9]{6,}",content):
				return 1
			elif re.search("粉q",content):
				return 1
			else:
				return 0
		elif re.search("百度翻译",content):
			print "文字里面含有百度翻译"
			return 1
		else:
			return 0
