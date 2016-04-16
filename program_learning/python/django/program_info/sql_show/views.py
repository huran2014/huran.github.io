# -*- coding=UTF-8 -*-
from django.shortcuts import render
import sys
reload(sys)
sys.setdefaultencoding('utf8')
#import codes
import MySQLdb
import json
import codecs
from django.http import HttpResponse
import chardet

# Create your views here.
def show_info(request):
#    names = person.objects.all()
    conn = MySQLdb.connect(
            host = 'localhost',
            user = 'root',
            passwd = '12345',
            db = 'mystudy',
	    charset = 'utf8',
            )
    print 'Connect to MySQL server successfully.'
    
    cur = conn.cursor()
    cur.execute('SELECT * from program_info')
    names = cur.fetchall()
    program_info=[]
    for name in names:
	print chardet.detect(str(name))
	print  str(name).decode('ascii').encode('utf-8')
	program_info.append(tuple_json(name))
    conn.close()
    print chardet.detect(str(program_info))
    return HttpResponse(str(program_info))

def tuple_json(name):
    info_json={}
    info_json["programName"]=name[0]
    info_json["programDate"]=name[1]
    info_json["programTime"]=name[2]
    info_json["programSummary"]=name[3]
    info_json["actor"]=name[4]
    info_json["channelNum"]=name[5]
    info_json["channelName"]=name[6]
    info_json["imgUrl"]=name[7]
    info_json["setsNum"]=name[8]
    info_json["programClass"]=name[9]
    return info_json
