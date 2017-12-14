package org.jzz.study.string;

import java.util.Formatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringRexTest {
	
	private static void testFormat() {

		System.out.format("test format :%5s\n", "aa");
		System.out.format("test format :%5s\n", "bbbb");
		new Formatter(System.out).format("test formatter:%d,%s\n", 10, "aa");
		String str = String.format("[%s], [%s]", "aaa", "bbbb");
		System.out.println(str);
	}
	
	private static void testMatcher(String str) {	
		
		System.out.println("test matcher.find() matcher.group()");
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(str);
		//find方法用来寻找子串，每执行一次，index会往后移位
		while (matcher.find()) {
			//System.out.println(matcher.find());
			System.out.println(matcher.group(0));
		}
		System.out.println("======================");
		
		//匹配整个串的写法一，规定正则头尾，保证唯一性
		matcher = Pattern.compile("^a(\\w|\\s)*b$").matcher(str); 
		System.out.println(matcher.find());
		System.out.println(matcher.group());
		System.out.println("======================");
		
		//匹配整个串的写法二，用自带方法
		System.out.println(str.matches("^a(\\w|\\s)*b$"));
		System.out.println(str.matches("\\w+"));
		System.out.println("======================");
		
		str.replace("a+", "replace"); //字符串的任何方法不会更改原内容
		System.out.println(str);
		System.out.println(str.replace("aa", "replace"));//不支持正则的替换 
		System.out.println(str.replaceAll("a+", "replace")); //提供正则表达式支持的替换方法
		System.out.println(Pattern.compile("a+").matcher(str).replaceAll("replace"));//使用Pattern和matcher的替换
		
	}
	
	public static void regexTest() {
		String uri = "/crm-server/updateCustomer";
		String uri2 = "/crm/server/base/updateUser";
		String url = "updateCustomer";
		System.out.println(uri.matches("[/|\\w|-]+" + url + "$"));
		System.out.println(uri2.matches("[/|\\w|-]+" + url + "$"));
	}
	
	public static void main(String[] args) {
		//testFormat();
		//testMatcher("aa bb cc abc aaab");
		regexTest();
	}
	
}
