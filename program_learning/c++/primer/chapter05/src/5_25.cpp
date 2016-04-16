/*************************************************************************
	> File Name: 5_25.cpp
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 二  4/21 17:47:27 2015
  > Explain: 
 ************************************************************************/
#include<iostream>
#include<vector>
#include<stdexcept>
#include<exception>
using namespace std;

void trans(int a,int b)
{
	if(b==0) throw "The second number can't be 0.";
	cout << "a/b=" << a/b << endl;
}

int main()
{
	int a,b;
	cout << "Please enter two numbers: ";
	while(cin >> a >> b)
	{
		try{
			//定义异常
			trans(a,b);
		}
		//catch(exception &ex){
		catch(const char* s){
			//处理异常
			cout << s << endl;
			//cout << ex.what() << endl;
			cout<< "Try agin? Please enter y or n: ";
			char c;
			cin >> c;
			if(c=='n')
			{
				return 0;
			}
			cout << "Please enter two numbers: ";
			continue;
		}
		cout << "Please enter two numbers: ";
	}
	cout << "You succeed!\n";
	return 0;
}
