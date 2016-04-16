/*
 * USER_ID: test#stczwd
 * PROBLEM: 83
 * SUBMISSION_TIME: 2015-05-25 00:59:44
 * */
#include <iostream>
 
int main()
{
	int *p1=new int;
	int *p2=new int;
	std::cin >> *p1 >> *p2;
	std::cout << *p1+*p2 << std::endl;
	delete(p1);
	delete(p2);
	return 0;
}
