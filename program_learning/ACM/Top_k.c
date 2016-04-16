/*************************************************************************
	> File Name: Top_k.c
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 三  6/17 17:26:15 2015
  > Explain: 
 ************************************************************************/
#include<stdio.h>

int n;  ///数字个数，n很大(n>10000)
int dui[10];
#define K 10    ///Top K,K的取值

void create_dui();　　///建堆
void UpToDown(int);　　///从上到下调整
int main()
{
	int i;
	int tmp;
	while(scanf("%d",&n)!=EOF)
	{
		for(i=1;i<=K;i++) ///先输入K个
		scanf("%d",&dui[i]);
		create_dui();  ///建小顶堆
		for(i=K+1;i<=n;i++)
		{
			scanf("%d",&tmp);
			if(tmp>dui[1])  ///只有大于根节点才处理
			{
				dui[1]=tmp;
				UpToDown(1);    ///向下调整堆
			}
		}
	}
	return 1;
}

void create_dui()
{
	int i;
	int pos=K/2;      ///从末尾数，第一个非叶节点的位置K/2
	for(i=pos;i>=1;i--)
		UpToDown(i);
}

void UpToDown(int i)
{
	int t1,t2,tmp,pos;
	t1=2*i; ///左孩子(存在的话)
	t2=t1+1;    ///右孩子(存在的话)
	if(t1>K)    ///无孩子节点
		return;
	else
	{
		if(t2>K)  ///只有左孩子
			pos=t1;
		else
			pos=dui[t1]>dui[t2]? t2:t1;

		if(dui[i]>dui[pos]) ///pos保存在子孩子中，数值较小者的位置
		{
			tmp=dui[i];dui[i]=dui[pos];dui[pos]=tmp;
			UpToDown(pos);
		}
	}
}
