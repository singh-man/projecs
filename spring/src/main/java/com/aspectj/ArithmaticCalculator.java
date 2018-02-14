package com.aspectj;

/**
 * impl class of Calculator 
 * This class is been adviced
 * @author Acer
 *
 */
public class ArithmaticCalculator implements Calculator{

	@Override
	public int add(int i, int j) {
		System.out.println("ArithmaticCalculator: add");
		return i+j;
	}

	@Override
	public void throwException() {
		System.out.println("ArithmaticCalculator: throwException");
		throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public void print() {
		System.out.println("ArithmaticCalculator: print");
	}

}
