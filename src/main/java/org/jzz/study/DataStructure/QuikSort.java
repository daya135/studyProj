package org.jzz.study.DataStructure;

import org.jzz.study.util.Print;

import com.sun.javafx.image.impl.IntArgb;

public class QuikSort {
	
	static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	static int partition(int[] a, int low, int high) {	//划分区间，定好一个元素的绝对位置
		/* 三个关键字：“基准元素”、“左游标”、“右游标” */
		int pivot = a[low];	//取左边第一个为基准值,则第一次从右边的游标开始比较！重点！
		while (low < high) {
			while (low < high && a[high] >= pivot)	//从右往左移游标
				--high;
			if (low < high) {	//改进1: 坐标相同不需要交换
				swap(a, low, high);
				++low;	//改进2: 从high交换到low后，位于low坐标的元素不需要再比较了，后移一位减少一次比较次数
			}
			while (low < high && a[low] <= pivot) //从左往右移游标
				++low;
			if (low < high) {	
				swap(a, low, high);
				--high; //从low交换到high后，这个坐标不需要再比较了，前移一位
			}
		}
		Print.PrintIntArr(a);
		return low;
	}
	
	static int partition2(int[] a, int low, int high) {
		int pidx = low; //方法二：只记录哨兵位置，但会增加取值操作的次数
		while(low < high) {
			while (low < high && a[high] >= a[pidx]) --high;
			if(low < high) {
				swap(a, low, high);
				pidx = high;	//跟踪哨兵位置
				++low;
			}
			while (low < high && a[low] <= a[pidx]) ++low;
			if(low < high) {
				swap(a, low, high);
				pidx = low;		//跟踪哨兵位置
				++low;
			}
		}
		Print.PrintIntArr(a);
		return pidx;
	}
	
	static void quickSort(int[] a, int low, int high) {
		if (low < high) {
			int pivot_index = partition(a, low, high);
//			int pivot_index = partition2(a, low, high);
			quickSort(a, low, pivot_index - 1);	//划分左区间
			quickSort(a, pivot_index + 1, high);	//划分右区间
		}
		
	}
	
	public static void main(String[] args) {
		int a[] = {3,1,5,7,2,4,9,6,10,8}; 
		int b[] = { 10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		Print.PrintIntArr(a);
		
		quickSort(a, 0, a.length - 1);
		Print.PrintIntArr(b);
		quickSort(b, 0, b.length - 1);
		
	}
}
