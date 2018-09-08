package org.jzz.study.enumTest;

import java.util.EnumMap;
import java.util.HashMap;

public class SimpleEnumUse {
	public static void main(String[] args) {
		Spiciness spiciness;
		
		for (Spiciness s : Spiciness.values()) {	//values()方法，返回的数组顺序保持定义时的顺序
			System.out.println(s);	//自动添加的toString()方法
			System.out.println(s.ordinal());	//ordinal()方法。返回元素的index
		}
		Spiciness howHot = Spiciness.HOT;	//声明与引用
		switch (howHot) {	//枚举类型可用于switch
		case HOT:
			System.out.println("this is HOT");
			break;
		default:
			break;
		}
		
		//普通的HashMap
		HashMap<Spiciness, String> map = new HashMap<Spiciness, String>();
		map.put(Spiciness.HOT, "hot!!!!");
		System.out.println(map.get(Spiciness.HOT));
		
		//枚举Map，使用上没什么区别
		EnumMap<Spiciness, String> em = new EnumMap<Spiciness, String>(Spiciness.class);
		for (Spiciness s : Spiciness.values()) {
			em.put(s, s.toString());
		}
		System.out.println(em.get(Spiciness.HOT));
	}
}
