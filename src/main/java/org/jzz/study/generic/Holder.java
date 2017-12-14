package org.jzz.study.generic;

class A{}

class B extends A{}

class C{}

public class Holder<T> {
	T t;
	public void set (T t) {
		this.t = t;
	}
	
	public <T extends A> void set1(T t) {
		T t1 = t;
		//this.t = t;
	}
	
	public static void main(String[] args) {
		Holder<A>  holder = new Holder<A>();
		holder.set(new A());
		holder.set(new B());

	}
}
