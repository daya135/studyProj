package org.jzz.study.test;

import static org.junit.Assert.assertEquals;	//静态引入方法，不用加类名就可调用，不推介使用
import org.junit.Assert;

import org.junit.Test;

public class TestJunit1 {

   String message = "TestJunit1"; 
	
   @Test
   public void testFunction1() { 
      System.out.println("TestJunit1.testFunction1");    
      Assert.assertEquals(message, "TestJunit1");     
   }
   
   @Test  
   public void testFunction2() { 
      System.out.println("TestJunit1.testFunction2");    
      assertEquals(message, "TestJunit11111");     
   }
}
