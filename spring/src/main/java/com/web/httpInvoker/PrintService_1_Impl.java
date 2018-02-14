package com.web.httpInvoker;

import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

public class PrintService_1_Impl implements PrintService_1 {

	private String name = PrintService_1_Impl.class.getSimpleName();

	@Override
	public String print(String msg) {
		return name + " : print : " + msg;
	}

	@Override
	public void execute() {
		System.out.println(name + " : execute : ");
	}

	@Override
	@Async
	public void asyncExecute() {
		System.out.println(name + " : " + Thread.currentThread().getName() + " : asyncExecute : start");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(name + " : " + Thread.currentThread().getName() + " : asyncExecute : threaded Exit");
	}

	@Override
	@Async
	public Future<String> asyncExecuteWithReturn() {
		System.out.println(name + " : " + Thread.currentThread().getName() + " : asyncExecuteWithReturn : start");

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(name + " : " + Thread.currentThread().getName() + " : asyncExecuteWithReturn : exit");
		return new AsyncResult<String>("asyncExecuteWithReturn");
	}

}
