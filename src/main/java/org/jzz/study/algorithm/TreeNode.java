package org.jzz.study.algorithm;

import org.jzz.study.DataStructure.BaseNode;

/** leetcode treeNode 节点定义*/
public class TreeNode implements BaseNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    public TreeNode(int x) { val = x; }
	@Override
	public BaseNode getLeft() {
		return left;
	}
	@Override
	public BaseNode getRight() {
		return right;
	}
	@Override
	public BaseNode getParent() {
		return null;
	}
	@Override
	public String getValue() {
		return String.valueOf(val);
	}
	
    public static TreeNode createTree(Integer[] nums) {
    	 TreeNode[] nodes = new TreeNode[nums.length];
    	if (nums.length > 0 && nums[0] != null) {
    		nodes[0] = new TreeNode(nums[0]);
    	}
    	for (int i = 1; i < nums.length;i++) {
    		if (nums[i] != null) {
    			nodes[i] = new TreeNode(nums[i]);
    			int parent = (i - 1) / 2;
    			if (i % 2 == 0) {
    				nodes[parent].right = nodes[i];
    			} else {
    				nodes[parent].left = nodes[i];
    			}
    		}
    	}
    	return nodes[0];
    }
}
