package com.gui.model;

/**
 * This acts like a domain model
 * @author emmhssh
 *
 */
public class ConfigurationModel extends AbstractDomainModel {

	private String configurationName;
	
	/*
	 * Decided: 1:1 relation between Model and Controller
	 * but
	 * 
	 * Proposal:
	 * rather than keeping the instance of Controller add the controller instance
	 * to the Model property Listerner
	 */
	
	public ConfigurationModel() {
	}

	public String getConfigurationName() {
		return configurationName;
	}

	public void setConfigurationName(String configurationName) {
		this.configurationName = configurationName;
	}

	@Override
	public void handleMessage(Object o) {
		// receives distributed msgs and based on it updates itself
		
		//success -> firePropertyChange()
		
		//failure -> return
		
	}

	@Override
	public String doSave() {
		return null;
	}

	@Override
	public String validate() {
		return null;
	}

	@Override
	public boolean isDirty() {
		return false;
	}


}
