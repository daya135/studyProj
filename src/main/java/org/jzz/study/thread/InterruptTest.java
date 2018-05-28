package org.jzz.study.thread;

public class InterruptTest extends Thread{
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		InterruptTest myThread = new InterruptTest();
		myThread.start();
		System.out.println("线程启动，主线程休眠..");
		Thread.sleep(2000);
		System.out.println("中断线程..");
		System.out.println(myThread.isInterrupted());
		System.out.println(myThread.interrupted());
		myThread.interrupt();
		System.out.println(myThread.isInterrupted());
		Thread.sleep(2000);
		System.out.println(myThread.isInterrupted());
	}
}
