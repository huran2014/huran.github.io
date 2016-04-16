# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

import re
import MySQLdb
import json
import codecs
import sys
reload(sys)
sys.setdefaultencoding('utf-8')


class WaPipeline(object):
    def __init__(self):
        self.file=codecs.open('wa_data_utf8.json','wb',encoding='utf-8')

    def process_item(self, item, spider):
	line = json.dumps(dict(item)) + '\n'
        #print line
        self.file.write(line.decode("unicode_escape"))		
	
	try:
		conn = MySQLdb.connect(
			host = '127.0.0.1',
			port = 3306,
			user = 'root',
			passwd = '12345',
			db = 'wa',  #read_default_file='/etc/mysql/my.cnf',
			charset='utf8'
                    )
		print "Connet to MySQL server successfully."
	except:
	    print "Could not connect to MySQL server."
	cur = conn.cursor()

#	#执行那个查询，这里用的是select语句
#    	cur.execute("select * from quizeEntity")
#    	#使用cur.rowcount获取结果集的条数
#    	numrows = int(cur.rowcount)
#	if(int i : numrows):
#		#cur.execute("truncate table quizeEntity")
#		cur.execute("delete from quizeEntity where id = " + i)

        #print '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
        #print 'item='+str(item)
        #print '~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~'
	quizeTitle="".join(item['quizeTitle'])
	quizeContent="".join(item['quizeContent'])
	quizePic = "".join(item['quizePic'])
	#if re.search("'",quizePic):
	#	quizePic = re.sub("'","\"",quizePic)
	print "quizePic="+quizePic
	print type(quizePic)

	#加判断，确定原数据库中是否有这一条，如果有，则更新，如果没有则进行添加
	query_check="select * from quizeEntity where `quizeTitle`=\"" + quizeTitle + "\""
	cur.execute(query_check)
	numrows = int(cur.rowcount)
	if numrows>=1:
	    sql="update quizeEntity set `quizeContent`=\"" + quizeContent + "\" where `quizeTitle`=\"" + quizeTitle + "\""
	    cur.execute(sql)
	    conn.commit()
	    sql1="update quizeEntity set `quizePic`=\"" + quizePic + "\" where `quizeTitle`=\"" + quizeTitle + "\""
	    cur.execute(sql1)
	    conn.commit()
	else:
	    sql = "insert into quizeEntity (quizeTitle,quizeContent,quizePic) values ('%s','%s','%s')"%(quizeTitle,quizeContent,quizePic)
	    #print 'sql='+sql
	    cur.execute(sql)
	    conn.commit()
	conn.close()
	return item
