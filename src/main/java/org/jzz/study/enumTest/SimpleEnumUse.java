package org.jzz.study.enumTest;

public class SimpleEnumUse {
	public static void main(String[] args) {
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

	}
}
