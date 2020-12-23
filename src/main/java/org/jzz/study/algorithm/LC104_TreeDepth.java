package org.jzz.study.algorithm;

import java.util.LinkedList;
import java.util.Queue;

import org.jzz.study.util.Print;


/** 二叉树最大深度 */
public class LC104_TreeDepth {
	
	/** 递归的思想查找深度！！ */
	public static int maxDepth_dfs(TreeNode root) {
		if (root == null) {
			return 0;
		}
		int leftDepth = maxDepth_dfs(root.left);
		int rightDepth = maxDepth_dfs(root.right);
		int depth = leftDepth > rightDepth ? leftDepth : rightDepth;
		return depth + 1;
	}
	
	/** 使用队列层序遍历，记录每个节点当前的深度*/
	 public static int maxDepth(TreeNode root) {
		 int depth = 0;
		 Queue<Pair<TreeNode, Integer>> queue = new LinkedList<Pair<TreeNode,Integer>>();
		 if (root == null) return depth;
		 queue.add(new Pair<TreeNode, Integer>(root, 1));
		 while (!queue.isEmpty()) {
			 Pair<TreeNode, Integer> pair = queue.poll();
			 if (pair.getKey() != null) {
				 depth = depth < pair.getValue() ? pair.getValue() : depth;
				 TreeNode node = pair.getKey();
				 queue.add(new Pair<TreeNode, Integer>(node.left, pair.getValue() + 1));
				 queue.add(new Pair<TreeNode, Integer>(node.right, pair.getValue() + 1));
			 }
		 }
		 return depth;
	 }
	 
	 public static void main(String[] args) {
		TreeNode root = TreeNode.createTree(new Integer[]{3,9,20,null,null,15,7});
		int depth = maxDepth(root);
		Print.print(depth);
		depth = maxDepth_dfs(root);
		Print.print(depth);
	 }
}

class Pair<T ,V> {
	private T key;
	private V value;
	
	public Pair(T t, V v) {
		this.setKey(t);
		this.setValue(v);
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}
	
}
