package org.jzz.study.thread;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

import org.jzz.study.util.Print;

/**
 * 混合使用CountDownLatch 与 CyclicBarrier
 * CountDownLatch :相当于计数器，线程完成一个记录一个(latch.countDown)，计数器递减，只能只用一次
 * CyclicBarrier ：计数器更像一个阀门，需要所有线程都到达（barrier.await();），然后继续执行，计数器递增，提供reset功能，可以多次使用。
 * 另一个例子：HorseRace
 */
public class CyclicBarrierTest {
	static class MyThread extends Thread {
		CyclicBarrier barrier;
		CountDownLatch latch;
		Random random = new Random(System.currentTimeMillis());
		int times[] = {random.nextInt(1000), random.nextInt(1000)};
		public MyThread(CyclicBarrier barrier, CountDownLatch latch) {
			this.barrier = barrier;	
			this.latch = latch;
		}
		
		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(times[0]);
				Print.print(Thread.currentThread().getName() + " 到达栅栏A " + times[0]);
				barrier.await();	//等待线程组的其它线程到达此处
				Print.print(Thread.currentThread().getName() + " 冲破栅栏A");
				
				TimeUnit.MILLISECONDS.sleep(times[1]);
				Print.print(Thread.currentThread().getName() + " 到达栅栏B " + times[1]);
				barrier.await();
				Print.print(Thread.currentThread().getName() + " 冲破栅栏B");
				
				latch.countDown();	//计数器减1
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		int threadNum = 5;
		CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {
			@Override
			public void run() {
				Print.print(Thread.currentThread().getName() + " 最后到达栅栏");
			}
		});
		CountDownLatch latch = new CountDownLatch(threadNum);
		
		for (int i = 0; i < threadNum; i++) {
			new MyThread(barrier, latch).start();
		}
		try {
			Print.print("等待所有线程执行完毕");
			latch.await();		//等待计数器归零
			Print.print("所有线程执行完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

