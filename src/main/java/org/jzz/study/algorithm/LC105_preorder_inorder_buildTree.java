package org.jzz.study.algorithm;

import java.util.HashMap;
import java.util.Map;

import org.jzz.study.DataStructure.BaseNode;
import org.jzz.study.util.Print;

/** 从前序与中序遍历序列构造二叉树 */
public class LC105_preorder_inorder_buildTree {
	Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	
	/**
	 * 对于任意一颗树而言，前序遍历的形式总是
		 [ 根节点, [左子树的前序遍历结果], [右子树的前序遍历结果] ]
	   即根节点总是前序遍历中的第一个节点。而中序遍历的形式总是
		 [ [左子树的中序遍历结果], 根节点, [右子树的中序遍历结果] ]
	   只要我们在中序遍历中定位到根节点，那么我们就可以分别知道左子树和右子树中的节点数目。
	   由于同一颗子树的前序遍历和中序遍历的长度显然是相同的，因此我们就可以对应到前序遍历的结果中，对上述形式中的所有左右括号进行定位。
	   这样以来，我们就知道了左子树的前序遍历和中序遍历结果，以及右子树的前序遍历和中序遍历结果，我们就可以递归地对构造出左子树和右子树，再将这两颗子树接到根节点的左右位置。
	 * 
	 * */
	public TreeNode buildTree(int[] preorder, int[] inorder, int pre_left, int pre_right, int in_left, int in_right) {
		if(pre_left > pre_right) {
			return null;
		}
		int pre_root = pre_left;
		int in_root = map.get(preorder[pre_root]);	//获取中序根节点下标
		
		TreeNode root = new TreeNode(preorder[pre_root]);
		int leftChildSize = in_root - in_left;
		root.left = buildTree(preorder, inorder, pre_left + 1, pre_left + leftChildSize, in_left, in_root - 1);
		root.right = buildTree(preorder, inorder, pre_left + leftChildSize +1, pre_right, in_root + 1, in_right);
		return root;
	}
	
	public TreeNode buildTree(int[] preorder, int[] inorder) {
		//使用哈希映射（HashMap）来帮助我们快速地定位根节点
		for (int i = 0; i < inorder.length; i++) {
			map.put(inorder[i], i);
		}
		return buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
	}
	
	public static void main(String[] args) {
		LC105_preorder_inorder_buildTree builder = new LC105_preorder_inorder_buildTree();
		int[] pre = {8,4,2,1,3,6,5,7,12,10,9,11,14,13,15};
		int[] in = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		TreeNode rooTreeNode = builder.buildTree(pre, in);
		Print.printTree(rooTreeNode);
	}
}
