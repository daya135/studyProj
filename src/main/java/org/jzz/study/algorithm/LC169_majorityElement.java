package org.jzz.study.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import org.jzz.study.util.Print;

/**给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数大于 ⌊ n/2 ⌋ 的元素。*/
public class LC169_majorityElement {
	/** 哈希表法 */
	public int majorityElement_hash(int[] nums) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			int count = map.get(nums[i]) == null ? 1 : map.get(nums[i]) + 1;
			map.put(nums[i], count);
		}
		int max = 0;
		int major = nums[0];
		for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
			if (entry.getValue() > max) {
				major = entry.getKey();
				max = entry.getValue();
			}
		}
		return major;
	}
	/** 随机，然后看计数是否大于 n/2 */
	public int majorityElement_random(int[] nums) {
		Random random = new Random(123456);
		int count = 0;
		int randomIdx = 0;
		while (count <= nums.length / 2) {
			count = 0;
			randomIdx = random.nextInt(nums.length);
			for (int i = 0; i < nums.length; i++) {
				if (nums[i] == nums[randomIdx]) {
					count++;
				}
			}
		}
		return nums[randomIdx];	
	}
	/** 排序取中 */
	public int majorityElement_sort(int[] nums) {
		Arrays.sort(nums);
		return nums[nums.length / 2];
	}
	
	public int majorityElement_Boyer_Moore(int[] nums) {
		
		return 0;
	}
	
	public static void main(String[] args) {
		LC169_majorityElement obj = new LC169_majorityElement();
		int[] nums = new int[] {1,2,3,4,1,3,1,4,1,1,1,1};
		int[] nums2 = new int[] {6,5,5};
		int[] nums3 = new int[] {1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,
				1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,1,2,3,3
				,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3
				,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3
				,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3
				,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3
				,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3
				,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3
				,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3};
		Print.print(obj.majorityElement_hash(nums));
		Print.print(obj.majorityElement_random(nums2));
		Print.print(obj.majorityElement_sort(nums3));
	}
}
