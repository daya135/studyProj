package org.jzz.study.generic.genericInterface;

import java.util.Iterator;
import java.util.Random;

/*
 * 接口声明时可以用泛化参数
 * 但是被实际实现时必须指定泛化参数，所以此处定义类时要指定参数而不能继续用泛型参数T
 */
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {
	private Class[] types = {Latte.class, Mocha.class, Cappuccino.class};
	private static Random rand = new Random(47);  //静态的随机种子，每次运行结果都一样
	private int size = 0;
	public CoffeeGenerator() {}
	public CoffeeGenerator(int size) { this.size = size; };
	@Override
	public Coffee next() {
		try {
			size --;
			return (Coffee) types[rand.nextInt(types.length)].newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 内部类，用于实现Iterator接口，
	*/
	class coffeeIterator implements Iterator<Coffee> {
		int count = size;

		@Override
		public Coffee next() {
			count --;
			return CoffeeGenerator.this.next();
		}
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		@Override
		public boolean hasNext() {
			return count > 0;
		}
	}
	
	@Override
	public Iterator<Coffee> iterator() {
		return new coffeeIterator();
	}


	public static void main(String[] args) {
		CoffeeGenerator coffeeGenerator = new CoffeeGenerator();
		for(int i = 0; i < 5; i ++)
			System.out.println(coffeeGenerator.next());
		for (Coffee c : new CoffeeGenerator(4)) {
			System.out.println(c);
		}
	}
}
