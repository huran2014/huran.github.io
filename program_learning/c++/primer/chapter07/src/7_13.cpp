/*************************************************************************
	> File Name: 7_13.cpp

	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 二  4/21 22:10:20 2015
  > Explain: 
 ************************************************************************/
#include<iostream>
#include "7_13.h"
using namespace std;

int main()
{
//	Sales_data sales(cin),sales_new(cin);
	cout << "Please enter the item's Isbn num price:\n";
//	if(read(cin,sales))
	Sales_data sales(cin);
	cout<<"cin end;"<<endl;
	if(cin)
	{
//		while(read(cin,sales_new))
		Sales_data sales_new(cin);
		while(cin)
		{
			if(sales.isbn()==sales_new.isbn())
			{
				sales.combine(sales_new);
			}
			else
			{
				print(cout,sales) << endl;
				sales=sales_new;
			}
			read(cin,sales_new);
		}
		print(cout,sales) << endl;
	}
	else
	{
		cerr << "No,data?!\n";
	}
	return 0;
}
