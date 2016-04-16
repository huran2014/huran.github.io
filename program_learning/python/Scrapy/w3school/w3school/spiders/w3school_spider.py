#coding:utf-8

#继承spider类
from scrapy.spider import Spider
from scrapy.selector import HtmlXPathSelector
from scrapy import log
#import scrapy

#import w3school
from w3school.items import W3schoolItem

class W3schoolSpider(Spider):
	"""爬取w3school标签"""
	#log.start("log",loglevel='INFO')
	name = "w3school"
	allowed_domains = ["w3school.com.cn"]
	start_urls = [
			"http://www.w3school.com.cn/xml/xml_syntax.asp"
		]

	#对scrapy.Spider类的override
	def parse(self, response):

		sel = Selector(response)
		sites = sel.xpath('//div[@id="navsecond"]/div[@id="course"]/ul[1]/li')
		items = []

		for site in sites:
			item = W3schoolItem()
			
			response = HtmlXPathSelector(response)
			title = response.select('a/text()').extract()
			link = response.select('a/@href').extract()
			desc = response.select('a/@title').extract()

			item['title'] = [t.encode('utf-8') for t in title]
			item['link'] = [l.encode('utf-8') for l in link]
			item['desc'] = [d.encode('utf-8') for d in desc]
			response.append(item)

			#信息记录函数
			log.msg("Appending item...",level='INFO')


		log.msg("Append done.",level='INFO')
		return items

