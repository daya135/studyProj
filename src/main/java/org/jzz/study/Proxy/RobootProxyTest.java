package org.jzz.study.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jzz.study.util.Null;

/** 代理类，将一切robot实例代理成NullRobot */
class NullRobotProxyHandler implements InvocationHandler {
	private String nullName;
	private Robot nRobbot = new NullRobot();

	public NullRobotProxyHandler(Class<? extends Robot> type) {
		nullName = type.getSimpleName() + " NullRobot";
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(nRobbot, args);
	}

	/* 空Robot类 */
	private class NullRobot implements Null, Robot {
		@Override
		public String name() {
			return nullName;
		}

		@Override
		public String model() {
			return nullName;
		}

		@Override
		public List<Operation> operations() {
			return Collections.emptyList();
		}
	}
}

public class RobootProxyTest {

	/* 返回一个代理对象 */
	public static Robot newNullRobot(Class<? extends Robot> type) {
		return (Robot) Proxy.newProxyInstance(RobootProxyTest.class.getClassLoader(),
				new Class[] { Null.class, Robot.class }, new NullRobotProxyHandler(type));
	}

	public static void main(String[] args) {
		System.out.println("代理前。。。。");
		Robot.Test.test(new SnowRemovalRobot("SnowBee"));
		System.out.println("代理后。。。。");
		Robot.Test.test(newNullRobot(SnowRemovalRobot.class));
	}
}

class SnowRemovalRobot implements Robot {
	private String name;

	public SnowRemovalRobot(String name) {
		this.name = name;
	}

	public String name() {
		return name;
	}

	public String model() {
		return "SnowBot Series 11";
	}

	public List<Operation> operations() {
		return Arrays.asList(new Operation() {
			public String description() {
				return name + " can shovel snow";
			}

			public void command() {
				System.out.println(name + " shoveling snow");
			}
		}, new Operation() {
			public String description() {
				return name + " can chip ice";
			}

			public void command() {
				System.out.println(name + " chipping ice");
			}
		}, new Operation() {
			public String description() {
				return name + " can clear the roof";
			}

			public void command() {
				System.out.println(name + " clearing roof");
			}
		});
	}
}