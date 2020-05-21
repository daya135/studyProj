package org.jzz.study.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.jzz.study.util.Print;
import org.jzz.study.util.PropertiesHolder;

/**
 * @author jzz
  *  数据库连接池的实现，ps：从网上抄的 https://blog.csdn.net/aloysjun/article/details/88716636
 */
public class PoolDataSource {
	private ReentrantLock lock = new ReentrantLock();
	private List<PoolConnection> list = Collections.synchronizedList(new ArrayList<PoolConnection>());
	
	private final String DRIVER_CLASS;
	private final String URL;
	private final String USERNAME ;
	private final String PASSWORD;
	private final int initSize;
	private final int  maxSize;
	private final int  stepSize;
	private final int  timeout;
	
	public PoolDataSource() throws Exception {
		Properties prop = PropertiesHolder.getProperties("config.properties");
		DRIVER_CLASS = prop.getProperty("jdbc.driver_class");
		URL = prop.getProperty("jdbc.url");
		USERNAME = prop.getProperty("jdbc.username");
		PASSWORD = prop.getProperty("jdbc.password");
		initSize = Integer.valueOf(prop.getProperty("jdbc.initSize"));
		maxSize = Integer.valueOf(prop.getProperty("jdbc.stepSize"));
		stepSize = Integer.valueOf(prop.getProperty("jdbc.maxSize"));
		timeout = Integer.valueOf(prop.getProperty("jdbc.timeout"));
		initPool();
	}
	
	//初始化连接池
	private void initPool() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public PoolConnection getConnection() {
		PoolConnection poolConnection = null;
		try {
			lock.lock();
			if (list.size() == 0) {
				createConnection(initSize);
			}
			poolConnection = getAvailableConnection();
	
			while(poolConnection == null){
				System.out.println("---------------等待连接---------------");
				createConnection(stepSize);	//尝试创建新连接，达到最大数量则不会创建
				poolConnection = getAvailableConnection();	//尝试获取可用连接，包括新建的和被释放的
				
				if(poolConnection == null){
					TimeUnit.MILLISECONDS.sleep(30);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return poolConnection;
	}
	
	private void createConnection(int count) throws SQLException {
		if (list.size() + count <= maxSize) {
			for(int i = 0; i < count; i++) {
				System.out.println("初始化第"+ list.size() + " 个连接");
				Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
				PoolConnection pool = new PoolConnection(conn, true);
				list.add(pool);
			}
		}
	}
	
	PoolConnection getAvailableConnection() throws SQLException {
		for (PoolConnection pool : list) {
			if (pool.isStatus()) {
				Connection connection = pool.getConnection();
				if (!connection.isValid(timeout)) {
					pool.setConnect(DriverManager.getConnection(URL, USERNAME, PASSWORD));
				}
				return pool;
			}
		}
		return null;
	}

}

