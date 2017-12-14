package org.jzz.study.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//注解标示这是一个函数式接口
@FunctionalInterface 
interface FunInterface {
	default void defaultFunc(){
		System.out.println("函数式接口可以定义默认方法： ");
	}
	
	static void description() {
		System.out.println("函数式接口允许static方法，因为static方法不能是抽象的");
	}
	
	int value (int i); //只允许定义一个抽象方法
}

public class LambdaTest {
	
	int objfunc(int i) {
		return i + 1;
	}
	
	static void testFunction(FunInterface fInterface, int i) {
		FunInterface.description();
		fInterface.defaultFunc();
		System.out.println(fInterface.value(i));
	}
	
	public static void main(String[] args) {
		List<String> list = Arrays.asList("aa","cc","bb");
		Collections.sort(list, (a,b)->a.compareTo(b));	 //Lambda表达式，函数体只有一行代码的可以去掉花括号和return表达式
		System.out.println(list);
		
		FunInterface fInterface = i -> i + 1;	//通过Lambda表达式支持函数式接口
		testFunction(fInterface, 1);
		
		FunInterface fInterface2 = Integer::valueOf; // ::关键字 通过静态方法引用完成函数式接口实例化
		System.out.println(fInterface2.value(1));
		
		LambdaTest lTest = new LambdaTest();
		FunInterface fInterface3 = lTest::objfunc;// ::关键字 也可以用对象的方法完成函数式接口实例化
		System.out.println(fInterface3.value(1));
	}	
}
/*
 * DK 1.8 API包含了很多内建的函数式接口，在老Java中常用到的比如Comparator或者Runnable接口，这些接口都增加了@FunctionalInterface注解以便能用在lambda上。
 */
