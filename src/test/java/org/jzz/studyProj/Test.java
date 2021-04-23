package org.jzz.studyProj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Map.Entry;

import org.jzz.study.algorithm.ListNode;
import org.jzz.study.algorithm.TreeNode;
import org.jzz.study.util.Print;

public class Test {
	
	public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            Integer count = map.getOrDefault(num, 0);
            count++;
            map.put(num, count);
        }
        List<Entry<Integer, Integer>> list = new ArrayList<>();
        for (Entry<Integer, Integer> entry : map.entrySet()) {
        	int i = 0;
            for (; i < list.size(); i++) {
                if (entry.getValue() > list.get(i).getValue()) {
                    break;
                }
            }
            if (i >= list.size()) {
            	list.add(entry);
            } else {
            	list.add(i, entry);
            }
        }
        List<Integer> ans = new ArrayList<>();
        int i = 0;
        int value = 0;
        for (; i < k; i++) {
        	ans.add(list.get(i).getKey());
        	value = list.get(i).getValue();
        }
        
        for (; i < list.size(); i++) {
        	if (list.get(i).getValue() == value) {
        		ans.add(list.get(i).getKey());
        	}
        }
      
        int[] ansArr = new int[ans.size()];
        for (i = 0; i < ans.size(); i++) {
            ansArr[i] = ans.get(i);
        } 
        return ansArr;
    }
   
	public static void main(String[] args) {
		Test test = new Test();
//		char[][] board = {{'A','B'},{'C','D'},};
//		char[][] board = {{'A','B','C','E'},{'S','F','E','S'},{'A','D','E','E'}};
		int[] nums = {4,1,-1,2,-1,2,3};
		Print.printArr(test.topKFrequent(nums, 2));
		
	}
}
