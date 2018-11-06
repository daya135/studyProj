package org.jzz.study.DataStructure;

import java.util.LinkedList;


/**
 * 线索二叉树
 */
public class ThreadedBinaryTree<T> {
	public int length = 0;
	public Node<T> head = null;
	
	static class  Node<T> {
		public Node<T> left = null;
		public Node<T> right = null;
		public T data;
		public int lTag = 0; 
		public int rTag = 0;
		
		Node() {}
		
		Node(T t) {
			this.data = t;
		}
    }
    
    public void InitTree(T[] arr) {
		if (arr == null) {
			return;
		}
		
		LinkedList<Node<T>> nodeList = new LinkedList<Node<T>>();
		
		for(T t : arr) {
			Node<T> node = new Node<T>(t);
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
    public void InOrder_recursion(Node<T> node) {
    	if (node.left != null) {
    		InOrder_recursion(node.left);
    	} 
    	System.out.print(node.data + " ");
    	if (node.right != null) {
    		InOrder_recursion(node.right);
    	}
    }
    
    public void preOrder(Node<T> node) {
    	Node<T> p = node;
    	LinkedList<Node<T>> listStack = new LinkedList<Node<T>>();
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
				p = null;
			}
    	}
    }
    
    /**
     * 中序线索化
     * 一旦完成线索化则不能再用常规方法遍历二叉树了，因为叶子节点的左右指针不为空了
     * @return first 头节点
     * @param head 根节点
     * */
    public Node<T> inOrder(Node<T> head) {
    	Node<T> first = new Node<T>();	//头节点，注意不是根节点！！！
    	Node<T> p = head;
    	Node<T> pre = null;
    	int firstFlag = 0;
    	LinkedList<Node<T>> listStack = new LinkedList<Node<T>>();
    	
    	while (p != null || !listStack.isEmpty()) {
    		if (p != null) {
    			listStack.push(p);
    			p = p.left;
    		} else {
				p = listStack.removeFirst();
				if (p.left == null) {
					p.lTag = 1;
				}
				if (p.right == null) {
					p.rTag = 1;
				}
				if (firstFlag == 0) {
					p.left = first;
					firstFlag = 1;
				}
				System.out.print(p.data + " ");
				if (pre != null) {
					if (pre.rTag == 1) {
						pre.right = p;
					}
					if (p.lTag == 1) {
						p.left = pre;
					}
				} 
				pre = p;
				p = p.right;
			}
    	}
    	first.left = head;
    	pre.right = first;
    	return first;
    }
    
    public void inOrderTraverse(Node<T> first) {
    	Node<T> p = first.left;	//头节点的左子树指向根节点
    	while (p != first) {
    		while (p.lTag == 0) {
    			p = p.left;
    		}
    		System.out.print(p.data + " ");
    		while (p.rTag == 1 && p.right != first) {
    			if (p.right != null) {
    				p = p.right;
    				System.out.print(p.data + " ");
    			} else {
    				return;   //访问到最后一个节点
    			}
    		}
    		p = p.right;
    	}
    }
    
    public static void test() {
    	Integer a[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
//		Integer a[] = {4, 2, 5, 1, 3, 7, 6};
    	ThreadedBinaryTree<Integer> tree = new ThreadedBinaryTree<Integer>();
		tree.InitTree(a);
		System.out.println("preOrder");
		tree.preOrder(tree.head);
//		System.out.println("\nInOrder_recursion");
//		tree.InOrder_recursion(tree.head);
		System.out.println("\ninOrder");
		Node<Integer> first = tree.inOrder(tree.head);
		System.out.println("\nfirst: " + first.data);
		tree.inOrderTraverse(first);
	}
    
    public static void main(String[] args) {
    	test();
		
	}
	
}
