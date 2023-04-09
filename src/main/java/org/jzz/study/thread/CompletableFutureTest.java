package org.jzz.study.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

//测试使用 java8 多线程工具
public class CompletableFutureTest {
	
	static ExecutorService executorService = Executors.newFixedThreadPool(10);
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
//		test1();
		
//		test2();
		
		test3();
	}
	
	
	//异步操作，谁先执行完就先获取谁的结果
	//CompletionService能够让异步任务的执行结果有序化。先执行完的先进入阻塞队列，利用这个特性，你可以轻松实现后续处理的有序性，避免无谓的等待，同时还可以快速实现诸如Forking Cluster这样的需求。
	static void  test1() throws InterruptedException, ExecutionException {
		CompletableFuture<String> futureTask1 = new CompletableFuture<>();
		CompletionService<String> cs = new ExecutorCompletionService(Executors.newCachedThreadPool());
		cs.submit(()->task("task1"));
		cs.submit(()->task("task2"));	
		cs.submit(()->task("task3"));		
		
		System.out.println(cs.take().get());
		System.out.println(cs.take().get());
	}
	
	
	//异步操作，只要有一个返回就退出（Dubbo中有一种叫做Forking的集群模式，这种集群模式下，支持并行地调用多个服务实例，只要有一个成功就返回结果。）
	static void  test2() throws InterruptedException, ExecutionException {
		// 创建线程池
		ExecutorService executor = Executors.newFixedThreadPool(3);
		// 创建CompletionService
		CompletionService<String> cs = new ExecutorCompletionService<>(executor); //使用指定的线程池，好于其默认的线程池
		// 用于保存Future对象
		List<Future<String>> futures = new ArrayList<>(3);
		//提交异步任务，并保存future到futures 
		futures.add(cs.submit(()->task("task1")));
		futures.add(cs.submit(()->task("task2")));
		futures.add(cs.submit(()->task("task3")));
		// 获取最快返回的任务执行结果
		String r = null;
		try {
		  // 只要有一个成功返回，则break
		  for (int i = 0; i < 3; ++i) {
		    r = cs.take().get();
		    //简单地通过判空来检查是否成功返回
		    if (r != null) {
		      break;
		    }
		  }
		} finally {
		  //取消所有任务
		  for(Future<String> f : futures)
		    f.cancel(true);
		}
		System.out.println(r);

	}
	
	//简单的任务串行
	static  void  test3() {
		List<CompletableFuture<String>> tasks = new ArrayList<>();
		tasks.add(CompletableFuture.supplyAsync(()->task("task1")));
		tasks.add(CompletableFuture.supplyAsync(()->task("task2")));
		tasks.add(CompletableFuture.supplyAsync(()->task("task3")));
		
//		CompletableFuture.allOf(tasks.get(0), tasks.get(1), tasks.get(2)) //当所有给定的 CompletableFuture 完成时，返回一个新的 CompletableFuture
		CompletableFuture.allOf(tasks.toArray(new CompletableFuture[tasks.size()])) //list转换为数组，这里数组也可以传0长度，则会新建一个数组
			.join(); //当前线程join到allOf返回的新线程（此线程需要所有线程完成时才返回）
		tasks.stream().forEach(t->{
			try {
				System.out.println(t.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
		
	}
	
	static String task(String taskName) {
		try {
		 TimeUnit.MILLISECONDS.sleep((long) (new Random(System.currentTimeMillis()).nextInt(1000)));
		} catch (InterruptedException e) {
		}
		return taskName;
	}
}
