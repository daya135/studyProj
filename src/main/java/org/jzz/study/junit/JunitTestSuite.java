package org.jzz.study.junit;

import org.junit.Assert;
import org.junit.BeforeClass;



import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/** 测试套件意味捆绑几个测试案例并且同时运行  */
@RunWith(Suite.class)
@SuiteClasses({TestPrimeNumber.class, TestJunit1.class})
public class JunitTestSuite {
	
}


