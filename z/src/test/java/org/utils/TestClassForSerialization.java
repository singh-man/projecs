package org.utils;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TestClassForSerialization implements Serializable {
	
	private int insI;
	private int insJ = 10;
	private String insS = "This is the test string used everywhere and also used to " +
				"check the compression utility too. Hope its work accordigly " +
				"and in proper way with the proper compression that shows the " +
				"compression of passed object. used here";
	private int insM;
	
	private static int staticK;
	private static int staticL = 10;
	
	private static String staticString = "static string";

	/*private void writeObject(ObjectOutputStream out) throws IOException {
		throw new NotSerializableException("Deleberately done");
	}
	
	private void readObject(ObjectInputStream in) throws IOException {
		throw new NotSerializableException("Deleberately done");
	}*/
	
	@Override
	public String toString() {
		return insI + " : " + insJ + " : " + insS + " : " + insM + " : " + staticK + " : " + staticL + " : " + staticString;
	}

	public int getInsI() {
		return insI;
	}

	public void setInsI(int insI) {
		this.insI = insI;
	}

	public int getInsJ() {
		return insJ;
	}

	public void setInsJ(int insJ) {
		this.insJ = insJ;
	}

	public String getInsS() {
		return insS;
	}

	public void setInsS(String insS) {
		this.insS = insS;
	}

	public static int getStaticK() {
		return staticK;
	}

	public static void setStaticK(int staticK) {
		TestClassForSerialization.staticK = staticK;
	}

	public static int getStaticL() {
		return staticL;
	}

	public static void setStaticL(int staticL) {
		TestClassForSerialization.staticL = staticL;
	}

	public static String getStaticString() {
		return staticString;
	}

	public static void setStaticString(String staticString) {
		TestClassForSerialization.staticString = staticString;
	}
}
