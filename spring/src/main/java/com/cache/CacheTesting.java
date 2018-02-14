package com.cache;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CacheTesting {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:**/cache.xml");

		System.out.println("fetching book");
		System.out.println(ctx.getBean(Book.class).findBook("Hello"));
		System.out.println("cached : " + ctx.getBean(Book.class).findBook("Hello new Book"));
		
		System.out.println("\n Adding new book");
		ctx.getBean(Book.class).addBook("manish new Book");

		System.out.println("\n fetching book");
		System.out.println(ctx.getBean(Book.class).findBook("manish new Book"));
		System.out.println("fetching wrong book but cache is triggered");
		System.out.println(ctx.getBean(Book.class).findBook("Hello new Book"));

		System.out.println("\n Adding a new book");
		ctx.getBean(Book.class).addBook("a new Book");
		System.out.println("fetching book");
		System.out.println(ctx.getBean(Book.class).findBook("manish new Book"));
		System.out.println(ctx.getBean(Book.class).findBook("a new Book"));
		System.out.println("cached : " + ctx.getBean(Book.class).findBook("a new Book"));
	}

}
