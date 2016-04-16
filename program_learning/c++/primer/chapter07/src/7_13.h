/************************************************************************
	> File Name: 7_13.h
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 二  4/21 13:09:29 2015
  > Explain: 
 ************************************************************************/
#include<iostream>
#include<string>
using namespace std;

struct Sales_data {
	//定义构造函数
	//Sales_data()=default;//默认初始化
	Sales_data(istream &in_input);//输入初始化
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

//Sales_data::Sales_data(istream& input)//外置的构造函数没有返回值
//{
//	cout << "Please enter the Isbn,number,price:\n";
//	read(input,*this);
//}

istream& read(istream &io,Sales_data &data)//返回引用可以直接返回原数据，不用拷贝
{
	
	return (io >> data.Isbn >> data.num >> data.price);
}

Sales_data::Sales_data(istream& input)//外置的构造函数没有返回值
{
//    cout << "Please enter the Isbn,number,price:\n";
	read(input,*this);
}   

ostream& print(ostream &io,Sales_data &data)
{
	return (io << data.Isbn << " " << data.num << " " << data.price);
}
