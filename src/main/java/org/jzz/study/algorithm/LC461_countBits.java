package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

/** 统计二进制数中1的数量 */
public class LC461_countBits {
	//2B版本
	public static int countBits(int x) {
		char[] bits = Integer.toBinaryString(x).toCharArray();
		int count = 0;
		for(int i = 0; i < bits.length;i++) {
			if (bits[i] == '1')
				count++;
		}
		return count;
	}
	//内置功能
	public static int countBits_1 (int x) {
		return Integer.bitCount(x);
	}
	//移位判断末尾
	public static int countBits_2 (int x) {
		int count = 0;
		while (x != 0) {
			if ((x & 1) == 1) count++;
			x = x >>> 1;
		}
		return count;
	}
	//布赖恩·克尼根算法
	public static int countBits_3 (int x) {
		int count = 0;
		while (x != 0) {
			count ++;
			x = x & (x - 1);	//清除最低位的1，绝了。。
		}
		return count;
	}
	
	public static void main(String[] args) {
		int x = 15;
		Print.print(countBits(x));
		Print.print(countBits_1(x));
		Print.print(countBits_2(x));
		Print.print(countBits_3(x));
	}
}
