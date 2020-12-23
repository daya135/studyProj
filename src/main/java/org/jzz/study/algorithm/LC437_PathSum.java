package org.jzz.study.algorithm;

import org.jzz.study.util.Print;

/*
给定一个二叉树，它的每个结点都存放着一个整数值。
找出路径和等于给定数值的路径总数。
路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
 	  10
 	 /  \
    5   -3
   / \    \
  3   2   11
 / \   \
3  -2   1

返回3
*/
public class LC437_PathSum {
	
	//法一：双重递归
	static int pathSum_1(TreeNode node, int sum) {	 //统计整根树，任意一个节点为起点的路径数
		if(node == null) return 0;
		int result = count(node, sum);
		int a = pathSum_1(node.left, sum);
		int b = pathSum_1(node.right, sum);
		return result + a + b;
	}	
	static int count(TreeNode node, int sum) {		//统计以当前节点为起点的路径数
		if (node == null) return 0;
		sum = sum - node.val;
		int result  = sum == 0 ? 1 : 0;
		return result + count(node.left, sum) + count(node.right, sum);
	}
	
	//法二：一次递归
	static int pathSum_2(TreeNode node, int sum, int[] array, int p) {
		if (node == null) return 0;
		int tmp = node.val;
		int n = node.val == sum ? 1 : 0;
		for (int i = p - 1; i >= 0; i--) {	//统计以当前节点为终点，到前面节点之和等于sum的路径数
			tmp += array[i];
			if (tmp == sum) 
				n++;
		}
		array[p] = node.val;	//将当前节点加入到路径(根据深搜的原理,每次都只计算一个分支，所以数据安全）
		int a = pathSum_2(node.left, sum, array, p + 1);
		int b = pathSum_2(node.right, sum, array, p + 1);
		return n + a + b;
	}
	
	public static void main(String[] args) {
		TreeNode tree = TreeNode.createTree(new Integer[]{10,5,-3,3,2,null,11,3,-2,null,1});
		Print.print(pathSum_1(tree, 8));
		Print.print(pathSum_2(tree, 8, new int[1000], 0));
	}
}
