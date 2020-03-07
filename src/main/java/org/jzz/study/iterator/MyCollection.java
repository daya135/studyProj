package org.jzz.study.iterator;

import java.util.AbstractList;
import java.util.Iterator;

/**
 * @author jiangzhengzhou  2019/11
 *	AbstractList 充当容器接口
 */
public class MyCollection extends AbstractList<Object>{

	private String[] strArr = {"A","B","C","D","E"}; 
	
	@Override
	public Object get(int index) {
		return strArr[index];
	}

	@Override
	public int size() {
		return strArr.length;
	}

	//迭代模式，collection需要实现的核心方法（此覆盖是为了看着方便）
	@Override
	public Iterator iterator() {  
		return new MyIterator(this);
	}
	
	
}
