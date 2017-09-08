package com.gui.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EventType {

	//String eventType() default "";
	
	//Class<? extends AbstractCommand.Type<? extends CommandHandler>> type();
	
	String eventType();
	
	//Class<? extends Type<? extends CommandHandler>> type();
}
