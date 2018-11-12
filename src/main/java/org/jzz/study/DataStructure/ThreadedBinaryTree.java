package org.jzz.study.DataStructure;

import java.util.LinkedList;

/**
 * 线索二叉树
 */
public class ThreadedBinaryTree<T> {
	public int length = 0;
	public Node<T> root = null;
	
	static class  Node<T> {
		public Node<T> left = null;
		public Node<T> right = null;
		public Node<T> parent = null; //后序遍历线索二叉树需要parent指针支持，前中序不需要
		public T data;
		public int lTag = 0; 
		public int rTag = 0;
		
		Node() {}
		
		Node(T t) {
			this.data = t;
		}
    }
    
	/** 建树，个人烂代码，只能建完全二叉树 */
    public void InitTree(T[] arr) {
		if (arr == null) {
			return;
		}
		
		LinkedList<Node<T>> nodeList = new LinkedList<Node<T>>();
		
		for(T t : arr) {
			Node<T> node = new Node<T>(t);
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
    
    public void createTree(Node<T> t, Node<T> p, T[] arr) {
    	if (arr[length++] == null) {
    		t = null;
    	} else {
    		t = new Node<T>(arr[length - 1]);
    		t.parent = p;
    		System.out.print(t.data + " ");
    		createTree(t.left, t, arr);
    		createTree(t.right, t, arr);
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
     * */
    public Node<T> inOrder() {
    	Node<T> first = new Node<T>();	//头节点，注意不是根节点！！！
    	Node<T> p = this.root;
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
    	first.left = root;
    	pre.right = first;
    	return first;
    }
    
    /** 中序遍历线索二叉树 */
    public void inOrderTraverse(Node<T> head) {
    	Node<T> p = head.left;	//头节点的左子树指向根节点
    	while (p != head) {
    		while (p.lTag == 0) {
    			p = p.left;
    		}
    		System.out.print(p.data + " ");
    		while (p.rTag == 1 && p.right != head) {
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
    
    /** 先序建立线索二叉树 */
    public Node<T> PreBuild() {
    	LinkedList<Node<T>> stack = new LinkedList<Node<T>>();
    	Node<T> p = this.root;
    	Node<T> pre = null;
    	Node<T> head = new Node<T>();
    	while(p != null || !stack.isEmpty()) {
    		while (p!= null) {
    			System.out.print(p.data + " ");
    			if (p.left == null) { 
    				p.lTag = 1;
    				p.left = pre;	//前序遍历过程中，进入此分支时pre必然不为空（即使根节点没左子树这么写也不会错）
    			}
    			if (pre != null && pre.right == null) {
    				pre.right = p;
    				pre.rTag = 1;	
    			}
//    			if (p.right == null) {
//    				p.rTag = 1;	//这一句可以放到上一个分支中！
//    			}
    			pre = p;
    			if (p.lTag == 0) {	//因为left指向了前驱，需要判断当前左子树是否为空
    				stack.push(p);
        			p = p.left;
    			} else {
					p = null;   
				}
    			
    		}
    		
    		if (!stack.isEmpty()) {
    			p = stack.removeFirst();
    			p = p.right;
    		}
    	}
    	head.left = root;
    	return head;
    }
    
    /** 先序遍历线索二叉树 */
    public void PreTraverse(Node<T> head) {
    	Node<T> p = head.left;
    	while (p != null) {
    		System.out.print(p.data + " ");
    		if (p.rTag == 1) {	//若RTag 的值为1，那么RChild 所指结点就是直接后继
    			p = p.right;
    		} else {
    			if (p.lTag == 0) {	
    				p = p.left;		//若LTag 的值为0，那么直接后继就是其左儿子。
    			} else {
    				p = p.right;	//若LTag 的值为1，那么直接后继就是其右儿子。
				}
    		}
    		//以上代码可以化简，即当LTag的值为1时，后继肯定为右孩子
    	}
    }
    
    public Node<T> PostBuild () {
    	LinkedList<Node<T>> stack = new LinkedList<Node<T>>();
    	Node<T> p = this.root;
    	Node<T> pre = null;
    	Node<T> head = new Node<T>();
    	stack.push(p); 
    	while(!stack.isEmpty()) {
    		p = stack.getFirst();
    		if ((p.left == null && p.right == null) || 
    				((pre != null) && (p.right == pre || pre.left == pre))) {
    			if (p.left == null) {
    				p.lTag = 1;
    				if (pre != null) {
        				p.left = pre;
        			}
    			} 
    			if (pre!= null && pre.right == null) {
    				pre.rTag = 1;
    				pre.right = p;
    			}
    			System.out.print(p.data + " ");
    			stack.removeFirst();
    			pre = p;
    		} else {
    			if (p.right != null) {
        			stack.push(p.right);
        		}
        		if (p.left != null) {
        			stack.push(p.left);
        		}
    		}
    	}
    	return head;
    }
    
    public void PostTraverse(Node<T> head) {
    	Node<T> p = head.left;
    	Node<T> parent = null;
    	while (p != null) {
    		
    	}
    }
    
    
    public static void test() {
    	Integer a[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
//		Integer a[] = {4, 2, 5, 1, 3, 7, 6};
    	ThreadedBinaryTree<Integer> tree = new ThreadedBinaryTree<Integer>();
		tree.InitTree(a);
		tree.createTree(null, tree.root, a);
		
//		System.out.println("preOrder");
//		tree.preOrder(tree.root);
//		System.out.println("\nInOrder_recursion");
//		tree.InOrder_recursion(tree.root);
		
//		System.out.println("\ninOrder");
//		Node<Integer> head = tree.inOrder();
//		System.out.println("\nfirst: " + head.data);
//		tree.inOrderTraverse(head);
		
//		System.out.println("\nPrebuild:");
//		Node<Integer> head = tree.PreBuild();
//		System.out.println("\nPreTraverse:");
//		tree.PreTraverse(head);
		
//		System.out.println("\nPostbuild:");
//		Node<Integer> head = tree.PostBuild();
//		System.out.println("\nPostTraverse:");
//		tree.PostTraverse(head);
		
		
	}
    
    public static void main(String[] args) {
    	test();
		
	}
	
}
