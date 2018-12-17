package org.jzz.studyProj;

import org.jzz.study.util.Print;

import com.sun.scenario.effect.Merge;

public class Test {
	
	static void swap(int a[], int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		
	}
	
	static int[] getNext(String str) {
		char[] p = str.toCharArray();
		int next[] = new int[p.length];
		
		return next;
	}
	
	static void devide(int[] a, int left, int right, int[] temp) {
		if (left < right) {
			int mid = (left + right) / 2;
			devide(a, left, mid, temp);
			devide(a, mid + 1, right, temp);
			merge(a, left, mid, right, temp);
			Print.PrintIntArr(a);
		}
	}
	
	static void merge(int[] a, int left, int mid, int right, int[] temp) {
		int i = left;
		int j = mid + 1;
		int t = 0;
		while (i <= mid && j <= right) {
			if (a[i] < a[j])
				temp[t++] = a[i++];
			else 
				temp[t++] = a[j++];
		}
		
		while (i <= mid) {
			temp[t++] = a[i++];
		}
		while (j <= right) {
			temp[t++] = a[j++];
		}
		
		t = 0;
		while (left <= right) {
			a[left++] = temp[t++];
		}
	}
	
	
	public static void main(String[] args) {
		int a[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		int[] steps = {5, 3, 1};
		String tStr = "ABACDABABH";
		String pString = "ABAC";
//		sort(a, steps);
		devide(a, 0, a.length - 1, new int[a.length]);
//		quickSort(a, 0, a.length - 1);
//		heapSort(a);
//		sort(a);
//		Print.PrintIntArr(getNext(tStr));
		
	}
}
