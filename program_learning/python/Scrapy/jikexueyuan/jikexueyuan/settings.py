# -*- coding: utf-8 -*-

# Scrapy settings for jikexueyuan project
#
# For simplicity, this file contains only the most important settings by
# default. All the other settings are documented here:
#
#     http://doc.scrapy.org/en/latest/topics/settings.html
#

BOT_NAME = 'jikexueyuan'

SPIDER_MODULES = ['jikexueyuan.spiders']
NEWSPIDER_MODULE = 'jikexueyuan.spiders'

COOKIES_ENABLED=True
# Crawl responsibly by identifying yourself (and your website) on the user-agent
#USER_AGENT = 'jikexueyuan (+http://www.yourdomain.com)'
ITEM_PIPELINES = {
	'jikexueyuan.pipelines.JikexueyuanPipeline':300
}
