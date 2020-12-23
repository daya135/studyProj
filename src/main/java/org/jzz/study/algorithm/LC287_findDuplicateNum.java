package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

/**
 * 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。
 * 不能更改原数组（假设数组是只读的）。
 * 只能使用额外的 O(1) 的空间。
 * 时间复杂度小于 O(n2) 。
 * 数组中只有一个重复的数字，但它可能不止重复出现一次。
 **/
public class LC287_findDuplicateNum {
	//二分查找
	public static int binarySearch(int[] nums) {
		int ans = -1;
		int l = 1;
		int r = nums.length - 1;
		while (l <= r) {
			int mid = (l + r) /2;
			int count = 0;
			for (int i = 0; i < nums.length; i++) {
				if (nums[i] <= mid) {
					count++;
				}
			}
			if (count <= mid) {
				l = mid + 1;
			} else {
				r = mid - 1;	//如1-7，mid = 3，ans是3，则此处执行后l和r的区间不包含ans了，但下面的一句能捕获到正确的ans，最终l>r跳出时，ans没有修改
				ans = mid;	//此处如果ans != mid 则存在ans<mid 使得count[ans]>mid,从而进入到次句，修改ans=mid_new
			}
		}
		
		return ans;
	}
	
	public static void main(String[] args) {
		int[][] nums = {{1,2,4,4,4,4,4},{3,1,3,4,2}};
		for (int i = 0; i < nums.length; i++) {
			Print.print(binarySearch(nums[i]));
		}
	}
}
