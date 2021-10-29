package org.jzz.study.java8;

import java.util.function.Consumer;

public class ConsumerTest {
	public static void main(String[] args) {
		Consumer<String> consumer = s -> System.out.println(s.toUpperCase());
		Consumer<String> consumer2 = s -> System.out.println(s.toLowerCase());
		Consumer<String> consumer3 = s -> System.out.println(s.substring(5));
		
		consumer.andThen(consumer2).andThen(consumer3).accept("hello word");
	}
}
