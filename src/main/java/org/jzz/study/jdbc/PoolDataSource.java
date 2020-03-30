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

/**
 * @author jzz
  *  数据库连接池的实现，ps：从网上抄的 https://blog.csdn.net/aloysjun/article/details/88716636
 */
public class PoolDataSource {
	private ReentrantLock lock = new ReentrantLock();
	private List<PoolConnection> list = Collections.synchronizedList(new ArrayList<PoolConnection>());
	
	private final static String DRIVER_CLASS = PropertiesHolder.getInstance().getProperty("jdbc.driver_class");
	private final static String URL = PropertiesHolder.getInstance().getProperty("jdbc.url");
	private final static String USERNAME = PropertiesHolder.getInstance().getProperty("jdbc.username");
	private final static String PASSWORD = PropertiesHolder.getInstance().getProperty("jdbc.password");
	
	//定义默认连接池属性配置
	private int initSize = 2;
	private int maxSize = 4;
	private int stepSize = 1;
	private int timeout = 2000;
	
	public PoolDataSource() {
		initPool();
	}
	
	//初始化连接池
	private void initPool() {
		String init = PropertiesHolder.getInstance().getProperty("initSize");
		String step = PropertiesHolder.getInstance().getProperty("stepSize");
		String max = PropertiesHolder.getInstance().getProperty("maxSize");
		String time = PropertiesHolder.getInstance().getProperty("timeout");
		
		initSize = init==null? initSize : Integer.parseInt(init);
		maxSize = max==null? maxSize : Integer.parseInt(max);
		stepSize = step==null? stepSize : Integer.parseInt(step);
		timeout = time==null? timeout : Integer.parseInt(time);
		
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

//配置文件类
class PropertiesHolder {
	private static Properties pro = new Properties();
	static{
		try {
			String pathString = PropertiesHolder.class.getResource("/").getPath();//只到达包所在的路径
//			String pathString = PropertiesHolder.class.getResource("").getPath();//到达class文件目录下
			Print.print("properties path: " + pathString);
			pro.load(new FileInputStream(pathString + "/datasource.properties"));	//maven将配置文件放到项目根目录（目标文件夹）
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Properties getInstance() {
		return pro;
	}
}