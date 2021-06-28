package org.jzz.study.java8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.jzz.study.util.Print;

/** 
 * java8 Stream api 测试
 * https://blog.csdn.net/y_k_y/article/details/84633001
 * */
public class StreamTest {
	@SuppressWarnings("unused")
	private static  class Item implements Comparable<Item>{
		public int id = 0;
		public String type = null;
		public int value = 0;
		
		public int getValue() {
			return this.value;
		}
		public int getId() {
			return this.id;
		}
		public String toString() {
			return String.format("[id:%d,type:%s,value：%d]", id, type, value);
		}
		@Override
		public int compareTo(Item item) {
			if (item.value == this.value) return 0;
			return item.value < this.value ? 1 : -1;
		}
	}	
	
	public static List<Item> getList() {
		List<Item> items = new ArrayList<StreamTest.Item>();
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < 10; i++) {
			Item item = new Item();
			item.id = i;
			item.type = i % 2 == 0 ? "a" : "b";
			item.value = random.nextInt(10);
			items.add(item);
		}
		return items;
	}
	
	/** 多种生成流的方法 */
	public static void generateStream() throws Exception {
		List<Item> list = getList();
		Integer[] nums = new Integer[10];
		String filePath = System.getProperty("user.dir") + "/data.txt";
		Stream<?> stream;
		//顺序流
		stream = list.stream();
		//并行流
		stream = list.parallelStream();
//		stream.forEach(System.out::println);
		//从数组获得流
		stream = Arrays.stream(nums);	
		//静态方法获得流
		stream = Stream.of(1, 2, 3, 4, 5); 
		stream = Stream.iterate(0, (x)->x+2).limit(5);
		stream.forEach(System.out::println);
		stream = Stream.generate(Math::random).limit(5);
		stream.forEach(System.out::println);
		// BufferedReader.lines()每行内容转化为流
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		stream = reader.lines();
		stream.forEach(System.out::println);
	}
	
	/** 流的中间操作 */
	public static void midOperation() {
		//filter:Predicate接口
		//过滤、去重、跳过、分页等中间操作
		Stream<?> stream = getList().stream().filter(item -> item.type == "a").distinct().skip(2).limit(1);
		//forEach:Comsumer接口
		stream.forEach(System.out::print);
		Print.print();
		//map:Function接口
		//map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素（Function表达式需要返回值）。
		getList().stream().map(item->{	
			item.value = item.value * 10;
			return item;
		}).forEach(System.out::print);		
		Print.print();
		//peek:Consumer接口
		//peek：如同于map，能得到流中的每一个元素。但map接收的是一个Function表达式，有返回值；而peek接收的是Consumer表达式，没有返回值。
		getList().stream().peek(item->{	
			item.value = item.value * 10;
		}).forEach(System.out::print);		
		Print.print();
		//flatMap:Function接口
		// flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
		List<String> list = Arrays.asList("a,b,c", "1,2,3");
		list.stream().flatMap(s->{
			String[] split = s.split(",");
			Stream<String> stream2 = Arrays.stream(split);
			return stream2;
		}).forEach(Print::printnb);
		Print.print();
		//sorted(Comparator com)：定制排序，自定义Comparator排序器  
        //sorted()：自然排序，流中元素需实现Comparable接口, 略
		getList().stream().sorted((item1, item2)->{
			return item1.value - item2.value;
		}).forEach(System.out::print);
	}
	/** 流的终止操作 */
	public static void terminateOperation() {
		//匹配、聚合操作
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
		boolean allMatch = list.stream().allMatch(s -> s < 3);	//false
		boolean noneMatch = list.stream().noneMatch(s -> s > 10); //true
		boolean anyMatch = list.stream().anyMatch(s -> s > 3);	//true
		Integer findFirst = list.stream().findFirst().get(); //1
		Integer findAny = list.stream().findAny().get(); //1
		long count = list.stream().count(); //5
		Integer max = list.stream().max(Integer::compareTo).get(); //5
		Integer min = list.stream().min(Integer::compareTo).get(); //1
		Print.print(Arrays.asList(allMatch, noneMatch, anyMatch, findFirst, findAny, count, max, min));
		//规约
		//聚合
	}
	
	public static void main(String[] args) throws Exception{
//		generateStream();	
//		midOperation();
		terminateOperation();
	}
}
