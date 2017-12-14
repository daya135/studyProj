package org.jzz.study.generic.tuple;

public class TuppleTest {
	static TwoTuple<String, Integer> f() {
		return new TwoTuple<String, Integer>("hi", 47);
	}
	
	static TreeTuple<String, Integer, String> h() {
		return new TreeTuple<String, Integer, String>("hi", 47, "how");
	}
	public static void main(String[] args) {
		TwoTuple<String, Integer> twoTuple = f();
		TreeTuple<String, Integer, String> treeTuple = h();
	}
	
}
