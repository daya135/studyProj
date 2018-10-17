//: net/mindview/util/Print.java
// Print methods that can be used without
// qualifiers, using Java SE5 static imports:
package org.jzz.study.util;
import java.io.*;

/**
 * @author Merin
 * @version 1.0.0
 */
public class Print {
  /** 
   * Print with a newline:
   * @param obj
   * @return void
   */
  public static void print(Object obj) {
    System.out.println(obj);
  }
  // Print a newline by itself:
  public static void print() {
    System.out.println();
  }
  // Print with no line break:
  public static void printnb(Object obj) {
    System.out.print(obj);
  }
  // The new Java SE5 printf() (from C):
  public static PrintStream
  printf(String format, Object... args) {
    return System.out.printf(format, args);
  }
  
  public static void PrintIntArr(int[] a) {
	  for (int i = 0; i < a.length; i++){
    	System.out.print(String.format("%d ", a[i]));
	  }
	  System.out.println();
  }
} ///:~
