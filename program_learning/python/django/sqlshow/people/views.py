from django.shortcuts import render
from django.http import HttpResponse
from people.models import Person
from django.shortcuts import HttpResponseRedirect,Http404,HttpResponse,render_to_response
  
# Create your views here.
def person(request):
#    names=Person.objects.all()
    names = Person.objects.all().values()
#    return render_to_response('index.html',locals())
    return HttpResponse({names})
