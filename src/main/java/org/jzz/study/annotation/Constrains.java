package org.jzz.study.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Constrains {
	boolean primaryKey() default false;
	boolean allowNull() default true;
	boolean unique() default false;
}
