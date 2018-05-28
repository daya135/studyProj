package org.jzz.study.thread;

class User {
	int id;
	public User(int id) {
		this.id = id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}

class UserThreadLocal {
	private static ThreadLocal<User> userLocal  = new ThreadLocal<User>();
	public static User getUser() {
		return userLocal.get();
	}
	public static void setUser(User user) {
		userLocal.set(user);
	}
}

class Filter implements Runnable{
	
	@Override
	public void run() {
		User user = new User(1);
		UserThreadLocal.setUser(user);
	}
}

class Controller {
	
	
}

public class ThreadLocalTest {
	public static void main(String[] args) {
		new Thread(new Filter()).start();
	}
}
