package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

/* 0-1背包问题 */
public class packproblem {
	int capacity = 9;	//背包容量
	int n = 4;	//石头数量
	
	int[] w = {3,4,5,6}; //1~n的体积
	int[] v = {5,7,8,11};	//1~n的价值
	int[][] V = new int[n + 1][capacity + 1]; //v[i][j]——前i个物品在当前背包容量为j时，最佳组合对应的价值
	
	int max_i = 0;
	int max_j = 0;
	int max_V = 0;
	
	//打表
	packproblem FindMax() {
		for (int i = 0; i <= n; i++) {
			V[i][0] = 0;
		}
		for (int j = 0; j <= capacity; j++) {
			V[0][j] = 0;
		}
		for(int i = 1; i <= n; i++) {
			for (int j = 1; j <= capacity; j++) {
				if (j < w[i - 1]) {	//注意下标，i是从1开始的，所以w[i - 1]表示第i个
					V[i][j] = V[i - 1][j]; //包容量小于当前石头体积
				} else {
					if (V[i - 1][j] > (V[i - 1][j - w[i - 1]] + v[i - 1])) {
						V[i][j] = V[i - 1][j];
					} else {
						V[i][j]=V[i - 1][j - w[i - 1]] + v[i - 1];
					}
				}
				//记录最大值
				if (max_V < V[i][j]) {
					max_V = V[i][j];
					max_i = i;
					max_j = j;
				}
				System.out.print(V[i][j] + " ");
			}
			Print.print("\n");
		}
		return this;
	}
	
	void FindWhat() {
		//非递归法
//		int i = max_i;
//		int j = max_j;
//		while (i > 0) {
//			if (V[i][j] == V[i - 1][j]) {
//				System.out.println("第" + i + "个没选");
//			} else if (V[i][j] == (V[i - 1][j - w[i - 1]] + v[i - 1])) {
//				System.out.println("第" + i + "个选中");
//				j = j - w[i - 1];	//回溯
//			}
//			i--;
//		}
		
		//递归法
		FindWhat(max_i, max_j);
	}
	
	void FindWhat(int i, int j) {
		if (i > 0) {
			if (V[i][j] == V[i - 1][j]) {	//关键条件是回到上一个解空间的条件
				System.out.println("第" + i + "个没选");
				FindWhat(i - 1, j);
			} else if(V[i][j] == (V[i - 1][j - w[i - 1]] + v[i - 1])) {
				System.out.println("第" + i + "个选中");
				FindWhat(i - 1, j - w[i - 1]);
			}
		}
	}
	
	public static void main(String[] args) throws Throwable {
		new packproblem().FindMax().FindWhat();
	}
}
