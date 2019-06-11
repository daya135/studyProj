package org.jzz.studyProj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jzz.study.DataStructure.BaseNode;
import org.jzz.study.DataStructure.HuffmanTree;
import org.jzz.study.DataStructure.Tree;
import org.jzz.study.util.Print;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.sun.javafx.collections.SortableList;
import com.sun.scenario.effect.Merge;

public class Test {
	
	static void swap(int a[], int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}
	
//	static final int capacity = 8;
//	static final int num = 4;
//	static final int[] w = {2, 3, 4, 5};
//	static final int[] v = {3, 4, 5, 6};
//	static int[][] V = new int[num + 1][capacity + 1];
//	static int maxV = 0;
//	static int max_i = 0;
//	static int max_j = 0;
//	static {
//		Arrays.fill(V[0], 0);
//		for (int i = 0; i < num; i++) {
//			V[i][0] = 0;
//		}
//	}
	
	static class PNode {
		public BaseNode node;
		public int row;
		public int col;
		public PNode(BaseNode node) {
			this.node = node;
		}
	}
	
	
	public static void printTree(BaseNode head) {
		ArrayList<PNode> list = new ArrayList<PNode>();
		int row = 1;
		List<PNode> tempList = new ArrayList<PNode>();
		PNode pNode = new PNode(head);
		pNode.row = row;
		pNode.col = 1;
		tempList.add(pNode);
		list.add(pNode);
		while(tempList.size() != 0) {
			//获得下一层节点
			tempList = getNextStep(tempList, ++row);
			//将下一层节点放入到当前打印队列
			for (int i = 0; i < tempList.size(); i++) {
				list.add(tempList.get(i));
			}
			if (tempList.size() == 0) {
				--row;
				break;
			}
		}
		
		int step = 2; //每个节点内容占用位数
		int r = 1; //当前层数
		do {
			String rowStr = getString(new Double(Math.pow(2, row - r)).intValue() - 1, step);
			PNode node;
			do {
				node = list.remove(0);
				String value = node.node.getValue();
				if (value.length() < step) {
					value = getString(step - value.length(), 1) + value; //节点内容长度不够时往前补空格
				}
				rowStr += value;
				rowStr += getString(new Double(Math.pow(2, row + 1 - r)).intValue() - 1, step);
			} while (list.size() > 0 && r == list.get(0).row);
			Print.print(rowStr);
			Print.print(); //空一行
			r++;
		} while (r <= row && list.size() > 0);
		
	}
	
	//返回空白区域，每个空白占用step位数
	static String getString(int num, int step) {
		StringBuffer sBuffer = new StringBuffer("");
		for (int i = 0; i < num * step; i++) {
			sBuffer.append(" ");
		}
		return sBuffer.toString();
	}
	
	//返回下一层所有节点，记录层数和位置
	static List<PNode> getNextStep(List<PNode> tempList, int row) {
		ArrayList<PNode> nextList = new ArrayList<PNode>();
		PNode b;
		int tcol = 0;
		boolean hasChild = false; //整个这一层是否有非空子节点
		BaseNode bNode = new BaseNode() {
			@Override
			public String getValue() {
				return " ";
			}
			@Override
			public BaseNode getRight() {
				return null;
			}
			@Override
			public BaseNode getParent() {
				return null;
			}
			@Override
			public BaseNode getLeft() {
				return null;
			}
		};
		for (int i = 0; i < tempList.size(); i++) {
			b = tempList.get(i);
			PNode tp;
			if (b.node.getLeft() != null) {
				tp = new PNode(b.node.getLeft());
				hasChild = true;
			} else {
				tp = new PNode(bNode);
			}
			tp.row = row;
			tp.col = ++tcol;
			nextList.add(tp);
			if (b.node.getRight() != null) {
				tp = new PNode(b.node.getRight());
				hasChild = true;
			} else {
				tp = new PNode(bNode);
			}
			tp.row = row;
			tp.col = ++tcol;
			nextList.add(tp);
		}
		if(!hasChild) {
			return new ArrayList<PNode>();
		}
		return nextList;
	}
	
	
	public static void main(String[] args) throws IOException {
		int a[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		int[] steps = {4, 2, 1};
		String tStr = "ABACDABABH";
		String pString = "ABAC";
//		while (true) {
//			System.in.read();
//		}
//		sort(a);
//		shellSort(a, steps);
//		devide(a, 0, a.length - 1, new int[a.length]);
//		quickSort(a, 0, a.length - 1);
//		heapSort(a);
//		Print.PrintIntArr(getNext(tStr));
//		FindMax();
//		findWhy(max_i, max_j);
//		findQueen(new int[SIZE], 0);
		
		Integer values[] = {10, 8, 9, 4, 12, 6, 14, 3, 5, 11, 15, 7, 1, 13, 2};
		Tree<Integer> tree = new Tree<Integer>();
		tree.InitTree(values);
//		printTree(tree.root);
		
		char[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
		int[] w = {2, 10, 3, 7, 5, 8, 9, 1, 6};
		int len = 9;
		HuffmanTree hufftree = new HuffmanTree();
		hufftree.createTree(c, w, len);
		Print.print(System.currentTimeMillis());
		printTree(hufftree.root);
		Print.print(System.currentTimeMillis());
		
	}
}
