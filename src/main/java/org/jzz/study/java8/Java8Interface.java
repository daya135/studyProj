package org.jzz.study.java8;

//@FunctionalInterface
public interface Java8Interface {
	default int add() {
		return 1;
	}
	
	default void set() {	//默认实现可以有多个
		
	}
	
	void test();
	
//	void test2();	//函数式接口要求仅有一个抽象方法
}
