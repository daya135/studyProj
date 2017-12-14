package org.jzz.study.thread;

import java.util.Random;

/**
 * 
 * @author Merin
 * 区分拿筷子的顺序，解决哲学家问题
 */
class Philosopher3 implements Runnable{
	Integer left; 
	Integer right;
	int id;
	Random random;
	
	public Philosopher3 (Integer[] forks, int id) {
		this.id = id;
		left = forks[id % 5]; //左边的筷子
		right = forks[(id + 1) % 5];	//右边的筷子
		random = new Random(47 + id);
	}

	@Override
	public void run() {
		System.out.println("哲学家" + id + "启动");
		while (true) {
			if (id % 2 == 0) {
				//先拿左边的筷子
				synchronized(left) {
					System.out.println("哲学家" + id + "拿到筷子" + left);
					try {
						Thread.sleep(500 + random.nextInt(500));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//再拿右边的筷子
					synchronized(right) {
						System.out.println("哲学家" + id + "拿到筷子" + right);
						System.out.println(String.format("哲学家%d进餐中..", id));
						try {
							Thread.sleep(1000 + random.nextInt(2000));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(String.format("哲学家%d进餐完毕", id));
//						right.notifyAll(); //这里是多余的，并没有需要通知的线程
					}
//					left.notifyAll();//这里是多余的，并没有需要通知的线程
				}
			} else {
				//先拿右边的筷子
				synchronized(right) {
					System.out.println("哲学家" + id + "拿到筷子" + right);
					try {
						Thread.sleep(500 + random.nextInt(500));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//再拿右边的筷子
					synchronized(left) {
						System.out.println("哲学家" + id + "拿到筷子" + left);
						System.out.println(String.format("哲学家%d进餐中..", id));
						try {
							Thread.sleep(1000 + random.nextInt(2000));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(String.format("哲学家%d进餐完毕", id));
//						left.notifyAll();//这里是多余的，并没有需要通知的线程
					}
//					right.notifyAll();//这里是多余的，并没有需要通知的线程
				}
			}
			
			//思考中
			try {
				Thread.sleep(1000 + random.nextInt(1000));
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

public class PhilosopherProblem3 {
	
	
	public static void main(String[] args) {
		Integer[] forks = {0,1,2,3,4}; //5个信号量，运行时会分开加锁以演示死锁问题
		Runnable[]  philosophers = {new Philosopher3(forks, 0), new Philosopher3(forks, 1),
				new Philosopher3(forks, 2), new Philosopher3(forks, 3), new Philosopher3(forks, 4)};
		for (Runnable philosopher : philosophers) {
			Thread thread = new Thread(philosopher);
			thread.start();
		}
	}
	
}
