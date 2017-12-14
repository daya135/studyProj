package org.jzz.study.thread;

import java.util.Random;

/**
 * @author Merin
 * 使用服务生对象解决哲学家问题，将锁集中在服务生对象上。先获得锁再使用逻辑判断是否满足执行条件。
 */
class Philosopher implements Runnable{
	Integer[] forks; //充当服务生对象，管理所有筷子，同一时间只能有一个人占用服务生
	int id;
	Random random;
	
	public Philosopher (Integer[] forks, int id) {
		this.forks = forks;
		this.id = id;
		random = new Random(47 + id);
	}

	@Override
	public void run() {
		while (true) {
			
			//准备拿筷子，占用服务生
			synchronized(forks) {
				if (id % 2 == 0) {	//从左手拿筷子
					if(forks[id] == 0 && forks[(id + 1) % 5] == 0) {
						forks[id] = 1;
						forks[(id + 1) % 5] = 1;
					} else {
						//不用wait()程序仍然正确运行！但是！！！性能会有问题，程序会极大占用CPU资源！！
						try {
							forks.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
				} else {	//从右手拿筷子
					if(forks[(id + 1) % 5] == 0 && forks[id] == 0 ) {
						forks[(id + 1) % 5] = 1;
						forks[id] = 1;
					} else {
						try {
							forks.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
				}
			}
			System.out.println(String.format("哲学家%d进餐中..", id));
			try {
				Thread.sleep(1000 + random.nextInt(2000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println(String.format("哲学家%d进餐完毕", id));
			//放筷子
			synchronized(forks) {
				if (id % 2 == 0) {
					forks[id] = 0;
					forks[(id + 1) % 5] = 0;
	
				} else {
					forks[(id + 1) % 5] = 0;
					forks[id] = 0;
				}
				forks.notifyAll();
			}
			
			//思考中
			try {
				Thread.sleep(1000 + random.nextInt(2000));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

public class PhilosopherProblem {
	
	
	public static void main(String[] args) {
		Integer[] forks = {0,0,0,0,0}; 
		Runnable[]  philosophers = {new Philosopher(forks, 0), new Philosopher(forks, 1),
				new Philosopher(forks, 2), new Philosopher(forks, 3), new Philosopher(forks, 4)};
		for (Runnable philosopher : philosophers) {
			Thread thread = new Thread(philosopher);
			thread.start();
		}
	}
	
}
