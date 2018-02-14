package com.ericsson;

/**
 * Note: To use this; Listeners should implement Listener interface.
 * 
 * USAGE:
 * @RegisterListener(subject={<Subject1>.class,<Subject2>.class},includeParent=true/false)
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.ericsson.subject.Subject;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegisterListener {

	/*
	 * Enter Subjects list here.
	 */
	public Class<? extends Subject>[] subject() default {}; //{} can have some values
	
	/*
	 * If parent subjects need to be included.
	 */
	public boolean includeParent() default false;
	
}
