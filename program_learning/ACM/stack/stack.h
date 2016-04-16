/*************************************************************************
	> File Name: stack.h
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 日 11/15 10:40:38 2015
  > Explain: 
 ************************************************************************/
#include <iostream>
using namespace std;


/**
  *首先创建单链表结构体Node
  *单链表仅含有一个后继指针
  */
typedef struct Node
{
	int data;
	struct Node *next;
} Node;
//定义单链表指针
typedef struct Node *LinkList;

class Stack
{
private:
	LinkList *list;

public:
	/**
	  *单链表的初始化创建,创建指定长度的链表
	  *输入：单链表指针和链表长度
	  *输出：0--成功，-1--失败
	  */
	int InitList(int num);

	/**
	  *单链表的初始化创建,建立一个空的链表
	  *输入：链表长度
	  *输出：0--成功，-1--失败
	  */
	int InitList();

	/**
	  *单链表的初始化创建,建立一个空的链表
	  *输入：单链表指针
	  *输出：0--成功，-1--失败
	  */
	int InitList(LinkList *l);

	/**
	  *检测链表是否为空
	  *输入：
	  *输出：0--成功，-1--失败
	  */
	int ListEmpty();

	/**
	  *获取单链表的长度
	  *输入：
	  *输出：返回单链表内元素的个数
	  */
	int ListLength();

	/**
	  *单链表清空
	  *输入：
	  *输出：0--成功，-1--失败
	  */
	int ClearList();

	/**
	  *获取单链表内第几个位置的元素
	  *输入：位置n，Node元素指针
	  *输出：0--成功，-1--失败
	  */
	int GetElem(int n, Node *node);

	/**
	  *查找单链表内是否存在某元素
	  *输入：Node元素指针
	  *输出：如果存在某元素，则返回位置，如果不存在该元素，则返回-1
	  */
	int GetElem(Node *node);

	/**
	  *在单链表内某个确定位置插入元素
	  *输入：位置n，Node元素指针
	  *输出：0--成功，-1--失败
	  */
	int GetElem(int n, Node *node);

	/**
	  *在单链表内删除某个元素
	  *输入：Node元素指针
	  *输出：0--成功，-1--失败
	  */
	int GetElem(Node *node);

	/**
	  *在单链表内删除某个确定位置的元素
	  *输入：位置n，Node元素指针
	  *输出：0--成功，-1--失败
	  */
	int GetElem(int n, Node *node);

	/**
	  *将两个单链表进行合并
	  *输入：单链表指针
	  *输出：0--成功，-1--失败
	  */
	int ListCombine(LinkList *l);
}