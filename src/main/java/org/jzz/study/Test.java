package org.jzz.study;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jzz.study.util.Print;
import org.springframework.util.StringUtils;

class testBean {
	private int age = 10;
	private String name = "test";
	
}

public class Test {
	
	private static final int[] a = {1,2};
	
	public static String reverse(String str) {
		if(str == null || str.length() < 2) {
			return str;
		}
		return reverse(str.substring(1)) + str.charAt(0);
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException  {
		//System.out.println();
		String str1 = "hello";
		String str = "你好你";
		Print.print(reverse(str1));
		byte[] bytes = str.getBytes("utf-8");
		for (byte b : bytes) {
			System.out.print(Integer.toHexString(b & 0x00FF));
		}
		
	}
}

