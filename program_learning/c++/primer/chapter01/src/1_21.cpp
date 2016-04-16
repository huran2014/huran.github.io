/*************************************************************************
	> File Name: 1_21.cpp
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 一  4/20 15:32:05 2015
  > Explain: 
 ************************************************************************/
#include<iostream>
#include "Sales_item.h"
using namespace std;

int main()
{
	Sales_item sales01,sales02,sales03;
	cout << "Please input two sales :\n";
	cin >> sales01 >> sales02;
	sales03=sales01+sales02;
	cout << "The total sales are: " << sales03 << endl;
	return 0;
}
