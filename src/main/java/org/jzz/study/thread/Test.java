package org.jzz.study.thread;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;

import org.jzz.study.innerClass.UnitTest.tester;
import org.jzz.study.util.Print;

public class Test {
	static class myThread extends Thread {
		MyLock lock;
		public myThread(MyLock lock) {
			this.lock = lock;
		}
		@Override
		public void run() {
			while (true) {
				synchronized (lock) {
					try {
						if ("stop".equals(lock.key)) {
							lock.wait();
							lock.notifyAll();
						}
						System.out.println("线程正在运行" + lock.key + new Date().toString());
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	static class MyLock {
		String key;
		public MyLock(String key) {
			this.key = key;
		}
	}
	
	/** 测试多线程环境下HashMap的安全性问题
	 * Hashtable 用synchronized实现线程安全，访问其不同方法的线程也会阻塞，效率低 */
	static void testHashMapUnsafe() {
		HashMap<String, String> map = new HashMap<String, String>();
//		Hashtable<String, String> map = new Hashtable<String, String>();
		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					map.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
				}
			}).start();
		}
		Print.print(map.size());
	}
	
	/** 测试在主线程中主动挂起、唤醒线程 */
	static void testWaitAndAwake() throws InterruptedException {
		MyLock lock = new MyLock("run");
		myThread  thread = new myThread(lock);
		thread.start();
		
		Thread.sleep(3000); 
		thread.suspend();
//		lock.key = "stop";	//挂起线程
		Thread.sleep(3000);
		thread.resume();
//		synchronized (lock) {
//			lock.key = "run";	
//			lock.notifyAll();	//唤醒线程
//		}
	}
	
	public static void main(String[] args) throws Exception {
//		testHashMapUnsafe();
		
	}
}
