package org.jzz.study.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.jzz.study.util.Print;

/**
 * 测试Thread.join
 * obj.join,机制：通过对象锁的方式，锁住当前线程，obj线程完成后会唤醒当前线程
 * 使用场景：让线程有序执行，类似功能：CyclicBarrier（有序执行内部代码，比join强大多了）
 * */
public class JoinTest {
	static void test1() {
		Thread preThread = Thread.currentThread();
		for (int i = 0; i < 10; i++) {
			JoinDemo joinDemo = new JoinDemo(preThread, i);	//传递preThread对象
			joinDemo.start();
			preThread = joinDemo;	
		}
	}
	
	static void test2 () throws Exception{
		for (int i = 0; i < 10; i++) {
			JoinDemo joinDemo = new JoinDemo(i);	//不传递preThread对象
			joinDemo.start();
			joinDemo.join(); //卡住主线程，则此处变成创建一个等其运行结束再创建一个。。
		}
	}
	
	static void test3 () throws Exception{
		List<Thread> threads = new ArrayList<Thread>();
		for (int i = 0; i < 10; i++) {
			JoinDemo joinDemo = new JoinDemo(i);
			joinDemo.start();
			threads.add(joinDemo);
		}
		for (Thread thread : threads) {
			thread.join();		//卡住主线程，等待列表中的线程全部结束
		}
	}
	
	public static void main(String[] args)  throws Exception{
//		test1();
//		test2();
		test3();
		Print.print("main thread over");
	}
}

class JoinDemo extends Thread {
	int i;
	Thread preThread = null;
	public JoinDemo(Thread preThread, int i) {
		this.preThread = preThread;
		this.i = i;
	}
	public JoinDemo(int i) {
		this.i = i;
	}
	
	@Override
	public void run() {
		try {
			TimeUnit.MILLISECONDS.sleep(new Random(System.currentTimeMillis()).nextInt(1000));
			if (preThread != null) {
				preThread.join();	//等待前一个线程结束再执行join后的内容,因为耗时的操作都在join前完成了，所以相当的快
			}
			Print.print(i + " thread over");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
