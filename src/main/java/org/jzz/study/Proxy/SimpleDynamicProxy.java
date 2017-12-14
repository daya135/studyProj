package org.jzz.study.Proxy;

import java.lang.reflect.Proxy;

public class SimpleDynamicProxy {
	public static void consumer(Interface iface) {
		iface.doSomething();
		iface.somethingElse("jzz");
	}
	public static void main(String[] args) {
		RealObject realObj = new RealObject();
		consumer(realObj);
		Interface proxy = (Interface)Proxy.newProxyInstance(
				Interface.class.getClassLoader(), 
				new Class[]{Interface.class},
				new DynamicProxyHandler(realObj));
		consumer(proxy);	//调用(Interface)proxy的将方法全部转发给DynamicProxyHandler执行
	}
}
