package org.jzz.study.iterator;

import java.util.AbstractList;
import java.util.Iterator;

import org.jzz.study.util.Print;

/** 测试迭代器 */
public class TestIterator {
	public static void main(String[] args) {
		AbstractList<Object> collection = new MyCollection();
		Iterator<Object> iterator = new MyIterator(collection);
		while (iterator.hasNext()) {
			Print.print(iterator.next());
		}
	}
}
