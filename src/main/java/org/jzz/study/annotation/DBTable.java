package org.jzz.study.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)	//ElementType.TYPE：类、接口或枚举声明
public @interface DBTable {
	public String name() default ""; //用空串代表元素不存在
}
