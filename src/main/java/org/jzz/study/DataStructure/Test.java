package org.jzz.study.DataStructure;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.jzz.study.algorithm.TreeNode;
import org.jzz.study.util.Print;

public class Test {
	static void swap(int a[], int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	static int[] getNext(String str) {
		char c[] = str.toCharArray();
		int next[] = new int[c.length];
		int k = -1;
		next[0] = -1;
		int j = 0;
		while (j < c.length - 1) {
			if (k == -1 || c[k] == c[j]) {
				k++;
				next[++j] = k;
			} else {
				k = next[k];
			}
		}
		return next;
	}
	
	static void sort(int[] a) {
		for (int k = a.length / 2 - 1; k >= 0; k--) {
			buildHeap(a, k, a.length);
		}
		Print.print("ssssssss");
		Print.printArr(a);
		for (int j = a.length - 1; j > 0; j--) {
			swap(a, 0, j);
			buildHeap(a, 0, j);
		}
	}
	
	static void buildHeap(int[] a, int head, int length) {
		int temp = a[head];
		for (int k = 2  * head + 1; k < length; k = 2  * k + 1) {
			if (k + 1 < length && a[k + 1] > a[k]) {
				k = k + 1;
			}
			if (a[k] > temp) {
				a[head] = a[k];
				head = k;
			} else {
				break;
			}
		}
		a[head] = temp;
	}
		
	
//	static final int capacity = 8;
//	static final int num = 4;
//	static final int[] w = {2, 3, 4, 5};
//	static final int[] v = {3, 4, 5, 6};
//	static int[][] V = new int[num + 1][capacity + 1];
//	static int maxV = 0;
//	static int max_i = 0;
//	static int max_j = 0;
//	static {
//		Arrays.fill(V[0], 0);
//		for (int i = 0; i < num; i++) {
//			V[i][0] = 0;
//		}
//	}
	
    
	
	
	public static void main(String[] args) throws IOException {
//		int a[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		int a[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		int[] steps = {4, 2, 1};
		String tStr = "ABACDABABH";
		String pString = "AAABCAAABC";
//		while (true) {
//			System.in.read();
//		}
//		sort(a);
//		sort(a, 1);
//		sort(a, steps);
//		sort(a, 0, a.length - 1, new int[a.length]);
//		sort(a, 0, a.length - 1);
//		Print.printArr(a);
		
		//测试kmp
		Print.printArr(getNext("ABACDABABH"));	

		//		FindMax();
//		findWhy(max_i, max_j);
//		findQueen(new int[SIZE], 0);
		
//		Print.PrintIntArr(getNext(pString));
		
		char[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
		int[] w = {2, 10, 3, 7, 5, 8, 9, 1, 6};
		int len = 9;
//		HuffmanTree hufftree = new HuffmanTree();
//		hufftree.createTree(c, w, len);
//		Print.print(System.currentTimeMillis());
//		printTree(hufftree.root);
//		Print.print(System.currentTimeMillis());
		
		TreeNode tree = TreeNode.createTree(new Integer[] {3,9,20,null,null,15,7});
		
		int[] nums  = {1, 2, 3};
	}
}
