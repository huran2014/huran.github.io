/*************************************************************************
	> File Name: pthread_sem.c
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 四  5/14 17:37:20 2015
  > Explain: 
 ************************************************************************/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>//sleep的库函数
#include <pthread.h>//线程库函数
#include <semaphore.h>//信号量控制的库函数

#define Max_td 5
char buffer[100];

void* td_function(void *num);//创建线程函数
sem_t sem;//创建信号量对象

int main()
{
	int td;//创建标识符
	pthread_t td_thread[Max_td];//创建线程对象
	void *td_result;//创建线程函数返回对象
	int i=0;//创建循环标志位
	int* num=&i;

	//信号量创建
	//创建sem对象，并声明它是该进程的局部信号量，不会影响其他进程。
	//并设置初始值为0
	td=sem_init(&sem,0,0);
	if(td!=0)
	{
		perror("sem init is failed!\n");
		return 1;
	}

	for(i=0;i<Max_td;i++)
	{
		td=pthread_create(&td_thread[i],NULL,td_function,(void*)num);
		if(td!=0)
		{
			perror("pthread_create is failed!\n");
			return 1;
		}
	}

	while(scanf("%s",buffer))
	{
		sem_post(&sem);
		if(strncmp("end",buffer,3)==0)
			break;
	}

	printf("Waiting for thread to finish...\n");

	for (i = Max_td; i >=0; i--)
	{
		td=pthread_join(td_thread[i],&td_result);
		if (td!=0)
		{
			perror("pthread_join is failed!\n");
			return 1;
		}
	}

	sem_destroy(&sem);

	return 0;
}

void* td_function(void* num)
{
	int i=(int)(*(int*)num);
	while(strncmp("end",buffer,3)!=0)
	{
		printf("This is the %d thread",i);
		sem_wait(&sem);
		int rand_num=1+(int)(9.0*rand()/(RAND_MAX+1.0));
		sleep(rand_num);
	}
	pthread_exit(NULL);
}
