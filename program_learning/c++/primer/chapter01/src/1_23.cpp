/*************************************************************************
	> File Name: 1_23.cpp
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 一  4/20 16:19:37 2015
  > Explain: 
 ************************************************************************/
#include<iostream>
#include<vector>
#include "Sales_item.h"
using namespace std;
const int sales_num=5;

int main()
{
	vector<Sales_item> sales_total;
	vector<int> sales_times;
	Sales_item sales[sales_num];
	cout << "Please input the sales of items:\n";
	for(int i=0;i<sales_num;i++)
	{
		cout << "item" << i << ": ";
		while(!(cin >> sales[i]))
		{
			cin.clear();
			while(cin.get()!='\n');
			cout << "Please input the sale of item" << i << " again:\n";
		}
	}

	for(int i=0;i<sales_num;i++)
	{
		int sales_add=1;
		for(int j=0;j<sales_total.size();j++)
		{
			if(sales[i].isbn()==sales_total[j].isbn())
			{
				sales_times[j]++;
				sales_add=0;
				break;
			}
		}
		if(sales_add==1)
		{
			sales_total.push_back(sales[i]);
			sales_times.push_back(1);
		}
	}
	cout << "The total of sales is:\n";
	for(int i=0;i<sales_total.size();i++)
	{
		cout << "The item of " << sales_total[i].isbn() << " sales " << sales_times[i] << " times.\n";
	}
	return 0;
}
