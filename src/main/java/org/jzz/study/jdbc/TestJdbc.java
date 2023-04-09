package org.jzz.study.jdbc;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jzz.study.util.Print;
import org.springframework.core.task.support.ExecutorServiceAdapter;

//原来的jdbc驱动已经升级了成 com.mysql.cj.jdbc.Driver
public class TestJdbc {
	
	static void testJdbc() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=utf8&serverTimezone=GMT%2B8","root","root"); 
		PreparedStatement  statement =  con.prepareStatement("select * from aa");	//不存在的表会报异常
		ResultSet resultset = statement.executeQuery();
		if (resultset == null) {
			Print.print("null table");	//空表不会生成空对象
		}
		while(resultset.next()) {
			String username = resultset.getString(2);	//下标从1开始
			Print.print(username);
		}
		resultset.close();
		statement.close();
		con.close();
	}
	
	static void testPoolDataSource() throws Exception {
		int ThreadCount = 20;
		PoolDataSource dataSource = new PoolDataSource();
		CountDownLatch latch = new CountDownLatch(ThreadCount);
		
		for (int i = 0; i < ThreadCount; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						PoolConnection connection = dataSource.getConnection();	//拿不到可用连接就会一致阻塞
						TimeUnit.SECONDS.sleep(1);
						connection.releaseConnection();
						latch.countDown();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
		
		try {
			latch.await();	//保证所有线程都结束了才结束控制台主线程，其实现象就是这句话在最后一句打印，可以调整线程数和latch数量不一致来观察
			System.out.println("**********************结束*********************************");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//prepareStatement使用示例
	static void testPrepareStatment() {
		String sql = "select * from xxx where a = ? and b = ? and time < ?";
//		PreparedStatement stmt = conn.prepareStatement(sql);
//        stmt.setInt(1, 61642);
//        stmt.setString(2, "MT202");
//        stmt.setDate(3, new Date(System.currentTimeMillis()));
//		ResultSet resultSet = stmt.executeQuery();
//		stmt.executeUpdate();
	}
	
	
	//测试分布式锁-数据库插入方案
	public void testLock() throws SQLException, InterruptedException {
		final String className = this.getClass().getName();
		final String method = "testLock";
		ExecutorService eService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 10 ; i++) {
			eService.execute(new Runnable() {
				@Override
				public void run() {
					Connection con = null;
					PreparedStatement  statement;
					String threadName = Thread.currentThread().getName();
					boolean hasLock = false;	
					try {
						//查找分布式锁时间
						System.out.println(threadName + "开始查找分布式锁");
						con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=utf8&serverTimezone=GMT%2B8","root","root");
						statement = con.prepareStatement("select createTime from demo.testLock where className = ? and method = ?");
						statement.setString(1, className);
						statement.setString(2, method);
						ResultSet resultSet = statement.executeQuery();
						if (resultSet.next()) {
							Date createTime = resultSet.getDate("createTime");
							System.out.println(threadName + "存在锁记录，createTime:" + createTime);
						}
						resultSet.close();
						statement.close();
						
						//拿到分布式方法锁
						boolean ok = false;
						int count = 0;
						while(count < 3) {
							System.out.println(threadName + "开始获取分布式锁");
							statement =  con.prepareStatement("insert into demo.testLock(className, method, createTime) values(?,?,?)");	//不存在的表会报异常
							statement.setString(1, className);
							statement.setString(2, method);
							statement.setDate(3,  new Date(System.currentTimeMillis()));
							try {
								statement.execute();
								statement.close();
								ok = true;
								break;
							} catch (SQLIntegrityConstraintViolationException e) {
								System.out.println(threadName + "获取锁被阻塞， 稍后重试");
								TimeUnit.SECONDS.sleep(new Random().nextInt(10));
								count ++;
							}
						}
						if (count >= 3) {
							System.out.println(threadName + "获取锁被阻塞，重试次数用尽，退出");
							return;
						}
						hasLock = true;
						
						//业务
						syncFunc();			
					} catch (Exception e) {
						e.printStackTrace();		
					} finally {
						if (hasLock) {
							//释放分布式锁
							try {
								System.out.println(threadName + "释放分布式锁");
								statement =  con.prepareStatement("delete from demo.testLock where className=? and method =?");
								statement.setString(1, className);
								statement.setString(2, method);
								statement.execute();
								statement.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
						try {
							con.close();
						} catch (Exception e2) {
						}
					}
				}
			});
		}
		eService.shutdown();
		eService.awaitTermination(Integer.MAX_VALUE,TimeUnit.SECONDS);
	}
	
	//测试分布式锁，模拟业务
	public void syncFunc() throws SQLException {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + "拿到了锁，准备执行业务 ");
		
		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=utf8&serverTimezone=GMT%2B8","root","root"); 
		PreparedStatement  statement =  con.prepareStatement("select * from demo.jobTable where name = ?");	//不存在的表会报异常
		statement.setString(1, "test");
		ResultSet resultset = statement.executeQuery();
		resultset.next();
		String status = resultset.getString("status");
		Date date = resultset.getDate("start_time");
		resultset.close();
		statement.close();
		
		if (date == null || "ready".equals(status)) {
			System.out.println(threadName + "符合作业运行条件，开始运行作业 ... ");
			
			//业务锁
			statement =  con.prepareStatement("update demo.jobtable set start_time = ?, status='running' where name = 'test'");	//不存在的表会报异常
			statement.setDate(1,  new Date(System.currentTimeMillis()));
			statement.execute();

			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(10));
			} catch (Exception e) {}
			
			statement = con.prepareStatement("update demo.jobtable set start_time = ?, status='ready',updateThread = ? where name = 'test'");	//不存在的表会报异常
			statement.setDate(1, new Date(System.currentTimeMillis()));
			statement.setString(2, threadName);
			statement.execute();
			
			System.out.println(threadName + "执行作业完成... !");
		} else {
			System.out.println(threadName + "作业已经被运行过，跳过 ");
		}
		
		try {
			con.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public static void main(String[] args) throws Exception {
//		testPoolDataSource();
		new TestJdbc().testLock();
	}
}
