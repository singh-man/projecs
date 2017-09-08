package com.aop;

public class StudentImpl implements Student {

	@Override
	public void read() {
		System.out.println("StudentImpl: reading");
	}

	@Override
	public String write(String topic) {
		System.out.println("StudentImpl: write");
		return topic;
	}
	
	@Override
	public boolean status(boolean status) {
		if(status == false)
			throw new ArrayIndexOutOfBoundsException();
		return status;
	}

}
