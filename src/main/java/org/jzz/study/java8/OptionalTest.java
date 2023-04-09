package org.jzz.study.java8;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Stream;

//Optional 的意义
//重要的一点是 Optional 不是 Serializable。因此，它不应该用作类的字段。
//Optional 主要用作返回类型。在获取到这个类型的实例后，如果它有值，你可以取得这个值，否则可以进行一些替代行为。
//Optional 是 Java 语言的有益补充 —— 它旨在减少代码中的 NullPointerExceptions，虽然还不能完全消除这些异常。
//它也是精心设计，自然融入 Java 8 函数式支持的功能。
public class OptionalTest {

	public static void main(String[] args) {
		User user = new User("jzz",18);
		User user2 = new User("jzl",17);
		Optional<User> emptyOpt = Optional.empty(); //空
		emptyOpt = Optional.ofNullable(null);	//显示传空
		
		Optional<User> userOpt = Optional.of(new User("jzz",18));
		
		userOpt.get().getName();
		
		//返回是否是空值
		System.out.println(userOpt.isPresent());
//		assertTrue(emptyOpt.isPresent());
		
		//接收一个consumer接口
		userOpt.ifPresent(System.out::println);
		
		//访问 Optional 对象的值
		assertEquals(user.getName(), userOpt.get().getName());
		
		User nullUser = null;
		User result;
		
		//orElse() 和 orElseGet() 的不同之处
		result = Optional.ofNullable(user).orElse(User.randomUser());	//即使前面orElse的对象仍会初始化。。虽说是废话。。但在密集调用场景有性能影响
		result = Optional.ofNullable(nullUser).orElseGet(User::randomUser);
		
		//返回异常
//		result = Optional.ofNullable(nullUser).orElseThrow(()-> new NullPointerException("空指针 supplier"));
//		assertEquals(result.getName(), user2.getName());
		
		//转换值
		String name = Optional.of(User.nullUser()).map(User::getName).orElse("default null name"); //使用map转换为另一个Optional
		System.out.println(name);
		name = Optional.of(User.nullUser()).flatMap(User::getNameOptional).orElse("default name"); //属性的getOptional方法，链式调用
		System.out.println(name);
		
		//过滤值
		Optional<User> optResult = Optional.of(user).filter(u -> u.name.equals("jzz"));
		assertTrue(optResult.isPresent());
		
		//链式调用
		user.setAddress(new Address());
		String city = Optional.of(user).flatMap(User::getAddressOptional).map(Address::getCity).orElse("default Address");
		System.out.println(city);
	}
	
	private static class Address {
		String city;
		public Address() {}
		
		public Address(String city) {
			this.city = city;
		}
		
		public String getCity() {
			return this.city;
		}
		
	}
	
	private static class User {
		public String name;
		public int age;	
		public Address address;
		
		public User(String name, int age) {
			this.name = name;
			this.age = age;
		}
		
		public static User randomUser() {
			System.out.println("createRandomUser");
			
			Random random = new Random();
			String name = "";
			for (int i = 0; i  < 3; i++) {
				name += String.valueOf((char)('a' + random.nextInt(26)));
			}
			return new User(name, random.nextInt(100));
		}
		
		public static User nullUser() {
			return new User(null, 0);
		}
		
		public Optional<String> getNameOptional() {
			return Optional.ofNullable(name);
		}
		
		public Optional<Address> getAddressOptional() {
			return Optional.of(address);
		}
		
		public Address getAddress() {
			return this.address;
		}
		
		public void setAddress(Address address) {
			this.address = address;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		
	}
	

}
