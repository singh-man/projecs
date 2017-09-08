package com.web.httpInvoker;

public class PrintServiceImpl implements PrintService {

	private String name = PrintServiceImpl.class.getSimpleName();
	@Override
	public void print(String msg) {
		System.out.println(name + " : print : " + msg);
	}

	@Override
	public void execute() {
		System.out.println(name + " : execute");
	}

	@Override
	public void post(Object o) {
		System.out.println(name + " : " + o);
	}
}
