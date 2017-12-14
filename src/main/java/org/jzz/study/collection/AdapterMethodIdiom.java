package org.jzz.study.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/*
 * 使用适配器模式，实现具有反向迭代能力的的Foreach循环
 */
class ReversibleArrayList<T> extends ArrayList<T> {
	public ReversibleArrayList(Collection<T> c) {
		super(c);
	}
	public Iterable<T> reversed(){
		//构建一个Iterable实例，用于Foreach循环
		return new Iterable<T>(){
			//重写iterator方法，返回一个Iterator实例
			public Iterator<T> iterator(){
				//构建一个Iterator实例，用于产生反向迭代的能力
				return new Iterator<T>() {
					int current = size() - 1;
					public boolean hasNext() {
						return current > -1;
					}
					public T next() {
						return get(current--);
					}
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
}

public class AdapterMethodIdiom {
	public static void main(String args[]) {
		ReversibleArrayList<String> ral = new ReversibleArrayList<String>(
				Arrays.asList("aa bb cc dd".split(" ")));
		for (String s : ral) {
			System.out.print(s + " ");
		}
		System.out.println();
		for (String s : ral.reversed()) {
			System.out.print(s + " ");
		}
	}
}
