package stczwd.Thread_Lock;

import java.util.Random;
import java.util.concurrent.*;

import javax.print.DocFlavor.CHAR_ARRAY;

import com.ibm.icu.impl.duration.TimeUnit;

public class Thread_run {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//this is one method to run Thread
//		for(int i=0;i<5;i++)
//			new Thread(new ThreadRunTest()).start();
//		System.out.println("Waiting for stop:");
		
		//this is another way to run Thread
		ExecutorService executor=Executors.newCachedThreadPool();
		for(int i=0;i<5;)
		{
			executor.execute(new ThreadRunTest(++i));
//			Callable callable= new ThreadRunTest();
//			try {
//				System.out.println(executor.submit(callable).get());
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
		executor.shutdown();
	}

}

class ThreadRunTest implements Runnable,Callable<String>
{
	private int thread_num=1;
	private int countdown=100;
	private static int taskcount=0;
	private final int id=taskcount++;
	private int taskruntimes=1;
	
	public ThreadRunTest() {
	}
	
	public ThreadRunTest(int thread_num) {
		this.thread_num=thread_num;
	}
	
	public ThreadRunTest(int thread_num,int countdown)
	{
		this.countdown=countdown;
	}
	
	private String count()
	{
		Random rand = new Random();
		countdown-=rand.nextInt(32);
//		return "#"+id+"\t"+(countdown>0? countdown+"\t taskruntimes:"+taskruntimes++:"This is over,this task "+id+" run totally "+taskruntimes+" times");
		return "#"+id+"\t"+Thread.currentThread()+"\t"+(countdown>0? countdown+"\t taskruntimes:"+taskruntimes++:"This is over,this task "+id+" run totally "+taskruntimes+" times");
	}
	
	@Override
	public void run() {
		Thread.currentThread().setPriority(this.thread_num);
		while(countdown>0)
		{
			System.out.println(count());
//			Thread.yield();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		return count();
	}
	
}