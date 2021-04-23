
package org.jzz.study.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import org.jzz.study.util.Print;


public class PriorityQueue_Test {
	
	static PriorityQueue<Integer> queue = new PriorityQueue<Integer>(){	//从小到大排序，小根堆
		public int compare(int a, int b) {
			return a - b;
		}
	};
	
	/** 求前K个元素 */
	public static void printTopValue(int[] nums, int k) {
		queue.clear();
		for (int i : nums) {
			if (queue.size() == k) {
				if (i >= queue.peek()) {
					queue.poll();
					queue.add(i);
				}
			} else {
				queue.offer(i);
//				queue.add(i);	
			}
		}
		while (!queue.isEmpty()) {
			Print.print(queue.poll());
		}
	}
	
	
	public static void main(String[] args) {
		int nums[] = {1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 9};
		printTopValue(nums, 3);
	}
	
}
