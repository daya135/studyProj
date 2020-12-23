package org.jzz.studyProj;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jzz.study.algorithm.TreeNode;
import org.jzz.study.util.Print;

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
	
	
}
