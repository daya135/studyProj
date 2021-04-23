package org.jzz.study.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.jzz.study.util.Print;

/** 
 * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 * 输入：nums = [1,2,3]
 * 输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
 * 
 * 标签：回溯法、位运算
 */
public class Subsets {
	List<List<Integer>> ansList = new ArrayList<>();
    List<Integer> t = new ArrayList<>();
    
    /** 位坐标法,用长度为nums.length=n的二进制序列表示是否选中， 共计[0, 2^n−1]种选择 */
    public List<List<Integer>> getSubsets1(int[] nums) {
    	int n = nums.length;
    	for (int mask = 0; mask < (1 << n); mask++) {
    		t.clear();
    		for (int i = 0; i < n; i++) {
    			if ((mask & (1 << i)) != 0) {
    				t.add(nums[i]); //选中此位
    			}
    		}
    		ansList.add(new ArrayList<>(t));
    	}
    	return ansList;
    }

    public List<List<Integer>> getSubsets2(int[] nums) {
        dfs(nums, 0);
        return ansList;
    }   
    
    /** dfs 回溯法, 太精彩了*/
    public void dfs(int[] nums, int cur) {
    	if (cur == nums.length) {
    		ansList.add(new ArrayList<>(t));
    		return;
    	}
    	t.add(nums[cur]);
    	dfs(nums, cur + 1);
    	t.remove(cur); //此处size()-1不能替换为cur， 想想为什么？因为用的是集合不是数组，不是针对指定下标置空，t为[null，null，3]时，想清空'3'，则不能用下标2！！
    	dfs(nums, cur + 1);
    }
    
   
    
    public static void main(String[] args) {
		Print.print(new Subsets().getSubsets1(new int[] {1, 2, 3, 4}));
		Print.print(new Subsets().getSubsets2(new int[] {1, 2}));
	}
}
