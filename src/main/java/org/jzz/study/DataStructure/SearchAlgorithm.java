package org.jzz.study.DataStructure;

public class SearchAlgorithm {
	
	/* 折半查找，非递归模式 ，需要从小到大排好序 */
	public static <T extends Comparable<T>>  int binarySearch(T[] x, T key){
		int low = 0;
		int high = x.length -1;
		while (low <= high) {
			int mid = (low + high) >>> 1;
			int cmp = x[mid].compareTo(key);
			if (cmp < 0) {
				low = mid + 1;
			} else if (cmp > 0) {
				high = mid - 1;
			} else {
				return mid;
			}
		}	
		return -1;
	}
	
	public static void main(String[] args) {
		Integer[] ints = new Integer[]{1,2,3,4,5,6,7,9}; 
		System.out.println("index: " + binarySearch(ints, 1)); //不能将自动包装用于数组，所以不能传入基本类型数组
	}
}
