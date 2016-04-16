/*************************************************************************
	> File Name: pthread.c
	> Author:stczwd
	> Mail: qcsd2011@163.com 
	> Created Time: 四  5/14 15:22:18 2015
  > Explain: 
 ************************************************************************/
#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>
#include <unistd.h>

#define MAX_thread 5

void *thread_function(void *arg);

int main()
{
	int td;
	pthread_t td_thread[MAX_thread];
	void *td_result;
	int i;
	int* num=&i;

	for(i=0;i<MAX_thread;i++)
	{
		td=pthread_create(&td_thread[i],NULL,thread_function,(void*)num);
		if(td!=0)
		{
			perror("Thread creat failed!\n");
			return 1;
		}
		sleep(1);
	}

	printf("Waiting for threads to finished....\n");

	for(i=0;i<MAX_thread;i++)
	{
		td=pthread_join(td_thread[i],&td_result);
		if(td==0)
			printf("Picked up a thread:%d\n",i);
		else
			perror("pthread_join failed\n");
	}

	printf("All done!\n");

	return 0;
}

void* thread_function(void* i)
{
	int num=(int)(*(int*)i);
	printf("thread_funtion is running in:%d\n",num);
	int rand_num=1+(int)(9.0*rand()/(RAND_MAX + 1.0));
	sleep(rand_num);
	printf("The %d thread is quit.\n",num);
	pthread_exit(NULL);
}
