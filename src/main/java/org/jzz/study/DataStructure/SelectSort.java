package org.jzz.study.DataStructure;

import org.jzz.study.util.Print;

public class SelectSort {
	
	static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	//简单选择排序
	static void simpleSelectSort(int[] a) {
		// 从头开始遍历，将后续的最小值交换到当前索引
		for (int i = 0; i < a.length; i++) {
			int minIdx = i;
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] < a[minIdx]) {
					minIdx = j; //只需要记录最小值坐标，不要直接复制值
				}
			}
			//交换到当前索引
			swap(a, minIdx, i);
			Print.PrintIntArr(a);
		}
	}
	
	//二元选择排序
	static void advanceSelectSort(int[] a) {
		// 做不超过n/2趟选择排序 
		for (int i = 0; i < a.length / 2; i++) {
			int minIdx = i;
			int maxIdx = i;
			for (int j = i + 1; j < a.length - i; j++) {
				if (a[j] < a[minIdx]) {
					minIdx = j;
					continue;
				}
				if (a[j] > a[maxIdx]) {
					maxIdx = j;
				}
			}
			if (i == maxIdx) {	
				//如果maxIdx等于i，则交换后maxIdx变为minIdx
				maxIdx = minIdx; 
			}
			swap(a, minIdx, i); //先交换min
			swap(a, maxIdx, a.length - i - 1); //再交换max
			Print.PrintIntArr(a);
		}
	}
	
	
	public static void main(String[] args) {
		int a[] = {3,1,5,7,2,4,9,6,10,8}; 
		int b[] = { 10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2, 16};
		simpleSelectSort(a);
		advanceSelectSort(b);
	}
}
