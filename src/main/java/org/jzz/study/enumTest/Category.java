package org.jzz.study.enumTest;
import java.util.EnumMap;

import org.jzz.study.enumTest.Input;

public enum Category {
	MONEY(Input.NICKEL, Input.DIME, Input.QUARTER, Input.DOLLAR),
	ITEM_SELECTION(Input.TOOTHPASTE, Input.CHIPS, Input.SODA, Input.SOAP),
	QUIT_TRANSACTION(Input.ABORT_TRANSACTION),
	SHUT_DOWN(Input.STOP);
	private Input[] values;
	private Category(Input... types) {
		values = types;
	}
	private static EnumMap<Input, Category> categories = new EnumMap<Input, Category>(Input.class);
	static {
		for (Category c : Category.class.getEnumConstants())
			for (Input type : c.values)
				categories.put(type, c);
	}
	public static Category categorize(Input input) {
		return categories.get(input);
	}
	
}
