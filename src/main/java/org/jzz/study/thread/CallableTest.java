package org.jzz.study.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyCallable implements Callable<Integer>{
	int count = 0, sum = 0;
	String name;
	
	public MyCallable (String name, int count) {
		this.name = name + count; 
		this.count = count;
	}
	
	public Integer call() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + name + "  begin");
		for (int j = 0; j < count ; j++) {
			sum += j;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " " + name + "  end");
		return new Integer(sum);
	}
}

class MyRunable implements Runnable{
	int i = 0;
	int sum = 0;
	
	public MyRunable (int i) {
		this.i = i;
	}
	
	public void run() {
		String theadName = Thread.currentThread().getName();
		System.out.println(theadName + "  begin");
		synchronized (this) {
	
			for (int j = 0; j < i; j++) {
				sum += j;
				System.out.println(theadName + " " + sum);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		
		}
		System.out.println(theadName + " end");
	}

}

public class CallableTest {
	
	public static void testCallable() {

		
		FutureTask<Integer> ft1 = new FutureTask<Integer>(new MyCallable("myCallable", 10));
		FutureTask<Integer> ft2 = new FutureTask<Integer>(new MyCallable("myCallable", 20));
		FutureTask<Integer> ft3 = new FutureTask<Integer>(new MyCallable("myCallable", 30));
		@SuppressWarnings("unchecked")
		FutureTask<Integer>[] fTasks = new FutureTask[]{ft1, ft2, ft3};
		
		System.out.println("主线程： " + Thread.currentThread().getName());
		for (FutureTask ft : fTasks) {
			Thread thread = new Thread(ft);
			thread.start();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("主线程for循环执行完毕..");
		try {
			System.out.println("获取返回值1：" + ft1.get());
			System.out.println("获取返回值2：" + ft2.get());
			System.out.println("获取返回值3：" + ft3.get());
		} catch (InterruptedException | ExecutionException e) {
			
			e.printStackTrace();
		}
	}
	
	public static void testRunable() {
		MyRunable runable = new MyRunable(10);
		
		System.out.println("主线程： " + Thread.currentThread().getName());
		for (int i = 0; i < 3; i ++) {
		//	Thread thread = new Thread(new MyRunable(10));
			Thread thread = new Thread(runable);
			thread.start();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("主线程for循环执行完毕..");
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		//testCallable();
		testRunable();
	}
	
}
