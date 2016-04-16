package stczwd.sortprogram;

import java.util.Random;

import org.apache.mahout.math.Arrays;

public class Sort {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int length=100000000;
		phiSort phi=new phiSort(length);
		System.out.println("总的排序长度是"+phi.getLength());
		System.out.println("/*************************这是生成的length长度的随机数组***************************\\");
		System.out.println("/*****************************现在是见证奇迹的时刻********************************\\");
		int[] nums=Arrays.copyOf(phi.getIds(),length);
		//phi.viewnums(nums);
		phi.lateralSort(nums);
		nums=Arrays.copyOf(phi.getIds(),length);
		//phi.viewnums(nums1);
		long currentTime=System.currentTimeMillis();
		phi.quickSort(nums,0, phi.getLength()-1);
		System.out.println("quickSort排序花费的时间是"+(System.currentTimeMillis()-currentTime)+"，数组长度是"+phi.getLength());
	}
}

class phiSort
{
	private int[] ids;
	private int length;
	private long current_time;
	
	public int[] getIds() {
		return ids;
	}

	public int getLength() {
		return length;
	}
	
	public phiSort(int length) {
		super();
		this.length=length;
		this.ids = new int[this.length];
		Randomint();
	}
	
	//生成随机的length长的数组
	private void Randomint()
	{
		Random rand= new Random();
		int ids_length=this.length;
		while(ids_length-->0)
			this.ids[ids_length]=rand.nextInt(this.length);
	}
	
	public void quickSort(int[] nums,int min,int max)
	{
		if(min<=max)
		{
			int middle=getmiddle(nums,min,max);
			//System.out.println(min+","+max+","+middle);
			//进入递归函数
			quickSort(nums,min,middle-1);
			quickSort(nums,middle+1,max);
		}
	}
	
	private int getmiddle(int[] nums,int min,int max)
	{
		//作为中轴，小于它的在左侧，大于它的在右侧
		int temp=nums[min];
		//循环进行下去，知道整个序列都被转移完成
		while(min<max)
		{
			//从高位开始向下，直到某个值小于中轴停止
			while(max>min && nums[max]>=temp) max--;
			//将从高位引出的小于于中轴的值，附加到低位上。
			nums[min]=nums[max];
			//从低位开始向上，直到某个值大于中轴停止
			while(max>min && nums[min]<=temp) min++;
			//将从低位引出的大于中轴的值，附加到高位上。
			nums[max]=nums[min];
			//此时，高位原先小的数字被大的数字代替，低位出原先小的数字被打的数字代替，可以继续进行下去。
		}
		//将最后空缺处来的位置补上，也就是把中轴补上
		nums[min]=temp;
		return min;
	}

	public void lateralSort(int[] nums) {
		current_time=System.currentTimeMillis();
		//Comb sort: http://en.wikipedia.org/wiki/Comb_sort
		int length = nums.length;
		int gap = length;
		boolean swapped = false;
		while (gap > 1 || swapped) {
			if (gap > 1) {
				//phi=1/0.618,phi是黄金分割率的倒数
				gap /= 1.247330950103979; // = 1 / (1 - 1/e^phi)
			}
			swapped = false;
			int max = length - gap;
			for (int i = 0; i < max; i++) {
				int other = i + gap;
				//判断ids[i]<ids[j];
				if (isLess(nums,other, i)) {
					swap(nums,i, other);
					swapped = true;
				}
			}
		}
		System.out.println("PHISort排序花费的时间是"+(System.currentTimeMillis()-this.current_time)+"，数组长度是"+this.length);
	}
	
	//目前是item
	private boolean isLess(int[] nums,int i, int j) {
			return nums[i] < nums[j];
	}

	//交换ids和values内容，也就是itemid和prefs
	private void swap(int[] nums,int i, int j) {
		int temp1 = nums[i];
		nums[i] = nums[j];
		nums[j] = temp1;
	}
	
	public void viewnums(int[] nums)
	{
		for(int i=0;i<this.length;i++)
		{
			if(i%100==0)
				System.out.println();
			System.out.print(nums[i]+"\t");
		}
		System.out.println();
	}
}