/*************************************************************************
	> File Name: 1_22.cpp
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 一  4/20 15:59:21 2015
  > Explain: 
 ************************************************************************/
#include<iostream>
#include "Sales_item.h"
using namespace std;
const int item_num=5;

int main()
{
	Sales_item sales[item_num];
	Sales_item sales_total;
	cout << "Please input the sales of items:\n";
	for(int i=0;i<item_num;i++)
	{
		cout << "item" << i << ": ";
		while(!(cin >> sales[i]))
		{
			cin.clear();
			while(cin.get()!='\n');
			cout << "Please input the sales of item" << i << " again:\n";
		}
		sales_total+=sales[i];
	}
	cout << "The total of sales is: " << sales_total << endl;
	return 0;
}


