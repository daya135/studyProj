package org.jzz.study.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

/*
 * 实现Serializable接口，通过对象的序列化和反序列化实现克隆,实现真正的深度克隆
 * 另外演示了使用工具类复制对象
 * by jzz 2017-06-06
 */
class Outer implements Serializable {
	private final static long serialVersionUID = 369285298572940L;
	
	int a = 1;
	String s = "outer";
	Date date = new Date(); //date也实现了Serializable接口
	Inner inner;
	
	public Outer(Inner inner) {
		this.inner = inner;
	}
	
	public Outer myClone() {
		Outer outer = null;
		try {
			// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
			objectOut.writeObject(this);
			// 将流序列化成对象
			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream objectIn = new ObjectInputStream(byteIn);
			outer = (Outer) objectIn.readObject();	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return outer;
	}
	
	@Override
	public String toString() {
		return this.date + " " + this.a + " " + this.s + " " + inner.toString(); 
	}
}

//所有的属性对象属性都需要实现序列化
class Inner implements Serializable{
	private static final long serialVersionUID = 872390113108L; //最好是显式声明ID
	int a = 1;
	String s = "inner";
	
	@Override
	public String toString() {
		return this.a + " " + this.s; 
	}
	
}

public class CloneExample02 {
	public static void main (String[] args) throws Exception{
		Inner inner = new Inner();
		Outer outer = new Outer(inner);
		Thread.sleep(1000);
		
		//使用自定义 的clone方法实现深度复制
		Outer outer2 = outer.myClone(); 
		
		//使用工具类复制对象
		Outer outer3 = new Outer(inner);
		org.springframework.beans.BeanUtils.copyProperties(outer, outer3); 
		
		outer2.a = 2;
		outer2.s = "outer2";
		outer2.inner.a = 2;
		outer2.inner.s = "inner2";
		
		outer3.date = new Date();
		
		System.out.println(outer);
		System.out.println(outer2);
		System.out.println(outer3);		
	}

}
