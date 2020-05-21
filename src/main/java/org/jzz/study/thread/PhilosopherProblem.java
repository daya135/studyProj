package org.jzz.study.thread;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Merin
 * 使用服务生对象解决哲学家问题，将锁集中在服务生对象上。
 */
//服务生类
class Waiter {
	int forks;
	public Waiter(int init) {
		this.forks = init;
	}
	
	//直接在服务生对象上定义同步方法，完全移除哲学家内部的同步机制，比较优化
	//将while循环直接置于这里，并设置wait(),再次精简哲学家内部代码行为
	public synchronized boolean applyForks() {
		while (forks < 2) {
			try {
				wait();	//进入waiting状态，等待唤醒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		forks = forks - 2;
		return true;
	}
	public synchronized void returnForks() {
		forks = forks + 2;
		notifyAll();	//唤醒所有
	}
}
//哲学家类
class Philosopher implements Runnable{
	Waiter waiter; //充当服务生对象，管理所有筷子，同一时间只能有一个人占用服务生
	int id;
	Random random =  new Random(47 + id);
	
	public Philosopher (Waiter waiter, int id) {
		this.waiter = waiter;
		this.id = id;
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			//拿筷子
			waiter.applyForks();
			
			//进餐中
			System.out.println(String.format("哲学家%d进餐中..", id));
			try {
				Thread.sleep(1000 + random.nextInt(2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//放筷子
			System.out.println(String.format("哲学家%d进餐完毕", id));
			waiter.returnForks();
			
			//思考中
			try {
				Thread.sleep(1000 + random.nextInt(2000));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}

public class PhilosopherProblem {
	public static void main(String[] args) {
		Waiter waiter = new Waiter(5);
		Runnable[]  philosophers = {new Philosopher(waiter, 0), new Philosopher(waiter, 1),
				new Philosopher(waiter, 2), new Philosopher(waiter, 3), new Philosopher(waiter, 4)};
		ExecutorService exec = Executors.newCachedThreadPool();
		for (Runnable philosopher : philosophers) {
			exec.execute(philosopher);
		}
	}
	
}
