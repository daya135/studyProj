package org.jzz.study.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.security.auth.x500.X500Principal;

import com.mongodb.Function;

//注解标示这是一个函数式接口,在编译上保证不会被新增方法
@FunctionalInterface 
interface FunInterface {
	default void defaultFunc(){
		System.out.println("函数式接口可以定义默认方法： ");
	}
	
	static void description() {
		System.out.println("函数式接口允许static方法，因为static方法不能是抽象的");
	}
	int func(int i, int j); //只允许定义一个抽象方法
}

@FunctionalInterface 
interface FunInterface2 {
	int value (int i); 
}

public class LambdaTest {
	
	int add(int i, int j) {
		return i + j;
	}
	
	static void testFunInterface1(FunInterface fInterface, int i) {
		FunInterface.description();
		fInterface.defaultFunc();
		System.out.println(fInterface.func(i, 2));
	}
	
	public static void main(String[] args) {
		List<String> list = Arrays.asList("aa","cc","bb");
		Collections.sort(list, (a,b)->a.compareTo(b));	 //注意这里, 编译器根据sort函数的参数决定的要实现的接口, 所以此处箭头函数的返回值要根据Comparator接口的覆写规则来
		System.out.println(list);
		
		FunInterface fInterface = (i, j) -> {	//通过Lambda表达式实现函数式接口,可支持写多行代码,就跟Es6的形式一样
			i = i++;
			++j;
			return i * j;	
		};
		testFunInterface1((i, j) -> i - j, 1);	//将函数当作参数传入,编译器自动构建为对象（这里隐藏了一个自动类型推断！！）
		
		LambdaTest lambdaTest = new LambdaTest();
		fInterface = lambdaTest::add;// ::关键字 也可以用对象的方法完成函数式接口实例化
		System.out.println(fInterface.func(1, 2));
		
		FunInterface2 fInterface2 = Integer::valueOf; // ::关键字 通过静态方法引用完成函数式接口实例化
		System.out.println(fInterface2.value(1));
		
		Function<Integer, String> func = x -> String.valueOf(x);	//使用内建的接口定义功能
		System.out.println(func.apply(1024));
		
		
		list.sort(Comparator.comparingInt(Integer::parseInt));
	}	
}
/*
 * DK 1.8 API包含了很多内建的函数式接口，在老Java中常用到的比如Comparator或者Runnable接口，这些接口都增加了@FunctionalInterface注解以便能用在lambda上。
 */
