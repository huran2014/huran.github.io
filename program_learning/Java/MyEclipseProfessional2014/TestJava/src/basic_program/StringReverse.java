package basic_program;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.tools.ant.taskdefs.PathConvert.MapEntry;


public class StringReverse {

	public static void main(String[] args) {
		//		System.out.println(isAnagram("anagram", "nagaram"));
//		String a = "A";
//		System.out.println(Integer.valueOf(a.charAt(0)));
		int[] nums = {3,3};
		System.out.println(containsDuplicate(nums));
	}

	public static boolean isAnagram(String s, String t) {
		char[] ArrayS = s.toCharArray();
		char[] ArrayT = t.toCharArray();
		Arrays.sort(ArrayS);
		Arrays.sort(ArrayT);
		return String.valueOf(ArrayS).equals(String.valueOf(ArrayT));
	}

	public static boolean containsDuplicate(int[] nums) {
		// 时间耗时太长
		// Arrays.sort(nums);
		// for(int i=0;i<nums.length-1;i++) {
		//     if(nums[i]==nums[i+1])
		//         return true;
		// }
		// return false;
		// 改用map的方式来解决
		HashMap<Integer,Integer> map = new HashMap();
		for(int i=0;i<nums.length;i++) {
			if(map.containsKey(nums[i])) {
				return true;
			} else {
				map.put(nums[i], 1);
			}
		}
		for (Entry<Integer, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey()+"\t"+entry.getValue());
		}
		return false;
	}

}
