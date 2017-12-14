package org.jzz.study.innerClass;

/*
 * 利用静态内部类进行单元测试，可防止发布的产品类里面含有冗余的main函数
 */
public class UnitTest {
	public void f() {
		System.out.println("f()");
	}
	public static class tester {
		public static void main(String[] args) {
			UnitTest unitTest = new UnitTest();
			unitTest.f();
		}
	}
}
