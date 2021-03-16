package org.jzz.studyProj;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jzz.study.algorithm.TreeNode;
import org.jzz.study.util.Print;
import org.springframework.core.SimpleAliasRegistry;

public class Test {
	
	static public List<List<Integer>> subsets(int[] nums) {
	    List<List<Integer>> list = new ArrayList<List<Integer>>();
	    for (int i = 0; i < nums.length; i++) {
	    	
	    }
	    return list;
	}
	
	static void subsets(int nums[], int n, boolean flag) {
		if (n >= nums.length) return;
		subsets(nums, n + 1, true);
		subsets(nums, n + 1, false);
	}
	
	static void reversePrint(int[] a, int begin) {
		if (begin < a.length - 1) {
			reversePrint(a, begin + 1);
		}
		Print.print(a[begin]);
	}
	   static public double myPow(double x, int n) {
	        if (n > 0)
	        	return pow(x, n);
	        else
	            return (1.0 / pow(x, n * -1));
	    }

	   static public double pow(double x, int n) {
		   if (n == 0) return 1.0;
		   double y = pow(x, n / 2);
		   return n % 2 == 0 ? y * y : y * y * x;
	   }
	   
	   
	public static boolean isPalindrome(String s) {
        if (s == null || s.length() == 0) return true;
        int l = 0;
        int r = s.length() - 1;
        while (l <= r) {
            int lc = Integer.valueOf(s.charAt(l));
            int rc = Integer.valueOf(s.charAt(r));
            lc = lc > 96 ? lc - 32 : lc;
            rc = rc > 96 ? rc - 32 : rc;
            if (lc < 48 || (lc > 57 && lc < 65) || lc > 90 ) {
                l ++;
                continue;
            } 
            if (rc < 65 || (rc > 57 && rc < 65) || rc > 90) {
                r --;
                continue;
            }
            if (rc != lc) return false;
            l ++;
            r --;
        }
        return true;
	}
	
    public static String countAndSay(int n) {
        if (n == 1) {
        	return "1";
        }
        String str = countAndSay(n - 1);
        int start = 0;
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 1; i < str.length(); i++) {
        	if (str.charAt(i) == str.charAt(start)) {
        		continue;
        	} else {
				sBuffer.append(i - start).append(str.charAt(start));
				start = i;
			}
        }
        sBuffer.append(str.length() - start).append(str.charAt(start));
        
        return sBuffer.toString();
    }
   
	public static void main(String[] args) {
		String str = countAndSay(4);
		Print.print(str);
	}
}
