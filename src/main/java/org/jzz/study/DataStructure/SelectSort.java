package org.jzz.study.DataStructure;

import org.jzz.study.util.Print;

public class SelectSort {
	//简单选择排序
	static void simpleSelectSort(int[] a) {
		// 从头开始遍历，将后续的最小值交换到当前索引
		for (int i = 0; i < a.length; i++) {
			int min = a[i];
			int minIdx = i;
			for (int j = i + 1; j < a.length; j++) {
				if (a[j] < min) {
					minIdx = j; //只需要记录最小值坐标，不要直接复制值
				}
			}
			//交换到当前索引
			min = a[minIdx];
			a[minIdx] = a[i];
			a[i] = min;
		}
	}
	
	
	public static void main(String[] args) {
		int a[] = {3,1,5,7,2,4,9,6,10,8}; 
		int b[] = { 10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		simpleSelectSort(a);
		Print.PrintIntArr(a);
		
		Print.PrintIntArr(b);
	}
}
