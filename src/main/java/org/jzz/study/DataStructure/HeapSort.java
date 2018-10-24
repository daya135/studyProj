package org.jzz.study.DataStructure;

import org.jzz.study.util.Print;

public class HeapSort {
	
	static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	static void heapSort(int[] a) {
		for (int i = a.length / 2 - 1; i >= 0; i--) {
			adjustHeap(a, i, a.length);		//建堆，从下往上建，逐级调整
			
		}
		Print.PrintIntArr(a);
		for (int j = a.length - 1; j > 0; j--) {
			swap(a, 0, j);		//交换堆顶和堆底元素，并从堆中移除这个元素
			adjustHeap(a, 0, j);	//从堆顶开始调整堆，逐级向下调整
			Print.PrintIntArr(a);
		}
	}
	
	static void adjustHeap(int[] a, int i, int length) {
		int temp = a[i];
		for (int k = 2 * i + 1; k < length; k = k * 2 + 1) { //2*i+1肯定是直接左子节点！！
			if (k + 1 < length && a[k] < a[k + 1]) {
				k++;
			}
 			if (a[k] > temp) {	
 				a[i] = a[k];	//将大的子节点换到父节点位置
 				i = k;	//此时i向下移，指向子节点位置
 			} else {
				break;	//因为是自下而上建好的堆，所以两个子节点都比父节点小的时候就不用往下找了
			}
		}
		a[i] = temp;
	}
	
	
	public static void main(String[] args) {
		int b[] = { 10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		
		Print.PrintIntArr(b);
		heapSort(b);
	}
}
