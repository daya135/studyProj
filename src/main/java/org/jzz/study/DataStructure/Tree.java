package org.jzz.study.DataStructure;

import java.util.LinkedList;
import org.jzz.study.util.Print;


public class Tree<T> {
	
	public int length = 0;
	public Node root = null;
	
    class Node {
		public Node left = null;
		public Node right = null;
		public T data;
		
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
		
		root = nodeList.getFirst();
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
	
	//前序遍历，递归算法
	public void PreOrder_recursion(Node p) {
		if (p != null) {
			System.out.print(p.data + " ");
			PreOrder_recursion(p.left);
			PreOrder_recursion(p.right);
		}
	}
	//后序遍历，递归算法
	public void PostOrder_recursion(Node p) {
		if (p != null) {
			PostOrder_recursion(p.left);
			PostOrder_recursion(p.right);
			System.out.print(p.data + " ");
		}
	}
	//中序遍历，递归算法
	public void InOrder_recursion(Node p) {
		if (p != null) {
			InOrder_recursion(p.left);
			System.out.print(p.data + " ");
			InOrder_recursion(p.right);
		}
	}
	/** 前序遍历,非递归算法1 自己的烂代码*/
	public void PreOrder(Node root) {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = root;
		while (p != null) {
			System.out.print(p.data + " ");
			if (p.right != null) {
				listStack.push(p.right);
			} 
			if (p.left != null) {
				p = p.left;
			} else {
				if (!listStack.isEmpty()) {
					p = listStack.removeFirst();
				} else {
					break;
				}
			}
		}
	}
	/** 前序遍历,非递归算法2 网上的优美代码 */
	public void PreOrder2(Node root) {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = root;
		while (p != null || !listStack.isEmpty()) {
			if (p != null) {
				System.out.print(p.data + " ");
				listStack.push(p);
				p = p.left;
			} else {
				p = listStack.removeFirst();
				p = p.right;
			}
		}
	}
	
	/** 中序遍历，非递归 */
	public void InOrder(Node root) {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = root;
		while (p != null || !listStack.isEmpty()) {
			while (p != null) {
				listStack.push(p);
				p = p.left;
			}
			if(!listStack.isEmpty()){
				p = listStack.removeFirst();
				System.out.print(p.data + " ");
				p = p.right;
			}
		}
	}
	/** 后序遍历，非递归，访问标志法 */
	public void PostOrder(Node root) {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = root;
		Node last = p;
		listStack.push(p);
		while (!listStack.isEmpty()) {
			p = listStack.getFirst();
			if ((p.left == null && p.right == null) ||
				(last != null) && (p.right == last && p.left == last)) {  //入栈顺序决定了不可能出现right不为空且last==left的情况，
				System.out.print(p.data + " ");
				last = p;
				listStack.removeFirst();
			} else {
				if (p.right != null) {
					listStack.push(p.right);
				} 
				if (p.left != null) {
					listStack.push(p.left);
				}
			}
		}
	}
	
	/** 后序遍历，非递归，双栈法 */
	public void PostOrder2(Node root) {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = root;
		listStack.push(p);
		listStack.push(p);
		while (!listStack.isEmpty()) {
			p = listStack.removeFirst();
			if (!listStack.isEmpty() && p == listStack.getFirst()) {
				if (p.right != null) {
					listStack.push(p.right);
					listStack.push(p.right);
				}  
				if (p.left != null) {
					listStack.push(p.left);
					listStack.push(p.left);
				}
			} else {
				System.out.print(p.data + " ");
			}
		}
	}
	
	public void layerOrder() {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = this.root;
		listStack.push(p);
		while(!listStack.isEmpty()) {
			p = listStack.removeLast();
			System.out.print(p.data + " ");
			if (p.left != null) {
				listStack.push(p.left);
			}
			if (p.right != null) {
				listStack.push(p.right);
			}
		}
	}
	
	public void testOrder() {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = this.root;
		Node pre = null;
		listStack.push(p);
		while(!listStack.isEmpty()) {
			p = listStack.getFirst();
			if ((p.left == null && p.right == null) ||
					(pre != null &&(p.left == pre || p.right == pre))) {
				System.out.print(p.data + " ");
				pre = p;
				listStack.removeFirst();
			} else {
				if (p.right != null)
					listStack.push(p.right);
				if (p.left != null) 
					listStack.push(p.left);
			}
		}
	}
	
	
	public static void main(String[] args) {
		Integer a[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
//		Integer a[] = {4, 2, 5, 1, 3, 7, 6};
		Tree<Integer> tree = new Tree<Integer>();
		tree.InitTree(a);
		System.out.println("layerOrder");
		tree.layerOrder();
		System.out.println("\nPreOrder_recursion");
		tree.PreOrder_recursion(tree.root);
		System.out.println("\nPostOrder_recursion");
		tree.PostOrder_recursion(tree.root);
		System.out.println("\nInOrder_recursion");
		tree.InOrder_recursion(tree.root);
		System.out.println("\nPreOrder");
		tree.PreOrder(tree.root);
		System.out.println();
		tree.PreOrder2(tree.root);
		System.out.println("\nPostOrder");
//		tree.PostOrder(tree.root);
		tree.PostOrder2(tree.root);
		System.out.println("\nInOrder");
		tree.InOrder(tree.root);
		System.out.println("\ntestOrder");
		tree.testOrder();
		
	}
	
}
