/**
 * �����̵߳Ļ����÷���ע������
 * ע�⣺ͬһ���߳�ֻ�ܽ���һ�Ρ�����ͬһ������2���̣߳�����ͬʱ����
 * ���Ǵ����̰߳�ȫ���⡣��Ϊ����߳�ͬʱ����ĳ�����ݡ�
 * ģ����Ʊ�������ܹ�2000��Ʊ��3����Ʊ��ͬʱ����
 */
package huitujichu;

public class xianchengjichu {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		//����һ������
		Ticketwindow tw1=new Ticketwindow(1000);
		//ͬʱ����3���߳�
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
	//���þ�̬�����������ͻᱻ3����Ʊ��ͬʱ����
	private static int ticketnum=20;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			//Ϊ�����̰߳�ȫ�ԣ�����ʹ��synchronized��Object������֤�ص�����ԭ����
			//Ҳ���Ǳ���ֻ��һ���߳�������δ���
			synchronized(this)
			{
				if(ticketnum>0)
				{
					//���������Ʊ�Ĵ���
					//Thread.currentThread.getname()���Ի�ȡ����ǰ���߳�����
					System.out.println(Thread.currentThread().getName()+"�����۳���"+ticketnum+"��Ʊ");
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