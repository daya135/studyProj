package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

public class PrimeNumber {
	static long timeBefore; //开始时间
	static long timeAfter;	//结束时间
	
	//幼稚园版本
	public static boolean validate1(int n) {
		timeBefore = System.currentTimeMillis();
		if (n <= 1) return false;
		if (n == 2) return true;
		for (int i = 2; i < n; i++) {	//傻傻的遍历：“除了1和其本身，没有任何数能整除它”
			if (n % i == 0) {
				return false;
			}
		}
		timeAfter = System.currentTimeMillis();
		Print.print(" 运行时间：" + (timeAfter - timeBefore));
		return true;
	}
	
	//小学生版本
	public static boolean validate2(int n) {
		timeBefore = System.currentTimeMillis();
		if (n <= 1) return false;
		for (int i = 2; i < n / 2; i++) {	//只需要找前一半就行了，因为超过n一半大小的数a肯定找不到对应的整数b，使得a x b = n
			if (n % i == 0) {
				return false;
			}
		}
		timeAfter = System.currentTimeMillis();
		Print.print("运行时间：" + (timeAfter - timeBefore));
		return true;
	}
	
	//中学生
	public static boolean validate3(int n) {
		timeBefore = System.currentTimeMillis();
		if (n == 2) return true;	//先把2放一边
		if (n <= 1 || n % 2 == 0) return false;	//再排除偶数
		for (int i = 3; i < n / 2; i += 2) { //除了0和2以外的偶数都不是素数，只需要从奇数里面找就行了
			if (n % i == 0) {
				return false;
			}
		}
		timeAfter = System.currentTimeMillis();
		Print.print("运行时间：" + (timeAfter - timeBefore));
		return true;
	}
	
	//高中生
	public static boolean validate4(int n) {
		timeBefore = System.currentTimeMillis();
		if (n == 2) return true;	//先把2放一边
		if (n <= 1 || n % 2 == 0) return false;	//再排除偶数
		for (int i = 3; i < Math.sqrt(n); i += 2) { //在方法3的基础上，将n/2替换为平方根
			if (n % i == 0) {
				return false;
			}
		}
		timeAfter = System.currentTimeMillis();
		Print.print("运行时间：" + (timeAfter - timeBefore));
		return true;
	}
	
	//埃拉托色尼选筛法,素数筛法,经常作为一道题的一部分用来打一定范围内素数表
	public static boolean validate5(int n) {
		int[] prime = new int[1000000];	//表的大小为一百万
		timeBefore = System.currentTimeMillis();
		prime[1] = 0;
		for(int i = 2; i <= n ; i++){
			prime[i] = 1;
		}	
		for (int i = 2; i <= n; i++) {
			if (prime[i] != 0) 
				Print.print(i);
			for (int j = i + i; j <= n; j += i) 
				prime[i] = 0;
		}
		timeAfter = System.currentTimeMillis();
		Print.print("PrimeNumber.validate3，运行时间：" + (timeAfter - timeBefore));
		return true;
	}
	
}
