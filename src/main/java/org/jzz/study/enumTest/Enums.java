package org.jzz.study.enumTest;

import java.util.Random;

public class Enums {
	private static Random random = new Random(47);
	public static <T extends Enum<T>> T random(Class<T> ec) {
		return random(ec.getEnumConstants());
	}
	public static <T> T random(T[] values) {
		return values[random.nextInt(values.length)];
	}
	public static <T> void print(T[] values) {
		for (T t : values) {
			System.out.print(t + " ");
		}
		System.out.println();
	}
}
