package org.jzz.study.java8;

class Person {
	String name;
	int age;
	public Person() {
		name = "default";
	}
	public Person(String name) {
		this.name = name;
	}
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	public String toString() {
		return name + " " + age;
	}
}
@FunctionalInterface 
interface PersonFactory<P extends Person> {
	 P create(String name, int age);
}

public class ConstructorTest {
	public static void main(String[] args) {
		PersonFactory<Person> personFactory = Person::new; //::关键字
		Person person = personFactory.create("aaa", 11);
		System.out.println(person);
	}

}
