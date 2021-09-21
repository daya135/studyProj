package org.jzz.study.net.rpc;

import java.io.Serializable;

//RPC消息传输实体
public class MsgBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String className;
	private String methodName;
	private Class[] methodParmType;
	private Object[] params;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class[] getMethodParmType() {
		return methodParmType;
	}
	public void setMethodParmType(Class[] methodParmType) {
		this.methodParmType = methodParmType;
	}
	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
	}
	
	
	
}
