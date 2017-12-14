package org.jzz.study.generic;


//class A{}
//
//class B extends A{};
//
//class C{}

public class GenericTest <T>{
	
	private T t;
	
	public void test(T t) {
		System.out.println(t.getClass().getName());
	}
	
	//此处有个很有意思的事情，就是T可以跟类类型不一致！！此时方法参数类型将跳出类泛型限定！！
	//实际上，这是因为方法类型作用域独立于类类型作用域的结果，应该标示为不同的类型参数
	//public <T extends A> void test1(T t) {
	public <E extends A> void test1(E e) {
		A a = e;
		//this.t = e;
		System.out.println(e.getClass().getName());
	}
	
	public void set(T t) {
		this.t = t;
	}
	
	public T get() {
		return t;
	}
	
	public T get(GenericTest<T> gTest) {
		return gTest.get();
	}
	
	public static <K> K noWildcard(GenericTest<K> gTest, K k) {
		gTest.set(k);
		k = gTest.get();
		return k;
	}
	
	public static <K> Object wildcard(GenericTest<?> gTest, K k) {
		//gTest.set(k);
		//K k1 = gTest.get();
		k = (K) gTest.get(); //此处不会因为类型不同抛出异常,为什么??
		Object obj = gTest.get();
		return obj;
	}
	
	public static <K> K extendsWildcard(GenericTest<? extends K> gTest, K k) {
		//gTest.set(k);
		k = gTest.get();
		return k;
	}
	
	public static <K> Object supperWildcard(GenericTest<? super K> gTest, K k) {
		gTest.set(k);
		//k = gTest.get();
		Object obj =  gTest.get();
		return obj;
	}
	
	//通配符，不能这么写，因为通配符是实参
//	public <? extends A> void test2(T t) {
//		System.out.println(t.getClass().getName());
//	}

	public static void main(String[] args) {
		GenericTest<A> gTesta = new GenericTest<A>();
		
		gTesta.test(new A());
		gTesta.test(new B());
		gTesta.test1(new A());
		gTesta.test1(new B());
		//gTesta.test(new C());	//Compile error!
		
		GenericTest<B> gTestb = new GenericTest<B>();
//		gTestb.test(new A());	//Compile error!
//		gTestb.set(new A());
		gTestb.test(new B());
		gTestb.test1(new B());
		gTestb.test1(new A()); //这里很奇怪，竟然能用??
		
		GenericTest<C> gTestc = new GenericTest<C>();
		//gTestb.test(new A());	//Compile error!
		gTestc.test(new C());
		//以下这两个实参厉害了，直接跳出了泛型类类型限定！！！
		gTestc.test1(new A());
		gTestc.test1(new B());
		//gTestc.test1(new C());
		gTestc.set(new C());
		
		//此处，使用规则将完全受限于声明规则，实参完全被隐藏（拜托，面向对象基本思想好不。。）
		GenericTest gTest = new GenericTest<A>(); //不规范写法
		GenericTest<?> gTest1 = new GenericTest<A>();
		GenericTest<? extends Object> gTest5 = new GenericTest<A>();
		GenericTest<? extends A> gTest2 = new GenericTest<A>();
		GenericTest<? extends A> gTest3 = new GenericTest<B>();
		GenericTest<? super A> gTest4 = new GenericTest<A>();
		//GenericTest<? super A> gTest5 = new GenericTest<B>();
		
		gTest.test(new A());
		gTest.set(new A());
		Object obj = gTest.get();
		
//		gTest1.set(new A());    //这个和wildcard(GenericTest<?> gTest, T t)中不能写入是一样的道理
//		T t1 = gTest1.get();	//这个和wildcard(GenericTest<?> gTest, T t)中不能读取是一样的道理
		gTest1.get();
//		gTest1.test(new Object()); //这里为啥不行??
//		gTest1.test(new A());	//这里为啥不行??
//		gTest5.test(new A());	//这里为啥不行??
		
//		gTest2.set(new B());	//这个和extendsWildcard(GenericTest<? extends T> gTest, T t)中不能写入是一样的道理
//		T t1 = gTest4.get();	//这个和supperWildcard(GenericTest<? super T> gTest, T t)中不能按类型读取是一样的道理
//		gTest2.test(new A());	//这里为啥不行??
		Object t2 = gTest4.get();	
		
		gTest4.set(new A());
		gTest4.test(new A());

		GenericTest.extendsWildcard(gTest2, new B());
		GenericTest.extendsWildcard(gTest3, new A());
		GenericTest.extendsWildcard(gTest3, new B());	//这个也行，擦除效应
		
		GenericTest.supperWildcard(gTest4, new A());
		GenericTest.supperWildcard(gTest4, new B());
		
		GenericTest<?> gTest6 = gTestc;
		//gTest5.set(new C());  注意看，无界通配符不能写，这个和wildcard(GenericTest<?> gTest, T t)函数里面是一样的
//		A a = (A)gTest5.get();	//此处会抛出异常
		A a1 = null;
		GenericTest.<A>wildcard(gTest6, a1);	//此处不会抛出异常,为什么??
		
	}
}
