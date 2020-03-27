package org.jzz.study.jdbc;

import java.sql.Connection;

/**
   * 连接池中的连接对象
 * */
public class PoolConnection {
	private Connection connection;	//真正的连接对象
	private boolean status; //连接状态
	
	public PoolConnection(Connection connect, boolean status) {
		this.connection = connect;
		this.status = status;
	}
	
	public Connection getConnection() {
		this.status = false;
		return this.connection;
	}
	
	public void setConnect(Connection connect) {
		this.connection = connect;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public void releaseConnection() {
		System.out.println("-----------释放连接-----------");
		this.status = true;
	}
}
