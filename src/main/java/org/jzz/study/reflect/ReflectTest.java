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
		
		for (Method method : methods2) {
			int modifier = method.getModifiers();	//获得属性或方法声明，用一位整数记录多个声明如：public static final xxx
			Print.print(method);
	        System.out.println("m2 isPublic: " + (modifier&1)); 
	        System.out.println("m2 isPrivate: " + (modifier&2)); 
	        System.out.println("m2 isProtected: " + (modifier&4)); 
	        System.out.println("m2 isStatic: " + (modifier&8)); 
	        System.out.println("m2 isFinal: " + (modifier&16)); 
			if (method.getParameterCount() == 1 && ((modifier&1) == 1) && (method.getParameters())[0].getType().equals(int.class)) {
				method.invoke(obj, 100);	//通过反射调用方法
			}
		}
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
