package org.jzz.study.DataStructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.jzz.study.util.Print;
import org.jzz.study.util.CompareUtil;


/* 泛型排序方法版本一 
 * 存在各种需要优化的设计问题
/* 测试类的比较方器, 每种需要排序的类型需要额外配置一个比较器，用于在泛型方法中实现比较*/
public class BubbleSort <T>{
	
	/* 对容器进行排序, 交换方式采用插入删除节点，适合linkList */
	public void Sort(List<T> list, Comparator<T> comparator) {
		for (int i = 0; i < list.size() -1; i++) {
			Boolean overFlag = true;
			for(int j = 1; j < list.size() - i; j++) {	//每次都要从头开始扫描，截止到上一个冒出泡的位置
				if (comparator.compare(list.get(j - 1), list.get(j)) > 0) { //控制排序方向
					/* 交换方式采用插入删除节点，容器必须支持变长*/
					T t = list.remove(j - 1);
					list.add(j, t);
					overFlag = false;
				}
			}
			if (overFlag) break;
		}
	}
	
	/* 对数组进行排序 */
	public static <E> void Sort(E[] arrayT, Comparator<E> comparator) {
		for (int i = 0; i < arrayT.length -1; i++) {
			Boolean overFlag = true;
			for(int j = 1; j < arrayT.length - i; j++) {	//每次都要从头开始扫描，截止到上一个冒出泡的位置
				if (comparator.compare(arrayT[j -1], arrayT[j]) > 0) { //控制排序方向
					E e = arrayT[j -1];
					arrayT[j -1] = arrayT[j];
					arrayT[j] = e;
					overFlag = false;
				}
			}
			if (overFlag) break;
		}
	}
	

	
	public static void main(String[] args) {
		BubbleSort<String> bubbleSort = new BubbleSort<>();
		//List<String> list = Arrays.asList("aab", "cbb", "bcc");	//产生基于固定数组的定长容器(fix-size)，不支持增删
		List<String> list = new ArrayList<>(Arrays.asList("aab", "cbb", "bcc")); //这样容器变成可变长容器了！
		list.add("dd");
		Comparator<String> compareStr = new CompareUtil.StringComparator();
		Comparator<Integer> compareInt = new CompareUtil.IntegerComparator();
		Print.print(list);
		bubbleSort.Sort(list, compareStr);
		Print.print(list);
		//bubbleSort.Sort(list, compareInt);	//继承泛型接口后将继承其泛型参数
		
		
		Integer[] integers = {1,3,2,5,4};
		BubbleSort.Sort(integers, compareInt); //使用String泛型参数声明的对象来排序整型
		Print.print(Arrays.asList(integers));
		
	}
}
