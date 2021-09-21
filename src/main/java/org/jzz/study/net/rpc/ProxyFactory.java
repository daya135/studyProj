package org.jzz.study.net.rpc;

import javassist.*;

public class ProxyFactory<T> {
	private static final String PROXYFREIX = "$ProxyFactory";
	private static final String PROXYSUFFIX = "Impl";
	
	private Class t;
	
	public ProxyFactory(Class t) {
		this.t = t;
	}
	
	public T getProxyObject() {
		T proxyObject = null;
		
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = pool.makeClass(getPackageName() + "." + getProxyObjectName());
		
		
		try {
			//设置代理类的父类
			ctClass.setSuperclass(pool.getCtClass(ProxyBase.class.getName()));
			//设置代理类继承的接口
			CtClass inter = pool.getCtClass(getPackageName() + "." + t.getSimpleName());
			CtClass[] intes = new CtClass[] {inter};
			ctClass.setInterfaces(intes);
			
			//测试新增的方法
			CtMethod ctNewMethod = CtNewMethod.make("public void test(){System.out.println(\"test add method\")}", null);
			ctClass.addMethod(ctNewMethod);

			//实现接口的各个方法
			CtMethod[] methods = ctClass.getDeclaredMethods();
			for (CtMethod method : methods) {
				
			}
			
		} catch (CannotCompileException | NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return proxyObject;
	}
	
	/** 获取包名 */
	public String getPackageName() {
		Package pag = t.getPackage();
		return pag.getName();
	}
	
	/** 获取代理对象名称 */
	public String getProxyObjectName() {
		return PROXYFREIX + t.getSimpleName() + PROXYSUFFIX;
	}
	
}
