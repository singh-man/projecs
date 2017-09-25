package com.mbean;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class Testing {

	private MBeanServer mbs = null;

	public String te(){
		return "";
	}
	public Testing() {

		// Create an MBeanServer and HTML adaptor (J2SE 1.4)
		mbs = ManagementFactory.getPlatformMBeanServer();
		HtmlAdaptorServer adapter = new HtmlAdaptorServer();

		// Unique identification of MBeans
		Thermometer thermometer = new Thermometer();
		ObjectName adapterName = null;
		ObjectName thermometerName = null;

		try {
			// Uniquely identify the MBeans and register them with the MBeanServer 
			thermometerName = new ObjectName(
					"F/E_MediationManager:name=thermometer");
			mbs.registerMBean(thermometer, thermometerName);
			// Register and start the HTML adaptor
			adapterName = new ObjectName("F/E_MediationManager:name=htmladapter,port=8000");
			adapter.setPort(8000);
			mbs.registerMBean(adapter, adapterName);
			adapter.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String argv[]) throws IOException {
		System.out.println("SimpleAgent is running...");
		Testing agent = new Testing();
		System.in.read();

	}

}
