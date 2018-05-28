package org.jzz.study.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.jar.Attributes.Name;

@Retention(RUNTIME)
@Target(FIELD)
public @interface SQLInteger {
	public String value() default "";
	Constrains constrains() default @Constrains; //这句话很灵性！
}
