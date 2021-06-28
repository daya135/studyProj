package org.jzz.study.java8;

import java.util.function.Consumer;

import org.jzz.study.util.Print;


@FunctionalInterface
interface MyConsumer<T> {
	void accept(T t);
}

public class MyStream<T> {
	
	T t;
	
	public void set(T t) {
		this.t = t;
	}
	
	public void forEach(Consumer<? super T> action) {		//这里为什么是super而不是extend ？？？
		action.accept(t);
	}
	
//	public void forEach(Consumer<? extends T> action) {
//		action.accept(action);
//	}
	
	
	public static void main(String[] args) {
		MyStream<Integer> stream = new MyStream<Integer>();
		stream.set(1);
		stream.forEach(t -> Print.print(t + 1));
	}
	
}
