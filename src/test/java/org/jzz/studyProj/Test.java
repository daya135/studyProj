package org.jzz.studyProj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jzz.study.DataStructure.BaseNode;
import org.jzz.study.DataStructure.HuffmanTree;
import org.jzz.study.DataStructure.Tree;
import org.jzz.study.util.Print;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.sun.javafx.collections.SortableList;
import com.sun.scenario.effect.Merge;

import java_cup.runtime.lr_parser;

public class Test {
	
	static void swap(int a[], int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
	static int[] getNext(String str) {
		char[] c = str.toCharArray();
		int[] next = new int[c.length];
		int k = -1;
		next[0] = -1;
		int i = 0;
		while (i < c.length - 1){
			if (k == -1 || c[k] == c[i]) {
				k++;
				i++;
				next[i] = k;
			} else {
				k = next[k];
			}
		}
	
		return next;
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
		int a[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		int[] steps = {4, 2, 1};
		String tStr = "ABACDABABH";
		String pString = "AAABCAAABC";
//		while (true) {
//			System.in.read();
//		}
//		sort(a);
//		shellSort(a, steps);
//		devideSort(a, 0, a.length - 1, new int[a.length]);
//		quickSort(a, 0, a.length - 1);
//		heapSort(a);
		
//		Print.PrintIntArr(getNext("ABACDABABH"));
//		FindMax();
//		findWhy(max_i, max_j);
//		findQueen(new int[SIZE], 0);
		
		Print.PrintIntArr(getNext(pString));
		
		char[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
		int[] w = {2, 10, 3, 7, 5, 8, 9, 1, 6};
		int len = 9;
		HuffmanTree hufftree = new HuffmanTree();
//		hufftree.createTree(c, w, len);
//		Print.print(System.currentTimeMillis());
//		printTree(hufftree.root);
//		Print.print(System.currentTimeMillis());
		
		String site = "出口|卫生间.aaa";
//		Print.print(site.indexOf("\\."));
//		Print.print(site.substring(site.indexOf(".") + 1));
//		StringBuilder sBuffer = new StringBuilder(site);
//		String[] sites = sBuffer.toString().split("|");
//        for (String s : sites) {
//            Print.print( "读取sit内容:" + s);
//        }
		
		Print.printArr(c, "\n");
	}
}
