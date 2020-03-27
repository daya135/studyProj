//: net/mindview/util/Print.java
// Print methods that can be used without
// qualifiers, using Java SE5 static imports:
package org.jzz.study.util;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jzz.study.DataStructure.BaseNode;
import org.jzz.study.DataStructure.HuffmanTree;
import org.jzz.study.DataStructure.Tree;

/**
 * @author Merin
 * @version 1.0.0
 */
public class Print {
	static int ContentLength = 2; //打印内容所占的长度
	
	//用于打印的树节点
	static class PNode {
		public BaseNode node;
		public int row;
		public int col;
		public PNode(BaseNode node) {
			this.node = node;
		}
	}
	
	//返回空白字符串，每个空白占用step位数
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
	
  /** 
   * 调用toString打印对象，尾部换行:
   * @param obj 打印的对象
   * @return void
   */
  public static void print(Object obj) {
    System.out.println(obj);
  }
  // Print a newline by itself:
  public static void print() {
    System.out.println();
  }
  // Print with no line break:
  public static void printnb(Object obj) {
    System.out.print(obj);
  }
  // The new Java SE5 printf() (from C):
  public static PrintStream
  printf(String format, Object... args) {
    return System.out.printf(format, args);
  }
  
  public static void PrintIntArr(int[] a) {
	  for (int i = 0; i < a.length; i++){
    	System.out.print(String.format("%d ", a[i]));
	  }
	  System.out.println();
  }
  
  public static void PrintIntArr(int[][] A) {
	  for (int[] a : A) {
			for (int i : a) {
				System.out.print(String.format("%3s", i + " "));
			}
			System.out.println();
	  }
	  System.out.println();
  }
  
  //打印链式树
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
		
		int step = ContentLength; //每个节点内容占用位数
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
	
	//打印用数组表示的树，第二个参数表示以哪个下标为根节点,第三个参数表示长度
	public static void printTree(int a[], int head, int length) {
		if(length > a.length || head >= a.length || head < 0) return;
		int max_row = (int)Math.ceil(Math.log((double)length - head + 1) / Math.log(2)); //取得总层数，请注意是log2(n+1)层
		int step = ContentLength;
		
		for (int r = 0; r < max_row; r++) { //行，从0开始
			String rowStr = "";//当前行打印内容
			for (int c = 0; c < Math.pow(2, r); c++) {	//列，从0开始，第r行有2^r列
				int index = (int)Math.pow(2, r) + c - 1 + head; //计算当前节点下标
				if (index >= length) {
					break;
				}
				String value = String.valueOf(a[index]);	//当前节点内容
				if (value.length() < step) {
					value = getString(step - value.length(), 1) + value; //节点内容长度不够时往前补空格
				}
				
				if (c == 0) {
					rowStr = getString((int)(Math.pow(2, max_row - r - 1) - 1), step); //第0列补充 2^(max_row-r)-1个step空间
				} else {
					rowStr += getString((int)(Math.pow(2, max_row - r) - 1), step); //后续每个间隔 2^(max_row-r+1)-1个step空间
				}
				rowStr += value;
			}
			Print.print(rowStr);
			Print.print();//多打一行空行
		}
	}
	
	/** 一个打印任意类型的数组的方法；
	 * @param arr 数组对象
	 * @param fucName 方法名，转换元素为要显示的字符串内容，默认为toString()
	 * @param splitStr 分隔符
	 */
//	public static void printArr(Object[] arr) {} //只能接受非基本类型数组，麻烦
//	public static <T> void printArr(T[] arr) {}	 //同样只能接受非基本类型数组，麻烦。
	public static void printArr(Object arr, String fucName, String splitStr) { //最终版本
		if(arr == null) return;
		
		try {
			for (int i = 0; i < Array.getLength(arr); i++) { //nb!!!此方法支持任何类型数组！！ps：是一个native方法
				Object obj = Array.get(arr, i);	//nb!!!此方法支持任何类型数组！！ps：是一个native方法
				Object content = obj;
//				object.getClass().isPrimitive();	//判断原始数据类型，但是没用，貌似被Array类包装了～
				if (fucName != null){	
					Method method = obj.getClass().getMethod(fucName, null);
					content = (String) method.invoke(Array.get(arr, i), null);
				}
				System.out.print(content);	//Array.get获得数组对象，invoke对应方法
				System.out.print(splitStr);
			}
			if (!"\n".equals(splitStr))
				System.out.println();	//最后一行避免再次换行
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 一个打印任意类型数组的方法
	 * @param arr 数组对象
	 * @param splitStr 分隔符
	 */
	public static void printArr(Object arr, String splitStr) { 
		printArr(arr, null, splitStr);
	}
  
} ///:~
