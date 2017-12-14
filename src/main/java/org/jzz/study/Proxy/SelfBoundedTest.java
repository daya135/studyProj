package org.jzz.study.Proxy;

class Base{}
class Derived extends Base{}

interface SelfBoundSetter<T extends SelfBoundSetter<T>> { //自限定的作用仅限于：如果你要继承我，那么初始化我指定类型参数时必须是你自身
	void set(T arg);
}

interface DerivedSBS extends SelfBoundSetter<DerivedSBS> {
	//void set(T arg);  //继承了父类接口的方法，不允许重复定义
}

class GenericSetter<T>{	//没有自限定声明：如果你要继承我，初始化时可以指定任意类型
	void set(T arg) {System.out.println("GenericSetter.set");}
}
	
class DerivedGs extends GenericSetter<DerivedGs> {	//手动限定为子类，是不是看着像自限定？请看下面
	void set(Derived arg) {	//此处是重写
		System.out.println("DerivedGs.set");
	}	
}

class DerivedGs1 extends GenericSetter<Base> {	//此处真正体现了自限定的本质：此处可以指定别的类来初始化父类，所以父类没有定义自限定就会造成这个结果
	void set(Derived arg) {	//此处是重载
		System.out.println("DerivedGs.set");
	} 
}

public class SelfBoundedTest {
	
	void test(DerivedSBS dsbs1, DerivedSBS dsbs2, SelfBoundSetter sbs) {
		dsbs1.set(dsbs2);
		//dsbs1.set(sbs);	此处因为方法重写，set方法仅接受DerivedSBS（子类）类型
	}
	
	public static void main(String[] args) {
		DerivedGs derivedGs = new DerivedGs();
		//derivedGs.set(new Base());	//此处不行是因为初始化父类用的类型参数是Derived，而不是用自限定类型实现了重写
		derivedGs.set(new Derived());
		
		DerivedGs1 derivedGs1 = new DerivedGs1();
		derivedGs1.set(new Base());
		derivedGs1.set(new Derived());	//这里就是重写
		
		
	}

}
