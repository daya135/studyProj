package org.jzz.study.DataStructure;

import org.jzz.study.util.Print;


public class KMP {
	
	static int[] getNext(String str) {
		char[] p = str.toCharArray();
		int next[] = new int[p.length];
		
		int k = -1;
		int j = 0;
		next[0] = -1;
		while (j < p.length - 1) {
			if (k == -1 || p[j] == p[k]) {
				next[++j] = ++k;
			} else {
				k = next[k];  //灵魂！！！
			}
			//改进版，当发生P[j] == P[next[j]]时，会多一次不必要的比较，见印象笔记kmp篇结尾
//			if (k == -1 || p[j] == p[k]) {
//				if (p[++j] == p[++k]) {
//					next[j] = next[k];
//				} else {
//					next[j] = k;
//				}
//			} else {
//				k = next[k];
//			}
		}
		return next;
	}
	
	static int strIdx(String str, String pat) {
		int next[] = getNext(pat);
		char[] strArr = str.toCharArray();
		char[] patArr = pat.toCharArray();
		int i = 0;
		int j = 0;
		while (i < strArr.length && j < pat.length()) {
			if (j == -1 || strArr[i] == patArr[j]) {
				i++;
				j++;
				continue;
			} else {
				j = next[j];
				continue;
			}
		}
		if (j == pat.length()) {
			return i - j;
		}
		return -1;
	}
	
	public static void main(String[] args) {
		String str = "ABACDABACDAABACDABACDABABHABACDABACDA";
		String pstr = "ABACDABABH";
		int[] a = getNext(pstr);
		int idx = strIdx(str, pstr);
		Print.PrintIntArr(a);
		Print.print(idx);
	}
	
}
