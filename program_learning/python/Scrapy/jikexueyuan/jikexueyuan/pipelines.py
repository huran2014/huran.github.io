# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

import json
import codecs
import MySQLdb
import sys
reload(sys)
sys.setdefaultencoding('utf-8')


class JikexueyuanPipeline(object):
    def __init__(self):
        self.file = codecs.open('/root/stczwd/Scrapy/jikexueyuan/jikexueyuan_data_utf8.json','w',encoding='utf-8')

    def process_item(self, item, spider):
	line = json.dumps(dict(item)) + '\n'
        self.file.write(line.decode("unicode_escape"))
        #return item

	try:
		conn = MySQLdb.connect(
			host = '127.0.0.1',
			port = 3306,
			user = 'root',
			passwd = '12345',
			db = 'stczwd',  #read_default_file='/etc/mysql/my.cnf',
			charset='utf8'
                    )
		print "Connet to MySQL server successfully."
	except:
	    print "Could not connect to MySQL server."
	cur = conn.cursor()
	#movieid=int(cur.execute("select max(id) from movienews"))
	#if(movieid>110):
	#cur.execute("truncate table movienews")
	
#	#执行那个查询，这里用的是select语句
#    	cur.execute("select * from movienews")
#    	#使用cur.rowcount获取结果集的条数
#    	numrows = int(cur.rowcount)
#	if(numrows>=100):
#		cur.execute("truncate table movienews")

	#print '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
	#print 'item='+str(item)
	#print '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
	category=''.join(item['category'])
	title=''.join(item['title'])
	link=''.join(item['link'])
	add_time=''.join(item['add_time'])
	sql = "insert into jikexueyuan (category,title,link,add_time) values ('%s','%s','%s','%s')"%(category,title,link,add_time)
	#sql = "insert into movienews (title,url,image,src,pdate,content) values ('%s','%s','%s','%s','%s','%s')"%(item['title'],item['url'],item['image'],item['src'],item['pdate'],item['content'])
	#print 'sql='+sql
	cur.execute(sql)
	conn.commit()
	conn.close()
	return item
