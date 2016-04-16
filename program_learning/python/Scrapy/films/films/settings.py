# -*- coding: utf-8 -*-

# Scrapy settings for films project
#
# For simplicity, this file contains only the most important settings by
# default. All the other settings are documented here:
#
#     http://doc.scrapy.org/en/latest/topics/settings.html
#

BOT_NAME = 'films'

SPIDER_MODULES = ['films.spiders']
NEWSPIDER_MODULE = 'films.spiders'

# Crawl responsibly by identifying yourself (and your website) on the user-agent
#USER_AGENT = 'films (+http://www.yourdomain.com)'

#禁止coockies，防止被ban
COOKIES_ENABLED = False

ITEM_PIPELINES = {
        'films.pipelines.FilmsPipeline':300
}

