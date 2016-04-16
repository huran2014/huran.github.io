#!/usr/bin/python
# -*- coding:utf-8 -*-

# from scrapy.contrib.spiders import  CrawlSpider,Rule

from scrapy.spider import Spider
from scrapy.http import Request
from scrapy.selector import Selector
from films.items import FilmsItem
#import time

class filmsSpider(Spider):
	"""爬虫FilmsSpider"""
	name = "films"
	#减慢爬取速度 为1s
	#download_delay = 1

	allowed_domains = ["imdb.com"]
	start_urls = [
	
		#第一篇文章地址
		"http://www.imdb.com/search/title?at=0&sort=moviemeter,asc&title_type=feature"
	]
	
	def parse(self, response):
		sel = Selector(response)
		sites = sel.xpath('//*[@id="main"]/table/tr')
		#sites = sel.xpath('//*[@id="main"]/table/tbody/tr[2]')
		#print sites
		items = []

		for site in sites:
			#获得文章url和标题
			item = FilmsItem()
			
			title = site.xpath('td[@class="title"]/a/text()').extract()
			year = site.xpath('td[@class="title"]/span[@class="year_type"]/text()').extract()
			summary = site.xpath('td[@class="title"]/span[@class="outline"]/text()').extract()
			#directors and actors
			lists = site.xpath('td[@class="title"]/span[@class="credit"]/text()').extract()
			#print lists
			people = site.xpath('td[@class="title"]/span[@class="credit"]/a/text()').extract()
			#num = classify(lists,people)
			
			#movietype
			movietype = site.xpath('td[@class="title"]/span[@class="genre"]/a/text()').extract()
			playtime = site.xpath('td[@class="title"]/span[@class="runtime"]/text()').extract()
	
			item['title'] = [t.encode('utf-8') for t in title]
			item['year'] = [y.encode('utf-8') for y in year]
			item['summary'] = [s.encode('utf-8') for s in summary]
			item['movietype'] = [m.encode('utf-8') for m in movietype]
			item['playtime'] = [p.encode('utf-8') for p in playtime]
			p_list = [l.encode('utf-8') for l in lists]
                	person = [p.encode('utf-8') for p in people]
			num = self.classify(p_list, person)
			item['director'] = person[0:num]
			item['actors'] = person[num:]
			
			items.append(item)
			yield item
#		return items
#		yield items		

		#获得下一篇文章的url
		urls = sel.xpath('//*[@id="right"]/span/a/@href').extract()
		for url in urls:
			print url
			url = "http://www.imdb.com" + url
			print url
			yield Request(url, callback=self.parse)

	def classify(self, p_list, person):
		directors = []
		actors = []
		#p_list = [l.encode('utf-8') for l in lists]
		#person = [p.encode('utf-8') for p in people]
#		print type(p_list)
#		print p_list
#		print person
#		print len(person)
#		print person[0:1]
		directors = person[0:1]
		i = 0
		for p in p_list[1:]:
			i += 1
			if p==', ':
				directors.extend(person[i:i+1])
			else:
				actors = person[i:]
				break
		return i
#		print directors
#		print actors
