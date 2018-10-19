package org.jzz.studyProj;

import org.jzz.study.util.Print;

public class Test {
	
	static void shellSort(int[] a, int step) {
		for (int i = step; i < a.length; i++) {
			int j = i - step;
			int temp = a[i];	//a[i]会被右移占掉，先保存
			while (j >= 0 && a[j] > temp) {
				a[j + step] = a[j];
				j = j - step;
			}
			a[j + step] = temp;
		}
		Print.PrintIntArr(a);
	}
	
	static void sort(int[] a, int[] steps) {
		for (int step : steps) {
			shellSort(a, step);
		}
	}
	
	public static void main(String[] args) {
		int A[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		int[] steps = {7,3,1};
		sort(A, steps);
	}
}
