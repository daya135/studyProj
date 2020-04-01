package org.jzz.study.junit;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.jzz.study.util.Print;

/* 
JUnit 测试框架具有以下重要特性：
  测试工具
  测试套件
  测试运行器
  测试分类 :几种重要的分类如下：
    包含一套断言方法的测试断言
    包含规定运行多重测试工具的测试用例
    包含收集执行测试用例结果的方法的测试结果
*/
/** 测试运行器 用于执行测试案例  */
public class JunitTestRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(JunitTestSuite.class);
		for(Failure failure : result.getFailures()) {
			Print.print(failure.toString());
		}
		Print.print(result.wasSuccessful());
	}
}
