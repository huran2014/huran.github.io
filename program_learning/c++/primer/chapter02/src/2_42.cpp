/*************************************************************************
	> File Name: 1_23.cpp
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 一  4/20 16:19:37 2015
  > Explain: 
 ************************************************************************/
#include<iostream>
#include<vector>
#include "2_42.h"
using namespace std;
int sales_times=0;

int main()
{
	vector<Sales_item> sales_total;
	vector<Sales_item> sales;
	Sales_item sales_new;
	cout << "Please input the sales of items:\n";
	cout << "sales_times" << sales_times << ": ";
	while(cin >> sales_new.isbn >> sales_new.num >> sales_new.price)
	{
		sales.push_back(sales_new);
		cout << "sales_times" << ++sales_times << ": ";
	}

	for(int i=0;i<sales_times;i++)
	{
		int sales_add=1;
		for(int j=0;j<sales_total.size();j++)
		{
			if(sales[i].isbn==sales_total[j].isbn)
			{
				sales_total[j].num+=sales[i].num;
				sales_add=0;
				break;
			}
		}
		if(sales_add==1)
		{
			sales_total.push_back(sales[i]);
		}
	}
	cout << "The total of sales is:\n";
	for(int i=0;i<sales_total.size();i++)
	{
		cout << "The item of " << sales_total[i].isbn << " sales " << sales_total[i].num << " of it, and it has totally gain $" << sales_total[i].num*sales_total[i].price << endl;
	}
	return 0;
}
