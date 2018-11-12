package org.jzz.studyProj;

import org.jzz.study.util.Print;

import sun.tools.jar.resources.jar;

public class Test {
	
	static void swap(int a[], int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
		
	}
	
	static int[] getNext(String str) {
		char[] p = str.toCharArray();
		int next[] = new int[p.length];
		int j = 0;
		int k = -1;
		next[0] = k;
		while (j < p.length -1) {
			if (k == -1 || p[j] == p[k]) {
				next[++j] = ++k;
			} else {
				k = next[k];
			}
			
		}
		return next;
	}
	
	
	
	public static void main(String[] args) {
		int a[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		int[] steps = {5, 3, 1};
		String tStr = "ABACDABABH";
		String pString = "ABAC";
//		sort(a, steps);
//		devide(a, 0, a.length - 1, new int[a.length]);
//		quickSort(a, 0, a.length - 1);
//		heapSort(a);
//		sort(a);
		Print.PrintIntArr(getNext(tStr));
		
		
		
		
	}
}
