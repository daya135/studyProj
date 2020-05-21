package org.jzz.study.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jzz.study.util.Print;

public class ArraysTest {
	public static void main(String[] args) {
		int[] ints = {1,2,3,4};
		List intList = (List) Arrays.asList(ints);	//基本类型数组，实际此处是List<int[]>
		Print.print(intList.size());
		
		String[] strs = {"a","b","c"};
		List strList = (List)Arrays.asList(strs);	//引用类型数组，转化为List<String>
		Print.print(strList.size());
		
		try {
			strList.remove(0); //只读容器：java.util.Arrays.ArrayList，调用修改方法是调用其父类AbstractList的方法，会抛出UnsupportedOperationException 
		} catch (Exception e) {
			e.printStackTrace();
		}
		strList = new ArrayList<String>(strList);	//转化为可修改的容器
		strList.add("d");
		Print.print(strList);
		
		Arrays.fill(strs, "abc");	//填充
		Print.printArr(strs, " ");
		
		String[] arr = Arrays.copyOf(strs, 3);	//Arrays.copyOf
		Print.printArr(arr, " ");
		arr = new String[10];
		System.arraycopy(strs, 0, arr, 3, 3);	//System.arraycopy , 非常好用
		Print.printArr(arr, " ");
	}
}
