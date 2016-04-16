# -*- coding: utf-8 -*-

# Scrapy settings for wa project
#
# For simplicity, this file contains only the most important settings by
# default. All the other settings are documented here:
#
#     http://doc.scrapy.org/en/latest/topics/settings.html
#

BOT_NAME = 'wa'

SPIDER_MODULES = ['wa.spiders']
NEWSPIDER_MODULE = 'wa.spiders'
ITEM_PIPELINES = {
	'wa.pipelines.WaPipeline':300
}

#禁止coockies，防止被ban
COOKIES_ENABLED = False

# Crawl responsibly by identifying yourself (and your website) on the user-agent
#USER_AGENT = 'wa (+http://www.yourdomain.com)'
