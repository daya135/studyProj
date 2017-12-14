package org.jzz.study.clone;

/*
 * 使用Cloneable接口实现对象深度复制,实现Cloneable接口并重写Object类中的clone()方法
 * by jzz 2017-06-06
 */
class Product implements Cloneable {
	int a = 1;	//基本类型是支持copy的
	String s = "aa";	//String对象是final的不能更改，所以不用写clone方法
	Attribute attribute;	//引用类型需要单独实现clone方法
	
	public Product(Attribute attribute) {
		this.attribute = attribute;
	}
	
	@Override
	public Object clone() {
		Product product = null;
		try {
			product =  (Product)super.clone(); //浅度复制
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		product.attribute =  (Attribute)attribute.clone(); //深度复制,将属性也复制一遍
		return product;
	}
	
}

//属性对象也需要重写clone方法
class Attribute implements Cloneable {
	int a = 2;
	String s = "bb";
	
	@Override
	public Object clone() {
		Attribute attribute =null;
		try {
			attribute  = (Attribute)super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
		return attribute;
	}
}

public class CloneExample01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Attribute attribute = new Attribute();
		Product product = new Product(attribute);
		Product product2 = (Product)product.clone();
		product2.a = 11;
		product2.s = "aaa";
		product2.attribute.a = 22;
		product2.attribute.s = "bbb";
		
		System.out.println(product.a + " " + product.s + " " + product.attribute.a + " " + product.attribute.s);
		System.out.println(product2.a + " " + product2.s + " " + product2.attribute.a + " " + product2.attribute.s);
	}

}
