package org.jzz.study.util;

import java.util.Comparator;

public class CompareUtil {
	/** String比较器  */
	public static class StringComparator implements Comparator<String> {

		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	}
	/** Integer比较器  */
	public static class IntegerComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			// TODO Auto-generated method stub
			return o1.compareTo(o2);
		}
	}
}
