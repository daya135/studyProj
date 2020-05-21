package org.jzz.study;

/**
 * 测试检查异常与运行时异常
 * */
public class ExceptionTest {
	void func1() {
		throw new RuntimeException("运行时异常");	//运行时异常，不用添加throws声明
	}
	
	void func2() throws MyException{
		throw new MyException("非运行时异常");
	}
	
	void func3() {
		func1();
	}
	
	void func4() throws Exception{	
		func2();	//编译时检查异常，必须添加throws声明
	}
	
}

class MyException extends Exception{
	private static final long serialVersionUID = 1L;

	public MyException(String msg) {
		super(msg);
	}
}