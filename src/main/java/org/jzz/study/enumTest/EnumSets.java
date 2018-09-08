package org.jzz.study.enumTest;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;

import static org.jzz.study.enumTest.Spiciness.*;

public class EnumSets {
	public static void main(String[] args) {
		EnumSet<Spiciness> spicSet = EnumSet.noneOf(Spiciness.class);
		spicSet.add(HOT);
		Enums.print(spicSet.toArray());
		spicSet.addAll(EnumSet.of(NOT, MILD));
		Enums.print(spicSet.toArray());
		spicSet = EnumSet.allOf(Spiciness.class);
		Enums.print(spicSet.toArray());
		spicSet.removeAll(EnumSet.of(MEDIUM, HOT));
		Enums.print(spicSet.toArray());
		Enums.print(EnumSet.range(NOT, HOT).toArray());
		spicSet = EnumSet.complementOf(spicSet); //不包含原枚举的Enumset
		Enums.print(spicSet.toArray());
		
		spicSet.clear();
		spicSet.add(HOT);
		spicSet.add(MEDIUM);
		for (Spiciness s : spicSet) {
			System.out.print(s + " "); //可以看出，EnumSet仍会按照定义的顺序重新排序，不受添加顺序影响。
		}
		System.out.println();
		
		HashSet<Spiciness> set = new HashSet<Spiciness>();
		set.add(HOT);
		set.add(MEDIUM);
		for (Spiciness s : set) {
			System.out.print(s + " ");	//普通的set受添加顺序的影响
		}
		
	}
	
}
