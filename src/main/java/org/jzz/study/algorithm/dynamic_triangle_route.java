package org.jzz.study.algorithm;

import java.util.Arrays;
import java.util.Random;


/* 计算三角形顶点到底边数字之和最大的路线
	1 
   1 8 
  2 3 6 
 4 1 1 3 
9 1 8 9 1  
*/
public class dynamic_triangle_route {
	static int max = 20;
	static int a[][] = new int[max][max]; //三角形
	static int m[][] = new int[max][max]; //
	
	static {
		for (int i = 0; i < m.length; i++)
			Arrays.fill(m[i], -1);
		for (int i = 0; i < max; i++) {
			for (int j = 0; j <= i; j++) {
				a[i][j] = new Random().nextInt(9) + 1;
			}
		}
		print(a);
	}
	
	//纯递归
	public static int route(int i, int j) {
		if (i == max - 1)
			return a[i][j]; //�
		int r = a[i][j] + Math.max(route(i + 1, j), route(i + 1, j + 1));
		return r;
	}
	
	//非递归
	public static int route1(int i, int j) {
		if (m[i][j] != -1) {
			return m[i][j];
		}
		if (i == max - 1)
			return a[i][j]; //�
		m[i][j] = a[i][j] + Math.max(route1(i + 1, j), route1(i + 1, j + 1));
		return m[i][j];
	}
	
	//自顶向下
	public static int route2() {
		for (int i = 0; i < max; i++) {
			m[max - 1][i] = a[max - 1][i];
		}
		for (int i = max - 2; i >= 0; i--) {
			for (int j = 0; j <= i; j++) {
				m[i][j] = Math.max(m[i + 1][j], m[i + 1][j + 1]) + a[i][j];
			}
		}
		return m[0][0];
	}
	
	//自底向上
	public static int route3() {
		int temp[] = new int[max]; //ֻ��һά����
		for (int i = 0; i < max; i++) {
			temp[i] = a[max - 1][i];
		}
		for (int i = max - 2; i >= 0; i--) {
			for (int j = 0; j <= i; j++) {
				temp[j] = Math.max(temp[j], temp[j + 1]) + a[i][j];
			}
		}
		return temp[0];
	}
	
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		int r = route(0, 0);
		long t1 = System.currentTimeMillis();
		System.out.println(r + "    " + (t1 - start));
		
		r = route1(0, 0);
		long t2 = System.currentTimeMillis();
		System.out.println(r + "    " + (t2 - t1));
		
		r = route2();
		long t3 = System.currentTimeMillis();
		System.out.println(r + "    " + (t3 - t2));
		
		r = route3();
		long t4 = System.currentTimeMillis();
		System.out.print(r + "    " + (t4 - t3));
		
	}
	
	public static void print(int a[][]) {
		String fillString = "                                                                                                 ";
		for (int i = 0; i < a.length; i++) {
			System.out.print(fillString.substring(0, a.length - i - 1));
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] > 0) 
					System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}
	}
}
