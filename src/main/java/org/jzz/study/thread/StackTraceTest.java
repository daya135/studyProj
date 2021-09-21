package org.jzz.study.thread;

import java.util.concurrent.TimeUnit;

/** 
 * 测试java方法调用栈
 * */
public class StackTraceTest {
	
	static private class A {
		public void doSomeThing() {
			StackTraceElement[] stack = Thread.currentThread().getStackTrace();	//行号从这里得到
			for (StackTraceElement trace : stack) {
				System.out.println(trace.getClassName() + " " + trace.getLineNumber());
			}
		}
	}
	
	static private class B {
		static void doSomeThing(A a) {
			a.doSomeThing();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getId());
		new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread().getName() + " " + Thread.currentThread().getId());
				B.doSomeThing(new A());
				try {
					Thread.sleep(TimeUnit.SECONDS.toMillis(3));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
	}
}
