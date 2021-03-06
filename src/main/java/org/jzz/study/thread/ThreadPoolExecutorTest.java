package org.jzz.study.thread;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.jzz.study.util.CompareUtil;
import org.jzz.study.util.Print;

/**
 * ThreadPoolExecutor是最原始的线程池类，Executors.newXXXThreadPool也是通过配置不同的内置参数实现的不同线程池（但可能有OOM问题），如下：
 * 1.FixedThreadPool
 * 2.CachedThreadPool
 * 3.SingleThreadExecutor
 * 4.ScheduledThreadPool
 * （可以观察下以上几个类的继承关系，有些和ThreadPoolExecutor处于同一层，都是ExecutorService接口的实例）
 */
public class ThreadPoolExecutorTest {
    static int corePoolSize = 2;	//核心线程池大小
    static int blockingQueueSize = 2; //阻塞队列大小
    static int maximumPoolSize = 6;	//最大线程池大小
    static long keepAliveTime = 10;	//线程最大空闲时间，对核心线程无效
    
    /** 测试ExecutorService.execute()方法 */
    public static void testExecute(ExecutorService executor) {
    	for (int i = 20; i > 0; i--) {
			MyTask task = new MyTask(i);
			executor.execute(task);		//连续提交任务也会成功，不影响的
//			executor.execute(task);		//连续提交任务也会成功，不影响的
		}
    }
    
    /** 测试ExecutorService.submit()方法 */
    public static void testSubmit(ExecutorService executor) {
    	int count = 6;
    	ArrayList<FutureTask<Integer>> futureTasks = new ArrayList<FutureTask<Integer>>(count);
    	for (int i = count; i > 0; i--) {	
//    		MyTask task = new MyTask(i);	//Runable对象也能使用submit提交，但是FutureTask.get只能返回空值，好处是也可以使用FutureTask.isDone判断线程是否完成
			MyCallable task = new MyCallable("myCallable", i * 10); //倒序，让时间长的计算先进任务（观察结果，6、5开始后为什么3、4被跳过了，先执行2、1呢，因为3、4在队列中执行到2、1就增加了线程数量）
			futureTasks.add((FutureTask<Integer>)executor.submit(task));	//submit返回一个FutureTask对象，需要强制转型
		}
    	boolean isAllDone = false;
    	boolean[] done = new boolean[count];
    	Arrays.fill(done, false);
    	//采用轮询方式获取线程返回值,可以实现先处理已经完成的返回结果
    	while (!isAllDone) {
    		isAllDone = true;
			for (int i = 0; i < futureTasks.size(); i++) {
				FutureTask futureTask = futureTasks.get(i);
				if(!futureTask.isDone()) {
					isAllDone = false;
				} else {
					if (!done[i]) {
						done[i] = true;
						try {
							Print.print(futureTask.toString()  + futureTask.get());
						} catch (InterruptedException e) {
							e.printStackTrace();
						} catch (ExecutionException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
    	Print.print("ThreadPoolExecutorTest.testSubmit() is over");
    }

	public static void main(String[] args) {
		ExecutorService executor;
		RejectedExecutionHandler handler; //拒绝策略，超过线程等待队列大小则进入此方法
		ThreadFactory threadFactory = new MyThreadFactory();	//线程创建工厂，可以不指定
		BlockingQueue<Runnable> blockingQueue;	//阻塞队列,类型强制要求，是影响线程调度的重要因素，有很多文章可以做
		
//		blockingQueue = new ArrayBlockingQueue<Runnable>(blockingQueueSize);	//必须指定容量参数，因为是有界队列
//		blockingQueue = new LinkedBlockingDeque<Runnable>();	//可以不指定容量，则为无界队列，这种情况只能有核心线程（因为无法决定该开多少非核心线程）
		//关于PriorityBlockingQueue，这里有一个疑问，不是应该指定类型为comparator吗？但看了类头的注释，意思是要自己保证为comparable或者提供一个comparator？
		//使用submit提交任务时，会有类型转化的问题，因为FutureTask没有实现Comparable接口
		//由于是堆实现，指定的其实是堆的初始容量，而不是上限！！
		blockingQueue = new PriorityBlockingQueue<Runnable>(blockingQueueSize);	
//		blockingQueue = new SynchronousQueue<Runnable>(); //非常特殊的队列，没有数据缓存的空间。逻辑很特殊上网看吧
		
		handler = new MyIgnorePolic();	
		//四种默认的线程拒绝策略
//		handler = new ThreadPoolExecutor.AbortPolicy();	//默认策略，超过则拒绝，抛出异常
//		handler = new ThreadPoolExecutor.CallerRunsPolicy();	//使用调用者线程来执行任务
//		handler = new ThreadPoolExecutor.DiscardPolicy();	//丢弃任务且不抛出异常
//		handler = new ThreadPoolExecutor.DiscardOldestPolicy();	//丢弃阻塞队列中靠前 的任务，尝试添加，如果失败则不断重复
		
//		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, 
//				workQueue, threadFactory, handler);
		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, 
				blockingQueue, threadFactory, handler);
		//四种默认的线程池
//		executor = Executors.newFixedThreadPool(5);	//没有指定线程工厂，则不会进入自定义线程工厂的逻辑
//		executor = Executors.newCachedThreadPool(threadFactory);
//		executor = Executors.newSingleThreadExecutor();
//		executor = Executors.newScheduledThreadPool(5, threadFactory);
		
		testExecute(executor);
//		testSubmit(executor);
		
		executor.shutdown();//已经提交的线程会继续执行，新来的线程会被拒绝. 不 执行这句则主线程不会结束
	}
}

/** 
 * 自定义任务，注意不是线程而是任务 
 * 实现Comparable接口以用来，实现任务优先级
 */
class MyTask implements Runnable, Comparable<MyTask>{
	String name = "MyTask";

	public MyTask(int id) {
		this.name = name.concat(String.valueOf(id));
	}

	@Override
	public void run() {
		Print.print(name.concat(" is start..."));
		try {
			TimeUnit.SECONDS.sleep(new Random().nextInt(10));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Print.print(name.concat(" is end !"));
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public int compareTo(MyTask o) {
		return this.name.compareTo(o.name);
	}

}

/** 自定义线程工厂 */
class MyThreadFactory implements ThreadFactory {

	private final AtomicInteger mThreadNum = new AtomicInteger(0);

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
		Print.print(t.getName() + " has been created");
		return t;
	}

}

/** 线程拒绝策略
 * 有四种策略，请看子类
 * */
class MyIgnorePolic implements RejectedExecutionHandler{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		//自定义拒绝策略
		doLog(r, executor);
	}
	
	 // 可做日志记录等
	private void doLog(Runnable r, ThreadPoolExecutor e) {
		Print.print(r.toString() + " has been rejected");
	}
}


