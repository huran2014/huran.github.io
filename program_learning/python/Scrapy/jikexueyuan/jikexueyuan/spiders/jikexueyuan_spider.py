#/usr/bin/python
# -*- coding:utf-8 -*-

from scrapy.spider import Spider
from scrapy.selector import Selector
from scrapy import log
import datetime

from jikexueyuan.items import JikexueyuanItem


class JikexueyuanSpider(Spider):
	"""爬取jikexueyuan标签"""
	name = "jikexueyuan"
	allowed_domains = ["jikexueyuan.com"]
	start_urls = [
		"http://www.jikexueyuan.com/course/html5/"
	]
	
	def parse(self, response):
	
		sel = Selector(text=response.body)
		sites = sel.xpath('//*[@id="aside"]/div/div[2]/ul/li')
		items = []
		print "sites="+str(sites)
		
		for site in sites:
			#item = JikexueyuanItem()
			
			category = site.xpath('dl/dt/text()').extract()
	
			#item['category'] = [c.encode('utf-8') for c in category]
			#items.append(item)
			
			#进一步爬取该目录下的文件
			site_divs = site.xpath('dl/dd/span')
			
			#lessons=''
			for site_div in site_divs:
				item = JikexueyuanItem()
				title = site_div.xpath('a/text()').extract()
				link = site_div.xpath('a/@href').extract()
				#获取当前的时间
				now = datetime.datetime.now()
				otherStyleTime = now.strftime("%Y-%m-%d %H:%M:%S")

				item['category'] = [c.encode('utf-8') for c in category]
				item['title'] = [t.encode('utf-8') for t in title]
				item['link'] = [l.encode('utf-8') for l in link]
				item['add_time'] = str(otherStyleTime)
				items.append(item)
			
#				print '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
#                        	print type(item_lessons)
#				print item_lessons
#                        	print '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
#			 item['lesson']=str(item_lessons)
#                        print '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
#                        print type(item['lesson'])
#                        print '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
#			#item['lesson'] = [i for i in item_lessons]	
#			items.append(item)			
						

			#recode
			log.msg("Appending item...",level='INFO')
		
		
		log.msg("Append done.",level='INFO')
		return items
	
