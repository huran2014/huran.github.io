package shuzu;

public class maopaopaixu {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		int paixu[]={1,34,-4,3,-6,4,-134};
		int temp=0;
		System.out.println("����˳��Ϊ(�ɴ�С)��");
		for(int i=0;i<paixu.length-1;i++)
		{
			for(int j=i+1;j<paixu.length;j++)
			{
				if(paixu[i]<=paixu[j])
				{
					temp=paixu[j];
					paixu[j]=paixu[i];
					paixu[i]=temp;
				}
			}
		}
		for(int i=0;i<paixu.length;i++)
		{
			System.out.print(paixu[i]+", ");
		}
	}
}
