package org.jzz.study.net.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//接口代理类的父类，其中rpcRuest()方法用于构建msgBean，并通过socket连接远程的服务获取方法调用结果
public class ProxyBase {
	public static Object rpcQuest(String className, String methodName, String parmTypes, String returnType, Object[] args ) {
		Object obj = null;
		try {
			MsgBean msg = new MsgBean();
			msg.setClassName(className);
			msg.setMethodName(methodName);
			String[] parmTypeArr = parmTypes.split(",");
			Class[] classes = new Class[parmTypeArr.length];
			for (int i = 0; i < parmTypeArr.length; i++) {
				classes[i] = getClass(parmTypeArr[i]);
			}
			msg.setMethodParmType(classes);
			msg.setParams(args);
			Socket socket = new Socket("127.0.0.1", 10086);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(msg);
			socket.shutdownOutput();
			if (!"void".equals(returnType)) {
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				obj = in.readObject();
				socket.shutdownInput();
				in.close();
			}
			out.close();
			socket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return obj;
	}
	
	public static <T> T returnVal(Object obj, Class<T> T) {
		return (T)obj;
	}
	
	
	static Class getClass(String className) throws ClassNotFoundException {
		return Class.forName(className);
	}
}
