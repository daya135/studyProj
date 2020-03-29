package org.jzz.study.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.jzz.study.util.Print;

//原来的jdbc驱动已经升级了成 com.mysql.cj.jdbc.Driver
public class TestJdbc {
	
	static void testJdbc() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/robot?characterEncoding=utf8&serverTimezone=GMT%2B8","root","root"); 
		PreparedStatement  statement =  con.prepareStatement("select * from t_device");
		ResultSet resultset = statement.executeQuery();
		while(resultset.next()) {
			String deviceName = resultset.getString(2);
			Print.print(deviceName);
		}
		resultset.close();
		statement.close();
		con.close();
	}
	
	static void testPoolDataSource() {
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
	
	public static void main(String[] args) throws Exception {
//		testJdbc();	
		testPoolDataSource();
		
	}
}
