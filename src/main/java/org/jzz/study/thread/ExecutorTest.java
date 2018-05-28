package org.jzz.study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorTest {
	
	class Sleeper extends Thread {
		private int duration;
		public Sleeper(String name, int sleepTime) {
			super(name);
			duration = sleepTime;
			start();
		}
		public void run() {
			try {
				sleep(duration);
			} catch (InterruptedException e) {
				System.out.println(getName() + " was Interrupted. isInterrupted(): " + isInterrupted());
				return;
			}
			System.out.println(getName() + " was awakened");
		}
	}
	
	class Joiner extends Thread {
		private Sleeper sleeper;
		public Joiner(String name, Sleeper sleeper) {
			super(name);
			this.sleeper = sleeper;
			start();
		}
		public void run() {
			try {
				sleeper.join();	//在一个线程内调用，而不是在一个任务内调用？？
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
			System.out.println(getName() + " join completed");
		}
	}
	
	public void JoinTest() {
		Sleeper
			sleepy = new Sleeper("Sleepy", 1000),
			grumpy = new Sleeper("Sleepy", 1000);
		Joiner
			dopey = new Joiner("Dopey", sleepy),
			doc = new Joiner("Doc", grumpy);
		grumpy.interrupt();
			
	}
	
	public static void main(String[] args) {
//		ExecutorService exec = Executors.newCachedThreadPool();
//		exec.execute(new MyRunable(10));
//		exec.shutdown();
		
		new ExecutorTest().JoinTest();
	}
}
