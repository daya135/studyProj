package org.jzz.study.generic.genericInterface;

public class Coffee {
	private static long counter = 0;
	private final long id = counter++;  //每创建一个对象便自增1，很巧妙
	public String toString() {
		return getClass().getSimpleName() + " " + id;
	}
}
