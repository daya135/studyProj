package org.jzz.study.DataStructure;

import org.jzz.study.util.Print;

public class MergeSort {
	
	static  void mergeSort(int[] arr) {
		int[] tmp = new int[arr.length];
		divide(arr, 0, arr.length - 1, tmp);
	}
	
	static void divide(int[] arr,int left,int right,int [] temp) {
		if( left < right) {
			int mid = (left + right) / 2;
			divide(arr, left, mid, temp);
			divide(arr, mid + 1, right, temp);
			merge(arr, left, mid, right, temp);
			Print.PrintIntArr(arr);
		}
	}
	
	static void merge(int arr[], int left, int mid, int right, int[] temp) {
		int i = left;
		int j = mid+1;
		int t = 0;
		while(i<=mid && j<=right) {
			if (arr[i] <= arr[j]) {
				temp[t++] = arr[i++];
			} else {
				temp[t++] = arr[j++];
			}
		}
		
		while (i <= mid) {
			temp[t++] = arr[i++];
		}
		while (j <= right) {
			temp[t++] = arr[j++];
		}
		
		t = 0;
		while(left <= right){
            arr[left++] = temp[t++];
        }
		
	}
	
	public static void main(String[] args) {
		int a[] = {3,1,5,7,2,4,9,6,10,8}; 
		int b[] = { 10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		
		
		mergeSort(b);
		Print.PrintIntArr(b);
	}
}
