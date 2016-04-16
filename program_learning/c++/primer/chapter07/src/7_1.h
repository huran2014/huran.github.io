/************************************************************************
	> File Name: 7_1.h
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 二  4/21 13:09:29 2015
  > Explain: 
 ************************************************************************/
#include<iostream>
#include<string>
using namespace std;

struct Sales_data {
	//定义成员函数
	string isbn() const { return this->Isbn;} //const可以指定this为常量指针
	Sales_data combine(const Sales_data& data)
	{
		this->num+=data.num;
		return *this;
	}
	string Isbn;
	int num;
	float price;
};

istream& read(istream &io,Sales_data &data)//返回引用可以直接返回原数据，不用拷贝
{
	return (io >> data.Isbn >> data.num >> data.price);
}

ostream& print(ostream &io,Sales_data &data)
{
	return (io << data.Isbn << " " << data.num << " " << data.price);
}
