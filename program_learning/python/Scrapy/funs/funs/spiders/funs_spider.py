#!/usr/bin/python
# -*- coding:utf-8 -*-

from scrapy.spider import Spider
from scrapy.http import Request
from scrapy.selector import Selector
from scrapy import log

from funs.items import FunsItem

class FunsSpider(Spider):
	#log.start("log",loglevel='INFO')
	name="funs"
	allowed_domains = ["http://ent.sina.com.cn/"]
	start_urls = [
		"http://ent.sina.com.cn/",
	]
	
	def parse_start(self , response):
		for i in range(0,5):
			start_url = start_urls[i]
			yield Request(start_url,callback=self.parse)

	def parse(self, response):
		
		self=Selector(response)
		sites = self.xpath('//div[@id="listZone"]/div[@class="nrC"]')
		items = []
		
		for site in sites:
			item = MovienewsItem()
			
			url = site.xpath('a/@href').extract()
			image = site.xpath('a/img[@class="nrPic"]/@src').extract()
			title = site.xpath('a/img[@class="nrPic"]/@alt').extract()
			src = site.xpath('p/text()').extract()
			pdate = site.xpath('p/span[@class="date"]/text()').extract()
			content = site.xpath('div[@class="nrP"]/text()').extract()
			
			item['title'] = [t.encode('utf-8') for t in title]
			url_p = str(url)[4:25]
			url_home = "http://ent.qq.com/"
			url_home += url_p
			url=[]
			url.append(url_home)
			item['url'] = [u.encode('utf-8') for u in url]
	                item['src'] = [s.encode('utf-8') for s in src]
	                item['pdate'] = [p.encode('utf-8') for p in pdate]
	                item['image'] = [i.encode('utf-8') for i in image]
	                item['content'] = [c.encode('utf-8') for c in content]
			items.append(item)
			#return item			

			yield item
			
			#url_next = "http://trace.qq.com/collect?pj=8888&url=http%3A//ent.qq.com/movie/news_index.shtml&w=1280&x=410&y=4675&v=1&u=951184213"
			#Request(url_next)

		#return items
