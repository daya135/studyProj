package org.jzz.study.test;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;	//静态引入方法，不用加类名就可调用，不推介使用

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** 测试套件意味捆绑几个测试案例并且同时运行  */
@RunWith(Suite.class)
@SuiteClasses({TestJunit1.class, TestJunit2.class})
public class JunitTestSuite {
	
}

class TestJunit2 {

   String message = "TestJunit2"; 
	
   @Test
   public void testPrintMessage() { 
      System.out.println("TestJunit2.testPrintMessage");    
      assertEquals(message, "TestJunit2");     
   }
}


