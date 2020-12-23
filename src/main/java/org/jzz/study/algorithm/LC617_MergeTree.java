package org.jzz.study.algorithm;

import java.util.Stack;

import org.jzz.study.util.Print;

/** 合并二叉树 */
public class LC617_MergeTree {
	/** 递归法，令人头大。。 */
	public static TreeNode merge(TreeNode t1, TreeNode t2) {
		if (t1 == null) return t2;
		if (t2 == null) return t1;
		t1.val += t2.val;
		t1.left =  merge(t1.left, t2.left);
		t1.right = merge(t1.right, t2.right);
		return t1;
	}
	
	/** 迭代法,重点是抓住返回树以t1为主！ */
	public static TreeNode merge_2(TreeNode t1, TreeNode t2) {
		if (t1 == null) return t2;
		Stack<TreeNode[]> stack = new Stack<>();
		stack.push(new TreeNode[] {t1, t2});
		while (!stack.isEmpty()) {
			TreeNode[] t = stack.pop();
			if (t[0] == null || t[1] == null) {	//t[0]==null && t[1]!=null的情况不可能出现，因为后面有t[0].left|right=t[1].left|right;t[0]!=null & t[1]==null,直接continue也是没问题的，因为返回的就是t[0]
				continue;
			}
			t[0].val += t[1].val;
			if (t[0].left == null) { //因为最后返回的就是t[0]，所以只用判断t0.left，t1.left==null是不用考虑的
				t[0].left = t[1].left;
			} else {
				stack.push(new TreeNode[] {t[0].left, t[1].left});
			}
			if (t[0].right == null) { 
				t[0].right = t[1].right;
			} else {
				stack.push(new TreeNode[] {t[0].right, t[1].right});
			}
		}
		return t1;
	}
	
	public static void main(String[] args) {
		TreeNode t1 = TreeNode.createTree(new Integer[] {1,2,3,4});
		TreeNode t2 = TreeNode.createTree(new Integer[] {1,2,3,null,5,null,7});
		Print.printTree(t1);
		Print.printTree(t2);
		TreeNode root = merge(t1, t2);
//		TreeNode root = merge_2(t1, t2);
		Print.printTree(root);
	}
}
