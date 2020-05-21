package org.jzz.study.json;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jzz.study.util.Print;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 测试匿名内部类初始化容器，序列化问题</br>
 * 实例初始化块 (instance initializer block)
 new HashMap<String, String>() {{</br>
	put(“Name”, “June”);</br>
	put(“QQ”, “2572073701”);</br>
 }};</br>
 根据博客https://blog.csdn.net/hellojoy/article/details/103983617提到：</br>
 （匿名内部类持有了外部类的引用，序列化内部类时会自动序列化外部类，如果外部类没有实现序列化接口则会报错。 ）</br>
 测试结果：jackson序列化正常，不会报错。。</br>
 
 * */
public class InnerCollectionTest {
	String name;
	InnerCollectionTest(String name) {
		this.name = name;
	}
	
	{
		Print.print("这是主类的——实例初始化块" + this.name);
	}
	
	static {
		Print.print("这是主类的——静态初始化块" + InnerCollectionTest.class.getSimpleName());
	}
	
	//匿名内部类 + 静态代码块初始化list
	Map<String, String> map = new HashMap<String, String>(2){{
		put("key", "value");
	}};
	
//	Map<String, String> map2 = {"map2" : "value2"};	//这种写法不行jdk1.8
//	List<Integer> list2 = [1];//这种写法不行jdk1.8
	
	//匿名内部类 + 静态代码块初始化list
	List list = new ArrayList<String>(1) {{
		for (int i = 0; i < 10; i++) {
			add("item" + i);
		}
	}};
	
	public static void main(String[] args) throws JsonProcessingException {
		InnerCollectionTest test = new InnerCollectionTest("1");
		test = new InnerCollectionTest("2");
		ObjectMapper mapper = new ObjectMapper();
		Print.print(mapper.writeValueAsString(test.map));
		Print.print(mapper.writeValueAsString(test.list));
	}
}
