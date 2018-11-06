package org.jzz.study.DataStructure;

import java.util.LinkedList;


/**
 * 线索二叉树
 */
public class ThreadedBinaryTree<T> {
	public int length = 0;
	public Node head = null;
	
    class Node {
		public Node left = null;
		public Node right = null;
		public T data;
		public int lTag = 0; 
		public int rTag = 0;
		
		Node(T t) {
			this.data = t;
		}
    }
    
    public void InitTree(T[] arr) {
		if (arr == null) {
			return;
		}
		
		LinkedList<Node> nodeList = new LinkedList<Node>();
		
		for(T t : arr) {
			Node node = new Node(t);
			nodeList.add(node);
		}
		
		head = nodeList.getFirst();
		this.length ++;
		
		for (int i = 0; i < arr.length / 2; i++) {
			int l = i * 2 + 1;
			int r = i * 2 + 2;
			if (l < arr.length) {
				nodeList.get(i).left = nodeList.get(l);
				this.length ++;
			}
			if (r < arr.length) {
				nodeList.get(i).right = nodeList.get(r);
				this.length ++;
			}
		}
	}
    
    //第二种递归写法
    public void InOrder_recursion(Node node) {
    	if (node.left != null) {
    		InOrder_recursion(node.left);
    	} 
    	System.out.print(node.data + " ");
    	if (node.right != null) {
    		InOrder_recursion(node.right);
    	}
    }
    
    public void preOrder(Node node) {
    	Node p = node;
    	LinkedList<Node> listStack = new LinkedList<Node>();
    	while (p != null) {
    		System.out.print(p.data + " ");
    		if (p.right != null) {
    			listStack.push(p.right);
    		} 
    		if (p.left != null) {
    			p = p.left;
    		} else if(!listStack.isEmpty()){
				p = listStack.removeFirst();
			} else {
				break;
			}
    	}
    }
    
    public void inOrder(Node node) {
    	Node p = node;
    	LinkedList<Node> listStack = new LinkedList<Node>();
    	while (p != null || !listStack.isEmpty()) {
    		if (p != null) {
    			listStack.push(p);
    			p = p.left;
    		} else {
				p = listStack.removeFirst();
				System.out.print(p.data + " ");
				p = p.right;
			}
    	}
    }
    
    public static void main(String[] args) {
    	Integer a[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
//		Integer a[] = {4, 2, 5, 1, 3, 7, 6};
    	ThreadedBinaryTree<Integer> tree = new ThreadedBinaryTree<Integer>();
		tree.InitTree(a);
		System.out.println("InOrder_recursion");
		tree.InOrder_recursion(tree.head);
		System.out.println("\ntree.inOrder");
		tree.inOrder(tree.head);
		System.out.println("\npreOrder");
		tree.preOrder(tree.head);
	}
	
}
