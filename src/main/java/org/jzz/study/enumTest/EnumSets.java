package org.jzz.study.enumTest;

import java.util.EnumSet;
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
	}
	
}
