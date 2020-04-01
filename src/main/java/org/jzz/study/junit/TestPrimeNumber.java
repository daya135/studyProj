package org.jzz.study.junit;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;	//j静态引入
import org.jzz.study.algorithm.PrimeNumber;
import org.jzz.study.util.Print;

/**
  * 参数化测试
 * */
@RunWith(Parameterized.class)
public class TestPrimeNumber {
	
	private int number;
	private Boolean expectedResult;
	
	public TestPrimeNumber(int a, Boolean result) {
		this.number = a;
		this.expectedResult = result;
	}
	
	@Parameters
	public static Collection primeNumbers() {
		return Arrays.asList(new Object[][] {
			 { 2, true },
	         { 6, false },
	         { 19, true },
	         { 22, false },
	         { 23, true }
		});
	}
	
	@Test
	public void testPrimeNumberChecker() {
		Print.print("Parameterized Number is : " + number);
		assertEquals(expectedResult, PrimeNumber.validate1(number));
	}
}
