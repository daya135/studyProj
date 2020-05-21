package org.jzz.study.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.jzz.study.util.Print;

import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

/** 
 * 使用红黑树存储的Map
 * 使用场景：超大容量的查询、排序场景
 * 大部分情况下HashMap通常比TreeMap快一点（树和哈希表的数据结构使然），建议多使用HashMap,在需要排序的Map时候才用TreeMap.
 * */
public class TreeMapTest {
	static Map<String, Object> treeMap;
	static Random random = new Random(System.currentTimeMillis());
	
	
	
	public static void main(String[] args) {
		treeMap = new TreeMap<String, Object>();
		
		for (int i = 0; i < 10000; i++) {
			treeMap.put(String.valueOf(random.nextInt(10000)), UUID.randomUUID());
		}
		Print.print(treeMap.size());
		
		//遍历
		Set<Entry<String, Object>> entrySet = treeMap.entrySet();
		for(Entry<String, Object> entry : entrySet) {
//			Print.print(entry.getKey() + "  " + entry.getValue());
		}
		Iterator<String> iterator = treeMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();	//不加这句会死循环
			//doSomething
		}
		
		//跟排序相关的方法
		((TreeMap)treeMap).firstKey();
		((TreeMap)treeMap).ceilingKey("100");
		((TreeMap)treeMap).higherKey("100");
		
		treeMap = new TreeMap<String, Object>(Collections.reverseOrder());	//指定排序器
		for (int i = 0; i < 10000; i++) {
			treeMap.put(String.valueOf(random.nextInt(10000)), UUID.randomUUID());
		}
		Print.print(((TreeMap)treeMap).firstKey());
	}
}
