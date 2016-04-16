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

class Child//创建孩子链表
{
	int id;
	private String name;
	private int age;
	private Child next;//指向链表的下一项
	
	public Child(int n, String str,int num,Child c)//构造方法，制定
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

class Link//创建链表
{
	private Child first=null;//创建链表，first指向第一个
	private Child temp;//创建链表使用中间量

//	public Link()//初始化链表
//	{
//		first=null;
//	}
	
	//链表显示
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
	
	//链表插入
	public void insertLink(int n,String name,int num)
	{
		this.temp=this.first;
		//先找到指定位置
		if(this.temp==null)//若原链表没有数据，则直接添加
		{
			Child new_child=new Child(n,name,num,temp);
			this.first=new_child;
			this.temp=new_child;
		}
		else
		{
			while(this.temp!=null)//直到找到最后一个
			{
				//找到要插入的位置
				if(this.temp.getNext()==null)
					break;
				if(this.temp.getId()<n && this.temp.getNext().getId()>=n)
				{
					break;
				}
				this.temp=this.temp.getNext();//转到下一个
			}
			if(this.temp.getId()<n)//新的放在temp后面
			{
				Child new_child=new Child(n,name,num,this.temp.getNext());
				this.temp.setNext(new_child);
			}
			else//否则要放在temp的前面
			{
				Child new_child=new Child(n,name,num,this.temp);
				this.temp=new_child;
			}
		}
		System.out.println("Insert ok!");
		this.showLink();
	}
	
	//链表删除
	public void deleteLink(int n)
	{
		this.temp=this.first;
		if(first==null)//若原链表没有数据，则直接添加
		{
			System.out.println("There is no data in the link, you can't delete anything");
		}
		else
		{
			if(this.first.getId()==n)//查看是否要删除的是第一个
			{
				this.first=this.first.getNext();
				System.out.println("Delete ok!");
			}
			else
			{
				while(this.temp.getNext()!=null)//直到找到最后一个
				{
					//找到要插入的位置，找到id顺序插入
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
					//直接将下一个链节点给当前节点，从而覆盖当前节点
					this.temp.setNext(this.temp.getNext().getNext());
					System.out.println("Delete ok!");
				}
			}
		}
		this.showLink();
	}
	
}