/*************************************************************************
	> File Name: SingleNumber.cpp
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 三  5/27 09:37:22 2015
  > Explain: 
 ************************************************************************/
#include <iostream>
#include <vector>

int main()
{
	using std::cin;
	using std::cout;
	using std::endl;
	using std::vector;
	int* num=new int;
	int* test=new int;
	*test=0;
	vector< vector<long long> > number(*test,vector<int>number1);
	vector< vector<bool> > flags;
	while(cin >> *num)
	{
		for(int i=0;i<*num;i++)
		{
			std::cin >> number[*test][i];
			flags[*test][i]=0;
		}
		int* flag_num=new int;
		*flag_num=0;
		for(int i=0;i<*num;i++)
		{
			if(flags[*test][i]==0)
			{
				*flag_num=1;
				for(int j=i+1;j<*num;j++)
				{
					if(number[*test][i]==number[*test][j])
					{
						flags[*test][j]=1;
						flags[*test][i]=1;
						++*flag_num;
					}
					if(*flag_num>=3)
						break;
				}
			}
		}
		delete(flag_num);
		++*test;
	}
	cin.clear();
	while(cin.get()!='\n');
	for(int i=0;i<*test;i++)
	{
		for(int j=0;j<*num;j++)
		{
			if(flags[i][j]==0)
			{
				cout << number[i][j] << endl;
				break;
			}
		}
	}
	delete(num);
	return 0;
}