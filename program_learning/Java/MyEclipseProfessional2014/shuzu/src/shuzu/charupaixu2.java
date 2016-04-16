package shuzu;

public class charupaixu2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int len=12;
		int shuju[]=new int[len];
		for(int i=0;i<len;i++)
		{
			//产生随机数
			int t=(int)(Math.random()*len);
			shuju[i]=t;
		}
		//int shuju[]={54,65,3,87,32,8,5,89,7,32};
		int jieguo[]=new int[len];
		jieguo[0]=shuju[0];
		for(int i=1;i<len;i++)
		{
			for(int j=0;j<i;j++)
			{
				if(shuju[i]>=jieguo[i-1])
				{
					jieguo[i]=shuju[i];
					break;
				}
				else
				if(shuju[i]<=jieguo[j])
				{
					int temp=shuju[i];
					for(int k=i;k>j;k--)
					{
						jieguo[k]=jieguo[k-1];
					}
					jieguo[j]=temp;
					break;
				}
			}
			
		}
		for(int m=0;m<len;m++)
		{
			System.out.print(jieguo[m]+" , ");
		}
	}
}