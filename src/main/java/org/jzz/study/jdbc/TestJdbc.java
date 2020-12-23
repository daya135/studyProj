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
	
	public static void main(String[] args) throws Exception {
//		testPoolDataSource();
		
	}
}
