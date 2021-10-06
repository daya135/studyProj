package org.jzz.studyProj;

public class TESTXX {
	static int getMax(int[] prices) {
		int max = 0;
		int low = prices[0];
		for (int i = 1; i < prices.length; i++) {
			if (low < prices[i]) {
				max = Math.max(max, prices[i] - low);
				continue;
			}
			low = prices[i];
		}
		
		return max;
	}
	
	
	public static void main(String[] args) {
		int[] prices = new int[] {1, 4, 2, 5, 7};
		int[] prices1 = new int[] {7, 4, 2, 5, 7};
		int[] prices2 = new int[] {3, 4, 2, 10, 7};
		int[] prices3 = new int[] {3, 2, 10, 1, 7};
		System.out.println(getMax(prices));
		System.out.println(getMax(prices1));
		System.out.println(getMax(prices2));
		System.out.println(getMax(prices3));
	}
}
