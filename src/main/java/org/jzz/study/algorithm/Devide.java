package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

/** 
 * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除
 * 要求不使用乘法、除法和mod运算符!!!!
 * */
public class Devide {
    public static int divide(int dividend, int divisor) {
    	if (dividend == 0) return 0;
        if (1 == divisor) return dividend;
        if (-1 == divisor) {
        	if (dividend == Integer.MIN_VALUE) {		//负数最大整数除以-1会溢出
        		return Integer.MAX_VALUE;
        	}
        	return -dividend;
        }
        int sign;
        if ((dividend >0  && divisor > 0) || (dividend < 0 && divisor < 0)) {
        	sign = 1;
        } else {
        	sign = -1;
        }
        long a = dividend;
        long b = divisor;
        a = a < 0 ? -a : a;
        b = b < 0 ? -b : b;
        long res = divide(a, b);
        if (sign < 0) {
        	res = -res;
        } else if (res > Integer.MAX_VALUE) { 
        	res = Integer.MAX_VALUE;
        }
        return (int)res;
        
    }
    
    public static long divide(long a, long b) {
    	if (a < b) return 0;
    	long count = 1;
    	long temp = b; 
    	while ((temp + temp) <= a){
    		count = count + count;
    		temp = temp + temp;
    	}
    	return count + divide(a - temp, b);
    }
    
    public static void main(String[] args) {
		Print.print(divide(Integer.MIN_VALUE, -1));
		Print.print(divide(Integer.MIN_VALUE, 1));
		Print.print(divide(Integer.MAX_VALUE, 1));
		Print.print(divide(Integer.MAX_VALUE, -1));
		Print.print(divide(0, 1));
		Print.print(divide(1, -1));
		Print.print(divide(1, 1));
		Print.print(divide(10, 3));
	}
}
