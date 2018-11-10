package org.jzz.study.DataStructure;

import org.jzz.study.util.Print;

public class HuffmanTree {
	
	/** 双亲表示法 */
	static class Node {
		public char ch;
		public int weight;
		public int parent;
		public int lchild, rchild;
		public String toString() {
			return "{" + ch + " " + weight + " " + parent + "} ";
		}
	}
	int MAXLEAFNUM = 16;
	Node[] nodes = new Node[2 * MAXLEAFNUM];	//声明对象数组后 ，必须对每个数组成员进行实例化话 才能直接使用，否则报 空指针异常！
	char[] huffmanCode = new char[MAXLEAFNUM + 1];
	
	public void display() {
		for (int i = 0; i < this.nodes.length; i++) {
			System.out.print(i + this.nodes[i].toString());
		}
		System.out.println();
	}
	
	public void createTree(char[] c, int[] w, int n) {
//		for (Node node : this.nodes) {
//			node = new Node(); //这么初始化有大问题!!!增强for是只读的!!!即无法改变nodes数组的值!!
//		}
		for (int i = 0; i < this.nodes.length; i++) {
			this.nodes[i] = new Node();		//这样初始化才行
		}
		int i =  1; 
		for (; i <= n; i++) {
			Node node = this.nodes[i];
			this.nodes[i].ch = c[i-1];
			this.nodes[i].weight = w[i-1];
			this.nodes[i].parent = this.nodes[i].lchild = this.nodes[i].rchild = 0;
		}
		for(; i < 2 * n; ++i) {
			this.nodes[i].parent = this.nodes[i].lchild = this.nodes[i].rchild = 0;
		}
		display();
		for (i = n + 1; i < 2 * n; i++) {
			int[] s = select(i - 1);
			int s1 = s[0];
			int s2 = s[1];
			this.nodes[s1].parent = i;
			this.nodes[s2].parent = i;
			this.nodes[i].lchild = s1;
			this.nodes[i].rchild = s2;
			this.nodes[i].weight = this.nodes[s1].weight + this.nodes[s2].weight;
			Print.print(s1 + nodes[s1].toString() + " " + s2 + nodes[s2].toString());
		}
		display();
		Print.print("建树完成, 根节点:" +  (i - 1));
		Print.print(nodes[i - 1]);
		
	}
	
	public int[] select(int n) {
		int[] s = {-1, -1};
		int min = Integer.MAX_VALUE;
		for (int i = 1; i <= n; i++) {
			if (nodes[i].parent != 0) {
				continue;
			}
			if (nodes[i].weight < min) {	//最小
				s[1] = s[0];	//把原最小值下标给s1
				s[0] = i;	//把新的最小字给s0
				min = nodes[i].weight;
			} 
			if (s[1] != -1) {
				if (s[0] != i && nodes[i].weight < nodes[s[1]].weight) {
					s[1] = i;	//如果次小值在最小值后面, 则进入这段代码
				}
			} else if(s[0] != i){
				s[1] = i;	//只有两位且最小值在前面时进入这句
			}
		}
		if(s[1] == -1){
			s[1] = s[0];	//只有一位时进到这里
		}
		return s;
	}
	
	
	
	public static void main(String[] args) {
		char[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
		int[] w = {2, 10, 3, 7, 5, 8, 9, 1, 6};
		int len = 9;
		HuffmanTree tree = new HuffmanTree();
		tree.createTree(c, w, len);
		
	}
	
}
