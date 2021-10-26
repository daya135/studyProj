package org.jzz.studyProj;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

import javax.sound.midi.Soundbank;

import org.jzz.study.jdbc.PoolConnection;
import org.jzz.study.net.rpc.MsgBean;
import org.jzz.study.net.rpc.ProxyBase;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;


interface MyInterface {
	String doSomething(String arg);
	int doSomething2(int a, int b);
}

class MyInterfaceClass implements MyInterface{
	public String doSomething(String arg) {
		System.out.println("origin class dosomething(" + arg + ")");
		return "origin class dosomething(" + arg + ")";
	}

	@Override
	public int doSomething2(int a, int b) {
		return a * b;	
	}
}

class OtherClass {
	public void doSomething(String arg) {
		System.out.println("other class's func");
	}
}

class MyProxyHandler implements InvocationHandler {
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("MyProxyHandler.invoke");
		System.out.println(method.getName());
		System.out.println(proxy.getClass().getName());
		Class[] inters = proxy.getClass().getInterfaces();
		for (Class cl : inters) {
			System.out.println(cl.getName());
		}
		MsgBean msg = new MsgBean();
		msg.setClassName(proxy.getClass().getInterfaces()[0].getName());
		msg.setMethodName(method.getName());
		msg.setMethodParmType(method.getParameterTypes());
		msg.setParams(args);
		
		System.out.println(msg);
		
		return 1; 
	}
}

public class TestProxy {
	
	public void func(String arg) { 
		System.out.println("this is TestProxy's func(" + arg + ")");
	}
	
	static Object javassistProxy(Class t) throws Exception {
		ClassPool classPool = new ClassPool();
		String fullName = t.getPackage().getName() + ".$proxy" + t.getSimpleName() + "Impl";
		CtClass ctClass = classPool.makeClass(fullName);
		ClassPath classPath = new ClassClassPath(OtherClass.class);
		classPool.insertClassPath(classPath);		//手动添加classPath，后面getCtClass才能正常加载
		ctClass.setSuperclass(classPool.getCtClass(OtherClass.class.getName()));
		//新增方法
		CtMethod ctNewMethod = CtNewMethod.make("public void testAddFunc(){System.out.println(\"this is test add method\");}", ctClass);
		ctClass.addMethod(ctNewMethod);
		
		Class cl = ctClass.toClass();
		return cl.newInstance();
	}
	
	public static void main(String[] args) throws Exception{
		MyInterface obj = (MyInterface)Proxy.newProxyInstance(TestProxy.class.getClassLoader(), new Class[] {MyInterface.class},
				new MyProxyHandler());
		obj.doSomething2(1, 2);
		
		
		System.out.println("+++++++++++++++++++++++++++++++++");
		Object object = javassistProxy(MyInterface.class);
		Class cl = object.getClass();
		System.out.println(cl.getName());
		Method[] methods = cl.getDeclaredMethods();
		
		for (Method method : methods) {
			System.out.println(method.getName());
			method.invoke(cl.newInstance(), null);
		}
	}
}
