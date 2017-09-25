package org.utils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

public class TestClassForExternalizable implements Externalizable {
	
	public int insI;
	public int insJ = 10;
	public String insS = "instance string";
	
	public static int staticK;
	public static int staticL = 10;
	
	public static String staticString = "static string";

	@Override
	public String toString() {
		return insI + " : " + insJ + " : " + insS + " : " + staticK + " : " + staticL + " : " + staticString;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		// TODO Auto-generated method stub
		
	}
}
