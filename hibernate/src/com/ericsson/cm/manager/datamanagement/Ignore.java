package com.ericsson.cm.manager.datamanagement;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Any setter that need not be handled by developer,
 * should be decorated with this.
 * 
 * @author emmhssh
 *
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface Ignore {

	public String value() default "Hibernate handled, Please don't set it";
}
