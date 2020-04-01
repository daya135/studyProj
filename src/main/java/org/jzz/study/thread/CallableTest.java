package org.jzz.study.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableTest {
	
	public static void testCallable() {
		FutureTask<Integer> ft1 = new FutureTask<Integer>(new MyCallable("myCallable", 10));
		FutureTask<Integer> ft2 = new FutureTask<Integer>(new MyCallable("myCallable", 20));
		FutureTask<Integer> ft3 = new FutureTask<Integer>(new MyCallable("myCallable", 30));
		@SuppressWarnings("unchecked")
		FutureTask<Integer>[] fTasks = new FutureTask[]{ft1, ft2, ft3};
		
		for (FutureTask ft : fTasks) {
			Thread thread = new Thread(ft);
			thread.start();
		}
		System.out.println("主线程for循环执行完毕..");
		try {
			System.out.println("获取返回值3：" + ft3.get());	//get是阻塞方法，可以改为轮询式判断isDone
			System.out.println("获取返回值1：" + ft1.get());	
			System.out.println("获取返回值2：" + ft2.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public static void testRunable() {
		MyRunable runable = new MyRunable(10);
		for (int i = 0; i < 3; i ++) {
//			Thread thread = new Thread(new MyRunable(10));	//创建多个runable对象，则线程之间的任务完全没联系，不属于合作计算
			Thread thread = new Thread(runable);	//使用一个runable对象建立多个线程, 则runable对象内部数据被多个线程改变，不加同步则发生脏读脏写
			thread.start();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("主线程： " + Thread.currentThread().getName());
		testCallable();
		testRunable();	//这句会被上一个方法内部的FutureTask.get阻塞
		System.out.println("主线程执行完毕..");
	}
	
	static class MyRunable implements Runnable{
		int num = 0;
		int sum = 0;
		
		public MyRunable (int num) {
			this.num = num;
		}
		
		public void run() {
			String threadName = Thread.currentThread().getName();
			System.out.println(threadName + " " + sum + "  begin");
			//synchronized (this) {
			for (int i = 0; i < num; i++) {
				synchronized (this) {	//如果同步加在循环外面，则先获得锁的线程计算完成后，其它线程才能开始执行
					sum += i;
					System.out.println(threadName + " " + sum);
				}
				try {
					TimeUnit.MILLISECONDS.sleep(new Random().nextInt(500));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(threadName + " end");
		}
	}
}

/** 一个用于测试的callable对象 */
class MyCallable implements Callable<Integer>{
	int count = 0, sum = 0;
	String name = "MyCallable";
	
	/**
	 * @param name 任务名称
	 * @param count 用于计算0~count的累加值
	 * */
	public MyCallable (String name, int count) {
		this.name = name + count; 
		this.count = count;
	}
	
	public Integer call() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + name + "  begin");
		for (int j = 0; j < count ; j++) {
			sum += j;
			try {
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(200));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " " + name + "  end");
		return Integer.valueOf(sum);
	}
	
	@Override
	public String toString() {
		return name;
	}
}