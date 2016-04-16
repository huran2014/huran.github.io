/*************************************************************************
	> File Name: 3_21.cpp
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 二  4/21 16:21:26 2015
  > Explain: 
 ************************************************************************/
#include<iostream>
#include<vector>
using namespace std;

int main()
{
	vector<int> number;
	int num=0;
	int num_new=0;
	cout << "Please enter a serial of numbers:\n";
	cout << "num0: ";
	while(cin >> num_new)
	{
		number.push_back(num_new);
		cout << "num" << ++num << ": "; 
	}

	auto num_begin=number.begin();
	auto num_end=number.end()-1;
	auto num_mid=number.begin()+number.size()/2;
	cout << "\nThe resualt is:\n";
	for(int i=0;num_begin!=num_mid+1;num_begin++,num_end--,i++)
	{
		if(num_begin!=num_end)
		{
			cout << "num" << i << "+num" << num-i << ": ";
			cout << (*num_begin)+(*num_end) << endl;
		}
		else
		{
			cout << "num" << i << ": ";
			cout << (*num_begin) << endl;
		}
	}
	return 0;
}
