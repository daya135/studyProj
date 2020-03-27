package org.jzz.study.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


import org.jzz.study.util.Print;

public class ReflectTest {
	public static void main(String args[]) throws Exception {
		Class<?> c = Class.forName("org.jzz.study.reflect.TestReflectClass");
		Constructor<?> cons = c.getConstructor();
		Constructor<?> cons2 = c.getConstructor(int.class, int.class, int.class, int.class);
		Object obj = c.newInstance();
		Object obj2 = cons.newInstance();
		Object obj3 = cons2.newInstance(10, 20, 30, 40);
		
		Field[] f = c.getFields();
		Field[] f2 = c.getDeclaredFields();
		Print.print("getFields: " + f.length);
		Print.printArr(f, "getName", "\n");
		Print.print("getDeclaredFields: " + f2.length);
		Print.printArr(f2, "getName", "\n");
		
		Method[] methods = c.getMethods();
		Method[] methods2 = c.getDeclaredMethods();
		Print.print("getMethods: " + methods.length);
		Print.printArr(methods, "getName", "\n");
		Print.print("getDeclaredMethods: " + methods2.length);
		Print.printArr(methods2, "\n");
		
		methods[0].invoke(obj, 100);	//通过反射调用方法
		Print.print(((TestReflectClass)obj).getString());	//强制转换，显示调用方法
		
	}
}

/** 反射测试类 */
class TestReflectClass {
	static int count = 0;
	
	int a;
	private int b;
	protected int c;
	public int d;
	
	{
		System.out.println("new Instance..." + count++);
	}
	
	public TestReflectClass() {
		a = 1;
		b = 2;
		c = 3;
		d = 4;
	}
	
	public TestReflectClass(int a, int b, int c, int d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	void setA(int a) {
		this.a = a;
	}
	
	private void setB(int b) {
		this.b = b;
	}
	
	protected void setC(int c) {
		this.c = c;
	}
	
	public void setD(int d) {
		this.d = d;
	}
	
	public String getString() {
		return String.format("%d %d %d %d", a, b, c, d);
	}
	
}
