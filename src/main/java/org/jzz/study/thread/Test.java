package org.jzz.study.thread;

import java.util.Date;

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
	
	
	//测试在主线程中主动挂起、唤醒线程
	public static void main(String[] args) throws Exception {
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
}
