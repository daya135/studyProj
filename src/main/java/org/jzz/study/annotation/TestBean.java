package org.jzz.study.annotation;

@DBTable(name = "TEST")
public class TestBean {
	@SQLInteger(value = "id", constrains = @Constrains(primaryKey = true))
	Integer id;
	
	@SQLString(value = "name", len = 20)
	String name;
	
	@SQLInteger("age")
	Integer age;
	
	static int objCount;
}
