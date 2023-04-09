package org.jzz.study.thread;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CountDownLatchTest {
	
	static ExecutorService executorService = Executors.newFixedThreadPool(10);
	
	public static void main(String[] args) throws InterruptedException {
		int len = 208;
		String[] sources = new String[len];
		String[] ret = new String[sources.length];
		
		IntStream.range(0, sources.length).forEach(x->sources[x]=String.valueOf(x));
		System.out.println("source: " + Arrays.toString(sources));
	
		int[][] interval = new int[(sources.length + 19) / 20][2];
		CountDownLatch latch = new CountDownLatch(interval.length);
		for (int i=0; i < interval.length; i++) {
			interval[i][0] = i*20;
			if (interval[i][0] + 20 <= sources.length) {
				interval[i][1] = interval[i][0] + 20;
			} else {
				interval[i][1] = sources.length;
			}
			
			final int idx = i;
			executorService.execute(()->{
				doSomething(interval[idx], sources, ret, latch);
			});
		}
		
		latch.await();
		System.out.println("ret: " + Arrays.toString(ret));
	}
	
	public static void testCompleteableFuture(MyTask[] myTasks, CountDownLatch latch) {
		
	}
	

	//每个线程：处理数组指定下标区间中的元素，返回值放到数组指定下标区间，这样就省去了各种同步容器，速度非常快
	static void doSomething(int[] interval, String[] strings, String[] ret, CountDownLatch latch) {
		StringBuilder builder = new StringBuilder();
		for(int i = interval[0]; i < interval[1]; i++) {
			builder.append(strings[i]);
			ret[i] = strings[i];
		}
		try {
			TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("this is task :" + Arrays.toString(interval) + ", " + builder.toString());	
		latch.countDown();
	}
}
