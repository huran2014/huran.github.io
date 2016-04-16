/*************************************************************************
	> File Name: 7_4.h
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 一  4/27 09:39:23 2015
 ************************************************************************/
#include<iostream>
#include<string>
using namespace std;

struct person{
	string name;
	string address;
	string phone;
	int family_num;

	//设定类成员函数
	string who() const { return this->name;}
	string where() const {return this->address;}
	string how() const { return this->phone;}
	int how_many() const { return this->family_num;}
};

istream& read(istream &in_put,person &people)
{
	in_put >> people.name >> people.address >> people.phone >> people.family_num;
	return in_put;
}

ostream& print(ostream& out_put,person &people)
{
	out_put << people.name << " " << people.address << " " << people.phone << " " << people.family_num <<endl;
	return out_put;
}

