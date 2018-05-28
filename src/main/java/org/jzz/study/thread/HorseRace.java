package org.jzz.study.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Horse implements Runnable {
	private static int counter = 0;
	private final int id = counter++;
	private int strides = 0;
	private static Random rand = new Random(47);
//	private static CyclicBarrier barrier; //这里，书上是使用static修饰，但去掉也没问题，使用static意义在哪
	private CyclicBarrier barrier;
	
	public Horse(CyclicBarrier barrier) {
		this.barrier = barrier;
	}
	public synchronized int getStrides() {
		return strides;
	}
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized (this) {
					strides += rand.nextInt(3);
				}
				barrier.await(); //阻塞当前线程，等待所有线程达到此位置后再执行
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public String tracks() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < getStrides(); i++) {
			s.append("*");
		}
		s.append(id);
		return s.toString();
	}
	public String toString() {
		return "Horse " + id + " "; 
	}
}

public class HorseRace {
	static final int FINSH_LINE = 75;
	private List<Horse> horses = new ArrayList<Horse>();
	private ExecutorService exec = Executors.newCachedThreadPool();
	private CyclicBarrier barrier;
	public HorseRace (int nHorse, final int pause) {
		//这里的run方法是所有线程达到某状态后自动执行的内容（先于等待线程的第二轮执行）
		barrier = new CyclicBarrier(nHorse, new Runnable() {
			public void run() {
				StringBuilder s = new StringBuilder();
				for(int i = 0; i < FINSH_LINE; i++) {
					s.append("=");
				}
				System.out.println(s.toString());
				for (Horse horse : horses) {
					System.out.println(horse.tracks());
				}
				for (Horse horse : horses) {
					if (horse.getStrides() >= FINSH_LINE) {
						System.out.println(horse + "won!");
						exec.shutdownNow();
						return;
					}
				}
		        try {
		            TimeUnit.MILLISECONDS.sleep(pause);
		        } catch(InterruptedException e) {
		        	  System.out.println("barrier-action sleep interrupted");
		     	}
			}
		});
		for (int i = 0; i < nHorse; i++) {
			Horse horse = new Horse(barrier);
			horses.add(horse);
			exec.execute(horse);
		}
	}
	public static void main(String[] args) {
		new HorseRace(6, 200);
	}
}
