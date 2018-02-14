package com.gui.view;

import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import com.gui.controller.ConfigurationController;

public class ConfigurationView extends JPanel implements ConfigurationController.Display {

	private ConfigurationController controller;
	public ConfigurationView(ConfigurationController controller) {
		this.controller = controller;
		controller.addView(this);
		init();
	}

	private void init() {
		show(true);
		
	}

	@Override
	public void update(PropertyChangeEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit() {
		// TODO Auto-generated method stub
		
	}

	private void sa() {
		controller.saveConfiguration(this);
	}
	@Override
	public String getConfigCollectionName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getConfigName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setData(Object o) {
		// Pass the data to associated Model type of the View
	}
}
