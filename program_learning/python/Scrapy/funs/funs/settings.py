# -*- coding: utf-8 -*-

# Scrapy settings for funs project
#
# For simplicity, this file contains only the most important settings by
# default. All the other settings are documented here:
#
#     http://doc.scrapy.org/en/latest/topics/settings.html
#

BOT_NAME = 'funs'

SPIDER_MODULES = ['funs.spiders']
NEWSPIDER_MODULE = 'funs.spiders'
ITEM_PIPELINES = ['funs.pipelines.FunsPipeline']

#禁止coockies，防止被ban
COOKIES_ENABLED = False
# Crawl responsibly by identifying yourself (and your website) on the user-agent
#USER_AGENT = 'funs (+http://www.yourdomain.com)'
