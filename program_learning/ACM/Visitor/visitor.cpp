/*************************************************************************
	> File Name: visitor.cpp
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 四  6/18 21:43:50 2015
  > Explain: 
 ************************************************************************/
#include <iostream>
#include <list> 
#include <string> 

using namespace std;

class CPerson;
class CStudent;
class CTeacher;

class CVisitor;
class CPrinter;

//元素（被访问者）
class CPerson //纯虚类 
{
protected: 
	string name; //或者改成数组
	int gender; 

	CPerson()
	{
	}

public:
	virtual void Accept( CVisitor& ) //= 0;//纯虚函数//不妨碍类的其他方法
	{
	}
public:
	void SetName(const string& Name)
	{
		name = Name;
	}

	string GetName() const//类的方法属性，不允许修改类元素
	{		
		return name;
	}

	void SetGender(const int& Gender)
	{
		gender = Gender;
	}

	int GetGender() const
	{
		return gender;
	}

};

//访问者
class CVisitor 
{
public:
	virtual void Visit( CStudent& ) = 0;
	virtual void Visit( CTeacher& ) = 0;
};

//具体元素
class CStudent: public CPerson 
{ 
private: 
	int grade; //年级
public: 
	CStudent(string Name,int Gender,int Grade)
	{ 
		name=Name; 
		gender=Gender; 
		grade=Grade; 
	} 
public: 
	virtual void Accept(CVisitor& printer) //虚函数
	{
		printer.Visit(*this);
	}

	void SetGrade(int Grade)
	{
		grade=Grade;
	}

	int GetGrade() const
	{
		return grade;
	}
}; 

//具体元素
class CTeacher:public CPerson 
{ 
private:
	int service_time;//工龄
public: 
	CTeacher(string Name,int Grade, int ServiceTime) 
	{ 
		name = Name; 
		gender = Grade; 
		service_time = ServiceTime;
	} 

public: 

	virtual void Accept(CVisitor& printer)
	{
		printer.Visit(*this);
	}

	void SetServiceTime(int ServiceTime)
	{
		service_time = ServiceTime;
	}

	int GetServiceTime() const
	{
		return service_time;
	}
};

//具体访问者
//这里，这个类打印具体元素的信息（就是显示在屏幕上），可根据需要打印不同的内容。
//具体访问者是一种策略，可根据不同需要创建新的具体访问者，而无需修改具体元素（即被访问者）。
class CPrinter: public CVisitor
{ 
public: 		
	void Visit(CStudent& s)
	{
		cout <<"student:" <<endl; 
		cout <<"/t Name:" <<s.GetName() <<endl; 
		if(s.GetGender()==0) 
			cout <<"/t Gender:" <<"female" <<endl; 
		else 
			cout <<"/t Gender:" <<"male" <<endl; 
		cout <<"/t Grade:" <<s.GetGrade() <<endl; 
	}

	void Visit(CTeacher& t)
	{
		cout <<"Teacher:" <<endl; 
		cout <<"/t Name:" <<t.GetName() <<endl; 
		if(t.GetGender()==0) 
			cout <<"/t Gender:" <<"female" <<endl; 
		else 
			cout <<"/t Gender:" <<"male" <<endl; 
		cout <<"/t Service Time:" <<t.GetServiceTime()<<endl; 
	}
};

//对象结构
//这里，Organization是个组织（如植树节组成一个团队，去植树），有若干老师，若干学生。
class Organization 
{
private:
	typedef list<CPerson*> CMemberList;
	CMemberList member_list; 

public: 

	void Add(CPerson* person) //增加人员。只是示例，没有考虑人员重复等情况。
	{
	//注意：person指向的空间必须是由new操作符申请的空间

		member_list.push_back(person);
	}

	//只是示例，所以删除成员等操作略去
	
	void PrintMembers(CPrinter& printer) //输出成员名单
	{
		CMemberList::iterator itr = member_list.begin();
		for(; itr != member_list.end(); ++itr) 
		{  
			(*itr)->Accept(printer);
		}
	}

	~Organization()
	{
		//删除申请的空间；c++比较麻烦
		CMemberList::iterator itr = member_list.begin();
		for(; itr != member_list.end(); ++itr) 
		{  
			delete *itr;
		}
	}
};

int main() 
{ 
	Organization Planting;//植树组织
	Planting.Add( new CTeacher("Johnny",1,10) );
	Planting.Add( new CStudent("Catherine",0,1) );
	Planting.Add( new CStudent("peter",1,2) );

	CPrinter printer;//访问者，也可看成一种策略
	Planting.PrintMembers(printer);

	return 0; 
}