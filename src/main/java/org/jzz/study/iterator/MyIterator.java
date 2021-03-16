package org.jzz.study.iterator;

import java.util.AbstractList;
import java.util.Iterator;

public class MyIterator implements Iterator<Object>{

	private AbstractList<Object> collection;  
	private int pos = -1;
	
	public MyIterator(AbstractList<Object> myCollection) {
		collection = myCollection;
	}

	@Override
	public boolean hasNext() {
		if (pos < collection.size() - 1) {
			return true;
		}
		return false;
	}

	@Override
	public Object next() {
		if (hasNext()) {
			pos++;
			return collection.get(pos) + " from MyIterator";
		}
		return null;
	}

}
