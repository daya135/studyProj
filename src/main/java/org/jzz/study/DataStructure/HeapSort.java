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
			adjustHeap(a, i, a.length);
			
		}
		Print.PrintIntArr(a);
		for (int j = a.length - 1; j > 0; j--) {
			swap(a, 0, j);
			adjustHeap(a, 0, j);
			Print.PrintIntArr(a);
		}
	}
	
	static void adjustHeap(int[] a, int i, int length) {
		int temp = a[i];
		for (int k = 2 * i + 1; k < length; k = k * 2 + 1) {
			if (k + 1 < length && a[k] < a[k + 1]) {
				k++;
			}
 			if (a[k] > temp) {
 				a[i] = a[k];
 				i = k;
 			} else {
				break;
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
