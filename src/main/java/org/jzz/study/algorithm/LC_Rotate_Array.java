package org.jzz.study.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jzz.study.util.Print;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;

/** 
 * 旋转数组：给定一个数组，将数组中的元素向右循环移动 k 个位置，其中 k 是非负数。
 * 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
 * 你可以使用空间复杂度为 O(1) 的 原地 算法解决这个问题吗？
 *  输入: nums = [1,2,3,4,5,6,7], k = 3
	输出: [5,6,7,1,2,3,4]
	解释:
	向右旋转 1 步: [7,1,2,3,4,5,6]
	向右旋转 2 步: [6,7,1,2,3,4,5]
	向右旋转 3 步: [5,6,7,1,2,3,4]
 
 * 题解:
 * 需要知道右移存在几个环路（从某一个节点出发回到当前节点）
   1 2 3 4 5 6 7 8 1 2 3 4 5 6 7 8 1 2 3 4 5 6 7 8
   1     4     7     2     5     8     3     6
   (上图一共走过了三个数组的长度)
   环路元素个数b * 移动的位数k = 数组长度n * 遍历走过的数组个数a
   即：b*k = n*a
   显然，na可以被n、k整除且结果都大于0
   所以，na是n、k的公倍数
   又因为na要取最小，所以bk = na = n,k的最小公倍数lcm(n,k)
   即：b = lcm(n,k) / k
   遍历次数: n / b = n*k / lcm(n,k) = gcd(n,k)
 */
public class LC_Rotate_Array {
	public static void rotate(int[] nums, int k) {
		int n = nums.length;
		k = k % n; //超过数组长度的移位简化为余数次移位
		int times = gcd(k, n);
		for (int i = 0; i < times; i++) {
			int current = i;
			int pre = nums[i];
			do {
				int nextIdx = (current + k) % n;
				int temp = nums[nextIdx];
				nums[nextIdx] = pre;
				pre = temp;
				current = nextIdx;
			} while (current != i);
		}
	}
	
	/** 求最大公约数, 递归 */
	public static int gcd(int a, int b) {
		return b > 0 ? gcd(b, a % b) : a;
	}
	
	public static void main(String[] args) {
		int[] nums = new int[] {1, 2, 3, 4, 5, 6, 7};
		rotate(nums, 3);
		Print.printArr(nums);
			}
}
