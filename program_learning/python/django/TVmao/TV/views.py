#encoding=utf-8
from django.shortcuts import render
import sys
reload(sys)
sys.setdefaultencoding('utf8')
#import codes
import MySQLdb
import codecs
from django.http import HttpResponse

# Create your views here.
def TV_show(request):
#    names = person.objects.all()
    conn = MySQLdb.connect(
            host = 'localhost',
            user = 'root',
            passwd = '12345',
            db = 'stczwd',
	    charset = 'utf8',
            )
    print 'Connect to MySQL server successfully.'

    cur = conn.cursor()
#    cur.execute('SELECT name form person')
    cur.execute('SELECT * from test')
#    names = [row[0] for row in cur.fetchall()]
    names = cur.fetchall()
#    for name in names:
    print names[0][1]
    #name = str(names).decode('utf-8').encode('utf-8')
    #print names.decode('utf-8')
   # name = str(names)
    conn.close()
#    return render(response,{names})
    return HttpResponse({names})
