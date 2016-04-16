/*************************************************************************
	> File Name: 7_1.cpp
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 二  4/21 22:10:20 2015
  > Explain: 
 ************************************************************************/
#include<iostream>
#include "7_1.h"
using namespace std;

int main()
{
	Sales_data sales,sales_new;
	cout << "Please enter the item's Isbn num price:\n";
	if(read(cin,sales))
	{
		while(read(cin,sales_new))
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
		}
		print(cout,sales) << endl;
	}
	else
	{
		cerr << "No,data?!\n";
	}
	return 0;
}
