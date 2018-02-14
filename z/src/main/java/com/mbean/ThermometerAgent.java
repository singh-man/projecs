package com.mbean;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class ThermometerAgent {

	private MBeanServer server = null;

	public ThermometerAgent() {

		server = ManagementFactory.getPlatformMBeanServer();

		Thermometer tBean = new Thermometer();
		ObjectName tBeanName = null;

		try {
			tBeanName = new ObjectName("ThermometerAgent:type=Thermometer");
			server.registerMBean(tBean, tBeanName);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
