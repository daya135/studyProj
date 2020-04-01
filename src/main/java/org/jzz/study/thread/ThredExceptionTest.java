package org.jzz.study.thread;

import java.util.concurrent.*;

class ExcetionTask implements Runnable {
	@Override
	public void run() {
		Thread t = Thread.currentThread();
		System.out.println("run() by " +  t);
		System.out.println(
				"eh = " +  t.getUncaughtExceptionHandler());
		throw new RuntimeException("ExcetionTask 抛出了一个异常");
	}
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{
	@Override
	public void uncaughtException(Thread t, Throwable e){
		System.out.println("MyUncaughtExceptionHandler，捕获到线程异常，可以在这里处理异常：" + e);
	}
}

class HandlerThreadFactory implements ThreadFactory {
	@Override
	public Thread newThread(Runnable r) {
		System.out.println(this + " creating new Thread");
		Thread t = new Thread(r);
		System.out.println("created " + t);
		t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
		System.out.println("eh = " + t.getUncaughtExceptionHandler());
		return t;
	}
}

/** 测试线程异常处理器  */
public class ThredExceptionTest {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
		exec.execute(new ExcetionTask());
	}
}
