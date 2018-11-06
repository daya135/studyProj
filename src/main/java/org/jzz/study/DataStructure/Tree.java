package org.jzz.study.DataStructure;

import java.util.LinkedList;
import org.jzz.study.util.Print;


public class Tree<T> {
	
	public int length = 0;
	public Node head = null;
	
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
	
	public void PreOrder(Node head) {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = head;
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
	
	public void InOrder(Node head) {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = head;
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
	
	public void PostOrder(Node head) {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = head;
		Node last = p;
		listStack.push(p);
		while (!listStack.isEmpty()) {
			p = listStack.getFirst();
			if ((p.left == null && p.right == null)
				|| (p.right == null && last == p.left) || (last == p.right)) {
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
	

	public void PostOrder2(Node head) {
		LinkedList<Node> listStack = new LinkedList<Node>();
		Node p = head;
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
	
	
	public static void main(String[] args) {
		Integer a[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
//		Integer a[] = {4, 2, 5, 1, 3, 7, 6};
		Tree<Integer> tree = new Tree<Integer>();
		tree.InitTree(a);
		tree.PreOrder_recursion(tree.head);
		System.out.println();
		tree.PostOrder_recursion(tree.head);
		System.out.println();
		tree.InOrder_recursion(tree.head);
		System.out.println();
		tree.PreOrder(tree.head);
		System.out.println();
//		tree.PostOrder(tree.head);
		tree.PostOrder2(tree.head);
		System.out.println();
		tree.InOrder(tree.head);
		
		
	}
	
}
