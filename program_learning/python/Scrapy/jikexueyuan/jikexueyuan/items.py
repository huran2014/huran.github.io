# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

#import scrapy
from scrapy.item import Item,Field

class JikexueyuanItem(Item):
	# define the fields for your item here like:
	# name = scrapy.Field()
	category = Field()
	title = Field()
	link = Field()
	add_time = Field()
	pass

#class LessonItem(Item):
#	title = Field()
#	link = Field()
#	pass
