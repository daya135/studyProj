package org.jzz.study.junit;

import static org.junit.Assert.assertEquals;	//静态引入方法，不用加类名就可调用，不推介使用

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.jzz.study.util.Print;
import static org.junit.Assert.assertEquals;	//静态引入方法，不用加类名就可调用，不推介使用

/** 
 * 测试单元，可以被JunitRunner调度，也可以在eclipse中右键执行run with junitTest
 * */
public class TestJunit1 {

   @Test
   public void testFunction1() { 
      System.out.println("TestJunit1.testFunction1");    
      assertEquals(5, UtilClassForTest.division(10, 2), 0.000001);     
   }
   
   @Test(expected = ArithmeticException.class)	//异常测试
   public void testFunction2() { 
      System.out.println("TestJunit1.testFunction2");    
      UtilClassForTest.division(1, 0);  
   }
   
   @BeforeClass
   public static void beforeClass() {
	   Print.print("beforeClass " + TestJunit1.class.getName());
   }
   
   @AfterClass
   public static void afterClass() {
	   Print.print("afterClass " + TestJunit1.class.getName());
   }
   
   @After
   public void after() {
	   Print.print("after");
   }
   
   @Before
   public void before() {
	   Print.print("before");
   }
}
