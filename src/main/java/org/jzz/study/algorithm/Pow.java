package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

/** 	
 	实现 pow(x, n) ，即计算 x 的 n 次幂函数（即，x^n）。
	-100.0 < x < 100.0
	-231 <= n <= 231-1
	-104 <= xn <= 104
 * */
public class Pow {
	
	static double myPow(double x, int n) {
		return n > 0 ? pow(x, n) : 1.0 / pow(x, -n);
	}
	
	static double pow(double x, int n) {
		if (n == 0) return 1;
		double num = pow(x, n / 2);
		return n % 2 == 0 ? num * num : num * num * x;
	}
	
	public static void main(String[] args) {
		Print.print(myPow(2, 5));
		Print.print(myPow(2, 0));
		Print.print(myPow(2, -5));
		Print.print(myPow(-2, 5));
	}
}
