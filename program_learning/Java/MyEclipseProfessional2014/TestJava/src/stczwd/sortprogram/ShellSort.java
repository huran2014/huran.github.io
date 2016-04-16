package stczwd.sortprogram;

public class ShellSort {

	public static void main(String[] args) {
		int[] nums = new int[]{1,5,34,65,34,2,65,345,6,24,6,234,67,56,98,56,345,567,34,675,3456,54,76,4};
		shellSort(nums);
		for (int i = 0; i < nums.length; i++) {
			System.out.print(nums[i]+"\t");
		}
	}
	//希尔排序
	public static void shellSort(int[] nums) {
		int len = nums.length;
		//每次取一半为一组
		for(int group=len/2;group>0;group/=2) {
			//根据分组，主次进行希尔排序，共group个分组
			for(int i=0;i<group;i++) {
				for (int j = i; j < len; j+=group) {
					int temp=nums[j];
					//插入排序，逐个比较
					while(j>group && nums[j-group]>temp) {
						nums[j]=nums[j-group];
						j-=group;
					}
					nums[j]=temp;
				}
			}
		}
	}
	

}
