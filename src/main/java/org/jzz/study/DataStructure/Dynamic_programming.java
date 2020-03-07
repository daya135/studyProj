package org.jzz.study.DataStructure;

import java.util.Arrays;

import org.jzz.study.util.Print;

public class Dynamic_programming {
	static int p[] = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
	static int MAX = p.length;
	
	public static int cut1(int []p, int n)
    {
        if(n == 0)
            return 0;
        int q = Integer.MIN_VALUE;
        for(int i = 1;i <= n; i++)
        {
            q = Math.max(q, p[i-1] + cut1(p, n-i));  
        }
        return q;
    }
	
	public static int cut2(int []p, int n, int r[]) {
		int q = -1;
		if (r[n] >= 0) {
			return r[n];
		}
		if (n == 0)
			q = 0;
		else 
			for (int i = 1; i <= n; i++)
				q = Math.max(q, cut2(p, n - i, r) + p[i - 1]);
		r[n] = q;
		return q;
	}
	
	public static void main(String[] args) {
		int R[] = new int[MAX + 1];
		Arrays.fill(R, -1);
//		int r = cut1(p, 7);
		int r = cut2(p, 7, R);
		Print.print(r);
	}
}
