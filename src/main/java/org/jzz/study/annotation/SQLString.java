package org.jzz.study.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface SQLString {
	public int len() default 0;
	public String value() default "";
	Constrains constrains() default @Constrains; //这句话很灵性！
}
