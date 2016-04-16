package links;

public class linklist {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		Link linklist=new Link();
		linklist.insertLink(0, "xiaoqiang", 23);
		linklist.insertLink(1, "xiaohong", 22);
		linklist.insertLink(3, "xiaoyu", 25);
		linklist.insertLink(2, "xiaozhao", 21);
		linklist.insertLink(4, "xiaoming", 26);
		linklist.deleteLink(0);
		linklist.deleteLink(2);
		linklist.insertLink(5, "xiaojie", 22);
		linklist.deleteLink(0);
	}

}

class Child//������������
{
	int id;
	private String name;
	private int age;
	private Child next;//ָ���������һ��
	
	public Child(int n, String str,int num,Child c)//���췽�����ƶ�
	{
		this.id=n;
		this.name=str;
		this.age=num;
		this.next=c;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Child getNext() {
		return next;
	}

	public void setNext(Child next) {
		this.next = next;
	}
}

class Link//��������
{
	private Child first=null;//��������firstָ���һ��
	private Child temp;//��������ʹ���м���

//	public Link()//��ʼ������
//	{
//		first=null;
//	}
	
	//������ʾ
	public void showLink()
	{
		this.temp=this.first;
		while(this.temp!=null)
		{
			System.out.println("id="+this.temp.getId()+",name="+this.temp.getName()+",age="+this.temp.getAge());
			this.temp=this.temp.getNext();
		}
		System.out.println("/********************Show Link ok!*********************/");
	}
	
	//�������
	public void insertLink(int n,String name,int num)
	{
		this.temp=this.first;
		//���ҵ�ָ��λ��
		if(this.temp==null)//��ԭ����û�����ݣ���ֱ�����
		{
			Child new_child=new Child(n,name,num,temp);
			this.first=new_child;
			this.temp=new_child;
		}
		else
		{
			while(this.temp!=null)//ֱ���ҵ����һ��
			{
				//�ҵ�Ҫ�����λ��
				if(this.temp.getNext()==null)
					break;
				if(this.temp.getId()<n && this.temp.getNext().getId()>=n)
				{
					break;
				}
				this.temp=this.temp.getNext();//ת����һ��
			}
			if(this.temp.getId()<n)//�µķ���temp����
			{
				Child new_child=new Child(n,name,num,this.temp.getNext());
				this.temp.setNext(new_child);
			}
			else//����Ҫ����temp��ǰ��
			{
				Child new_child=new Child(n,name,num,this.temp);
				this.temp=new_child;
			}
		}
		System.out.println("Insert ok!");
		this.showLink();
	}
	
	//����ɾ��
	public void deleteLink(int n)
	{
		this.temp=this.first;
		if(first==null)//��ԭ����û�����ݣ���ֱ�����
		{
			System.out.println("There is no data in the link, you can't delete anything");
		}
		else
		{
			if(this.first.getId()==n)//�鿴�Ƿ�Ҫɾ�����ǵ�һ��
			{
				this.first=this.first.getNext();
				System.out.println("Delete ok!");
			}
			else
			{
				while(this.temp.getNext()!=null)//ֱ���ҵ����һ��
				{
					//�ҵ�Ҫ�����λ�ã��ҵ�id˳�����
					if(this.temp.getNext().getId()==n)
					{
						break;
					}
					this.temp=this.temp.getNext();
				}
				if(this.temp.getNext()==null)
				{
					System.out.println("There is no data that you want to delete!");
				}
				else if(this.temp.getNext().getId()==n)
				{
					//ֱ�ӽ���һ�����ڵ����ǰ�ڵ㣬�Ӷ����ǵ�ǰ�ڵ�
					this.temp.setNext(this.temp.getNext().getNext());
					System.out.println("Delete ok!");
				}
			}
		}
		this.showLink();
	}
	
}