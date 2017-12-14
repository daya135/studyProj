package org.jzz.study.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Merin
 * 对象序列化用于状态持久测试
 */
public class MyWorld {
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		House house = new House();
		List<Animal> animals = new ArrayList<Animal>();
		animals.add(new Animal("dog", house));
		animals.add(new Animal("pig", house));
		animals.add(new Animal("cat", house));
		System.out.println(animals);
		
		ByteArrayOutputStream buf1 = new ByteArrayOutputStream(); //内存映射输出流，可在内存中创建一个缓冲数组，发送到此输出流的数据会保存到缓冲数组
		ObjectOutputStream o1 = new ObjectOutputStream(buf1);
		o1.writeObject(animals);
		o1.writeObject(animals);
		ByteArrayOutputStream buf2 = new ByteArrayOutputStream();
		ObjectOutputStream o2 = new ObjectOutputStream(buf2);
		o2.writeObject(animals);
		
		ObjectInputStream in1 = new ObjectInputStream(new ByteArrayInputStream(buf1.toByteArray()));
		ObjectInputStream in2 = new ObjectInputStream(new ByteArrayInputStream(buf2.toByteArray()));
		List animals1 = (List)in1.readObject(),
				animals2 = (List)in1.readObject(),
				animals3 = (List)in2.readObject();
		System.out.println(animals1);
		System.out.println(animals2);
		System.out.println(animals3);
	}
}


class House implements Serializable {}

class Animal implements Serializable {
	private String name;
	private House house;
	public Animal(String name, House house) {
		this.name = name;
		this.house = house;
	}
	public String toString() {
		return String.format("%s[%s],%s", name, super.toString(), house);
	}
}