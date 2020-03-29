package org.jzz.studyProj;

import static org.junit.Assert.*;

import org.junit.Test;
import org.jzz.study.util.Print;

/** */
public class TestJunit {

	@Test
	public void test() {
		Print.print("执行单元测试");
		String str= "Junit is working fine";
	    assertEquals("Junit is working fine",str);
	}

}
