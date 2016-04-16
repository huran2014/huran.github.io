# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

import MySQLdb
import time
import sys
reload(sys)
sys.setdefaultencoding('utf-8')

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

#执行那个查询，这里用的是select语句
cur.execute("select * from quizeEntity")
#使用cur.rowcount获取结果集的条数
numrows = int(cur.rowcount)
if numrows >= 49:
	#如果数目比49大的话需要清空数据库
	#首先删除外键表
	print numrows
	#cur.execute("alter table answerEntity drop foreign key quize_id")
	cur.execute("drop table answerEntity")
	#清空表的数据
	cur.execute("truncate table quizeEntity")
	#清除完后再添加外键表
	#cur.execute("alter table answerEntity add CONSTRAINT quize_id_refs_id_d76ec39d FOREIGN KEY (quize_id) REFERENCES quizeEntity (id)")
	cur.execute("""CREATE TABLE answerEntity (
  id int(11) NOT NULL AUTO_INCREMENT,
  quize_id int(11) NOT NULL,
  answerContent text NOT NULL,
  answerPopty int(11) DEFAULT NULL,
  updateTime datetime NOT NULL,
  PRIMARY KEY (id),
  KEY answerEntity_037260ce (quize_id),
  CONSTRAINT quize_id_refs_id_d76ec39d FOREIGN KEY (quize_id) REFERENCES quizeEntity (id)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8""")
#	#sql = "delete from quizeEntity where id = %d" % (i)
#	#cur.execute(sql)
#	cur.execute("delete from quizeEntity where id = " + str(i))
#	print i
#cur.execute("delete from quizeEntity where id = 114")
#cur.execute("delete from quizeEntity where id = 357")
#cur.execute("delete from quizeEntity where id = 358")
conn.commit()
conn.close()
