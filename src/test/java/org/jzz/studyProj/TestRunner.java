package org.jzz.studyProj;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.jzz.study.util.Print;

/* 
 JUnit 测试框架具有以下重要特性：
	测试工具
	测试套件
	测试运行器
	测试分类
*/
/** 测试运行器 用于执行测试案例 */
public class TestRunner {
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestJunit.class);
		for (Failure failure : result.getFailures()) {
			Print.print(failure.toString());
		}
		Print.print(result.wasSuccessful());
	}
}
