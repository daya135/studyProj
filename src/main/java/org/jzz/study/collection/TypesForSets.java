package org.jzz.study.collection;

import java.util.HashSet;
import java.util.Set;

//用于set的元素，必须实现equals方法
class SetType {
	int i;	//默认default,同一个包中公开
	public SetType(int n) { i = n; }
	public boolean equals(Object o) {
		return o instanceof SetType && (i == ((SetType)o).i);
	}
	public String toString() { return Integer.toString(i); }
}
//用于HashSet的元素，必须实现hashCode方法
class HashType extends SetType {
	public HashType(int n) { super(n); }
	public int hashCode() { return i;}
}
//用于实现TreeSet的元素，必须实现Comparable接口
class TreeType extends SetType implements Comparable<TreeType> {
	public TreeType(int n) { super(n); }
	public int compareTo(TreeType o) {
		return (o.i < i ? -1 : (o.i == i ? 0 : 1));
	}
}

public class TypesForSets{
	static <T> Set<T> fill(Set<T> set, Class<T> type) {
		try {
			for (int i = 0; i < 10; i++) {
				set.add(type.getConstructor(int.class).newInstance(i));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return set;
	}
	static <T> void test(Set<T> set, Class<T> type) {
		fill(set, type);
		fill(set, type);
		fill(set, type);
		System.out.println(set);
	}
	public static void main(String[] args) {
		//这样使用,HashSet的s失去唯一性，不满足Set的基本定义了
		test(new HashSet<SetType>(), SetType.class); 
	}
}
