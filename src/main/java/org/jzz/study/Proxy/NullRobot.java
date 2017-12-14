package org.jzz.study.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;

import org.jzz.study.util.Null;

class NullRobotProxyHandler implements InvocationHandler{
	private String nullName;
	private Robot proxid = new NRobot();
	
	public NullRobotProxyHandler(Class<? extends Robot> type) {
		nullName = type.getSimpleName() + " NullRobot";
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(proxid, args);
	}
	
	/* 空Robot类 */
	private class NRobot implements Null, Robot{
		@Override
		public String name() { return nullName; }

		@Override
		public String model() { return nullName; }

		@Override
		public List<Operation> operations() {
			return Collections.emptyList();
		}
		
	}
}

public class NullRobot {
	
	/* 返回一个空Robot的代理对象*/
	public static Robot newNullRobot(Class<? extends Robot> type) {
		return (Robot)Proxy.newProxyInstance(
				NullRobot.class.getClassLoader(), 
				new Class[]{ Null.class, Robot.class },
				new NullRobotProxyHandler(type));
	}
	
	public static void main(String[] args) {
		Robot.Test.test(new SnowRemovalRobot("SnowBee"));
		Robot.Test.test(newNullRobot(SnowRemovalRobot.class));
	}
}