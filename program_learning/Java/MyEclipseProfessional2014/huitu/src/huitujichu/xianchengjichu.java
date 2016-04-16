/**
 * 讲解线程的基础用法和注意事项
 * 注意：同一个线程只能进行一次。但是同一个类有2个线程，可以同时启动
 * 但是带来线程安全问题。因为多个线程同时调用某个数据。
 * 模拟售票环境，总共2000张票，3个售票点同时开卖
 */
package huitujichu;

public class xianchengjichu {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//创建一个窗口
		Ticketwindow tw1=new Ticketwindow(1000);
		//同时启用3个线程
		Thread t1=new Thread(tw1);
		Thread t2=new Thread(tw1);
		Thread t3=new Thread(tw1);
		
		t1.start();
		t2.start();
		t3.start();
	}
}

class Ticketwindow implements Runnable
{
	int waittime;
	public Ticketwindow(int waittime)
	{
		this.waittime=waittime;
	}
	//设置静态变量，这样就会被3个售票点同时销售
	private static int ticketnum=20;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			//为保障线程安全性，可以使用synchronized（Object）来保证重点程序的原子性
			//也就是必须只有一个线程运行这段代码
			synchronized(this)
			{
				if(ticketnum>0)
				{
					//输出正在售票的窗口
					//Thread.currentThread.getname()可以获取到当前的线程名称
					System.out.println(Thread.currentThread().getName()+"正在售出第"+ticketnum+"张票");
					try {
						Thread.sleep(waittime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ticketnum--;
				}
				else
				{
					break;
				}
			}
			
		}
	}
	
}