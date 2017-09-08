package com.web.httpInvoker;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PrintServiceTest {

	public static void main(String[] args) {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:**/context-httpInvoker.xml");

		testPrintService(ctx);

		testPrintService_1(ctx);
	}

	private static void testPrintService_1(ApplicationContext ctx) {
		PrintService_1 ps_1 = ctx.getBean(PrintService_1.class);
		System.out.println(ps_1.print("man"));
		ps_1.execute();
		ps_1.asyncExecute();
		
		Future<String> future = ps_1.asyncExecuteWithReturn();
		try {
			System.out.println("Async Result: " + future.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void testPrintService(ApplicationContext ctx) {
		PrintService ps = ctx.getBean(PrintService.class);

		ps.print("hello");
		ps.execute();
		ps.post(new ArrayList());
	}
}
