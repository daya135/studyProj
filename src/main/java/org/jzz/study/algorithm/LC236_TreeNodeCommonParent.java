package org.jzz.study.algorithm;

import java.util.HashMap;
import java.util.Map;

import org.jzz.study.DataStructure.BaseNode;
import org.jzz.study.util.Print;

final class LC236_TreeNodeCommonParent {
    static TreeNode[] nodes;
    public static TreeNode createTree(Integer[] nums) {
    	nodes = new TreeNode[nums.length];
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
    
    static TreeNode ans;
    /** 方法一：深搜 */
    public static boolean dfs(TreeNode node, TreeNode p, TreeNode q) {
    	if (node == null) return false;
    	boolean lson = dfs(node.left, p, q);
    	boolean rson = dfs(node.right, p, q);
    	if ((lson && rson) || ((node.val == p.val || node.right == q.right) && (lson || rson))) {
    		ans = node;
    	}
    	return lson || rson || node.val == p.val || node.val == q.val;
    }
	/** 通过深搜实现 */ 
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
    	return ans;
    }
    
    static Map<Integer, TreeNode> parentMap = new HashMap<Integer, TreeNode>();
    /** 记录节点的parent */
    public static void dfs_2(TreeNode node) {
    	if (node.left != null) {
    		parentMap.put(node.left.val, node);
    		dfs_2(node.left);
    	}
    	if (node.right != null) {
    		parentMap.put(node.right.val, node);
    		dfs_2(node.right);
    	}
    }
    static Map<Integer, Boolean> vistMap = new HashMap<Integer, Boolean>();
    /** 方法二：通过记录父节点，查找祖先节点的重合位置 */
    public static TreeNode lowestCommonAncestor_2(TreeNode root, TreeNode p, TreeNode q) {
        dfs_2(root);
        parentMap.put(root.val, null);
        while (	p != null) {
        	vistMap.put(p.val, true);
        	p = parentMap.get(p.val);
        }
        while(q != null) {
        	if (vistMap.get(q.val) != null) return q;
        	q = parentMap.get(q.val);
        }
    	return null;
    }
    
    
    static TreeNode ans_searchTree = null;
    static void dfs3(TreeNode node,  TreeNode min, TreeNode max) {
    	if (node == null) return;
    	if (node.val >= min.val && node.val <= max.val) {
    		ans_searchTree = node;
    		return;
    	}
    	if (node.val > max.val) {
    		dfs3(node.left, min, max);
    	} else {
    		dfs3(node, min, max);
    	}
    }
    /** 二叉排序树查找公共祖先 easy*/
    public static TreeNode lowestCommonAncestor_searchTree(TreeNode root, TreeNode p, TreeNode q) {
    	TreeNode max = p.val > q.val ? p : q;
    	TreeNode min = p.val < q.val ? p : q;
    	dfs3(root, min, max);
    	return ans_searchTree;
    }
    
	
	public static void main(String[] args) {
		TreeNode root = createTree(new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14});
		Print.printTree(root);
		TreeNode parent = lowestCommonAncestor(root, nodes[8], nodes[10]);
		TreeNode parent_2 = lowestCommonAncestor_2(root, nodes[8], nodes[10]);
		Print.printTree(parent);
		Print.printTree(parent_2);
		
		
		root = createTree(new Integer[]{8,4,12,2,6,10,14,1,3,5,7,9,11,13,15});
		TreeNode parent_3 = lowestCommonAncestor_searchTree(root, nodes[8], nodes[11]);
		Print.printTree(parent_3);
	}
}
