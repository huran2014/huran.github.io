/*************************************************************************
	> File Name: stack.cpp
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 日 11/15 10:40:45 2015
  > Explain: 
 ************************************************************************/
#include <iostream>
#include "stack.h"
using namespace std;

/**
  *单链表的初始化创建,创建指定长度的链表
  *输入：单链表指针和链表长度
  *输出：0--成功，-1--失败
  */
int Stack::InitList(int num)
{
	Node p;//定义结点
	if(num<=0)//判断需求长度是否太小
	{
		cout << "The Length of LinkList is to small!\n";
		return -1;
	}
	//创建一个只有头结点的空链表
	this->InitList();
	//srand为逝去的事件数，以秒数计，服务于rand
	srand(time(0));
	for (int i = 0; i < num; ++i)
	{
		*p = (Node)malloc(sizeof(Node))
	}
}

/**
  *单链表的初始化创建,建立一个空的链表
  *输入：链表长度
  *输出：0--成功，-1--失败
  */
int Stack::InitList()
{
	//首先设置头结点
	*(this->list)=(LinkList)malloc(sizeof(Node));
	//将头结点的下一跳设置为Null
	this->list->next=NULL;
}

/**
  *单链表的初始化创建,建立一个空的链表
  *输入：单链表指针
  *输出：0--成功，-1--失败
  */
int Stack::InitList(LinkList *l)
{
	if (!ListEmpty(l))
	{
		return -1;
		cout << "This is a empty LinkList";
	}
	this->list=l;
	return 0;
}

/**
  *检测链表是否为空
  *输入：
  *输出：0--成功，-1--失败
  */
int Stack::ListEmpty();

/**
  *获取单链表的长度
  *输入：
  *输出：返回单链表内元素的个数
  */
int Stack::ListLength();

/**
  *单链表清空
  *输入：
  *输出：0--成功，-1--失败
  */
int Stack::ClearList();

/**
  *获取单链表内第几个位置的元素
  *输入：位置n，Node元素指针
  *输出：0--成功，-1--失败
  */
int Stack::GetElem(int n, Node *node);

/**
  *查找单链表内是否存在某元素
  *输入：Node元素指针
  *输出：如果存在某元素，则返回位置，如果不存在该元素，则返回-1
  */
int Stack::GetElem(Node *node);

/**
  *在单链表内某个确定位置插入元素
  *输入：位置n，Node元素指针
  *输出：0--成功，-1--失败
  */
int Stack::GetElem(int n, Node *node);

/**
  *在单链表内删除某个元素
  *输入：Node元素指针
  *输出：0--成功，-1--失败
  */
int Stack::GetElem(Node *node);

/**
  *在单链表内删除某个确定位置的元素
  *输入：位置n，Node元素指针
  *输出：0--成功，-1--失败
  */
int Stack::GetElem(int n, Node *node);

/**
  *将两个单链表进行合并
  *输入：单链表指针
  *输出：0--成功，-1--失败
  */
int Stack::ListCombine(LinkList *l);
