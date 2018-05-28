package org.jzz.study.enumTest;

import java.util.Random;

public enum Input {
	NICKEL(5), DIME(10), QUARTER(25), DOLLAR(100),
	TOOTHPASTE(200), CHIPS(75), SODA(100), SOAP(50),
	ABORT_TRANSACTION {	//无参的枚举实例
		public int amount() {
			throw new RuntimeException("ABORT.amount()");
		}
	},
	STOP {
		public int amount() {
			throw new RuntimeException("SHUT_DOWN.amount()");
		}
	};
	int value;
	private Input(int value) {
		this.value = value;
	}
	private Input() {} //这个作为无参枚举实例的构造方法，必须有否则报错。
	int amount() { return value; }
	static Random rand = new Random(47);
	public static Input randowmSelection() {
		return values()[rand.nextInt(values().length - 1)];  //从自身实例里随机选取一个
	}
}
