package org.jzz.study.net.rpc;

import javassist.*;

/** 生成代理类的工厂 */
public class ProxyFactory<T> {
	private static final String PROXYFREIX = "$ProxyFactory";
	private static final String PROXYSUFFIX = "Impl";
	
	private Class t;
	
	public ProxyFactory(Class t) {
		this.t = t;
	}
	
	/** 获取代理对象名称 */
	public String getProxyObjectName() {
		return PROXYFREIX + t.getSimpleName() + PROXYSUFFIX;
	}
	
	public T getProxyObject() {
		T proxyObject = null;	//生成的代理对象
		
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = pool.makeClass(t.getPackage().getName() + "." + getProxyObjectName());
		
		try {
			//设置代理类的父类
			ctClass.setSuperclass(pool.getCtClass(ProxyBase.class.getName()));
			//设置代理类继承的接口
			CtClass inter = pool.getCtClass(t.getPackage().getName() + "." + t.getSimpleName());
			CtClass[] intes = new CtClass[] {inter};
			ctClass.setInterfaces(intes);
			
			//测试新增的方法
			CtMethod ctNewMethod = CtNewMethod.make("public void test(){System.out.println(\"test add method\");}", ctClass);
			ctClass.addMethod(ctNewMethod);

			//实现接口的各个方法
			CtMethod[] methods = inter.getDeclaredMethods();
			for (CtMethod method : methods) {
				CtMethod cm = new CtMethod(method.getReturnType(), method.getName(), method.getParameterTypes(), ctClass);
				String returnType = method.getReturnType().getName();
				String paramTypes = "";
				StringBuilder sb = new StringBuilder("");
				for (CtClass aClass : method.getParameterTypes()) {	//此处为啥脱裤子放屁，到了那边又还原为数组，直接传数组过去不行吗？？？
					sb.append(aClass.getName());
					sb.append(",");
				}
				paramTypes = sb.substring(0, sb.lastIndexOf(","));
				sb = new StringBuilder("{");
				sb.append("String className=\"" + t.getName() + "\";");
				sb.append("String methodName=\"" + method.getName() + "\";");
				sb.append("String returnType=\"" + returnType + "\";");
				sb.append("String paramTypes=\"" + paramTypes + "\";");
				sb.append("Object[] args = new Object[]{");
				for (int i = 1; i <= method.getParameterTypes().length; i++) {		//此处${1}怎么替换为arg[]数组的？？
					sb.append("$" + i);
					if (i != method.getParameterTypes().length) {
						sb.append(",");
					}
				}
				sb.append("};");
				sb.append("Object obj = rpcQuest(className, methodName, paramTypes, returnType, args);");	//在新加的方法内调用了父类的方法！！精妙
				if (!"void".equals(returnType)) {
					sb.append("return (" + returnType + ")obj;");
				}
				sb.append("}");
				cm.setBody(sb.toString());
				ctClass.addMethod(cm);
				System.out.println(cm.getName());
				System.out.println(sb);
				
			}
			Class aClass = ctClass.toClass();
			proxyObject = (T)aClass.newInstance();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return proxyObject;
	}

	/** 获取包名 */
	public String getPackageName() {
		Package pag = t.getPackage();
		return pag.getName();
	}
	
}
