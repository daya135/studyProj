package org.jzz.study.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.jzz.study.util.Print;

/**
 * 不同的二叉搜索树Ⅱ
 * 给定一个整数 n，生成所有由 1 ... n 为节点所组成的 二叉搜索树 。
 * 输入：3
 * 以上的输出对应以下 5 种不同结构的二叉搜索树：
   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
 * */
public class LC95_DifferentSearchTree_2 {
	public static List<TreeNode> generateTree(int start, int end) {
		List<TreeNode> allTrees = new ArrayList<TreeNode>();
		if (start > end) {
			allTrees.add(null);
			return allTrees;
		}
		for (int i = start; i <= end; i++) {
			List<TreeNode> leftTrees = generateTree(start, i - 1);
			List<TreeNode> rightTrees = generateTree(i + 1, end);
			
			for (TreeNode leftNode : leftTrees) {
				for (TreeNode rightNode : rightTrees) {
					TreeNode head = new TreeNode(i);
					head.left = leftNode;
					head.right = rightNode;
					allTrees.add(head);
				}
			}
		}
		return allTrees;
	}
	
	public static void main(String[] args) {
		List<TreeNode> treeList = generateTree(1, 4);
		for (TreeNode tree : treeList) {
			Print.printTree(tree);
			Print.print("==========================================");
		}
		
		
	}
}
