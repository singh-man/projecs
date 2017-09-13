package com.servlet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface ForUrl {

	public String url() default "";
	
	public String param() default "";
	
	public ParamScope scope() default ParamScope.REQUEST;
	
	
	public enum ParamScope {
		APPLICATION,
		SESSION,
		REQUEST
	}
}
