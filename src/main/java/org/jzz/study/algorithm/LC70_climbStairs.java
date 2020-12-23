package org.jzz.study.algorithm;

import java.util.HashMap;
import java.util.Map;

import org.jzz.study.util.Print;

public class LC70_climbStairs {
	public static Map<Integer, Integer> map = new HashMap<>();
	 
	/** 递归， 保存计算数据，防止重复计算 */
	public static int climbStairs(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        int value1;
        int value2; 
        if (map.containsKey(n-2)) {
        	value1 = map.get(n - 2);
        } else {
			value1 = climbStairs(n - 2);
			map.put(n - 2, value1);
		}
        if (map.containsKey(n - 1)) {
        	value2 = map.get(n - 1);
        } else {
			value2 = climbStairs(n - 1);
			map.put(n - 1, value2);
		}
        return value1 + value2;
	}
	
	/** 迭代 */
	public static int climbStairs_2(int n) {
		int ans[] = new int[46]; //n = 46 会溢出
		ans[0] = 0;
		ans[1] = 1;
		ans[2] = 2;
		for (int i = 3; i <= n; i++) {
			ans[i] = ans[i - 1] + ans [i - 2];	//斐波拉切亚数列
		}
		return ans[n];
	}
	
	public static void main(String[] args) {
		
		Print.print(climbStairs_2(45));
	}
}
