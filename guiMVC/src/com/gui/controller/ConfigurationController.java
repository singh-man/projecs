package com.gui.controller;

import com.gui.model.ConfigurationModel;
import com.gui.view.View;

public class ConfigurationController extends AbstractController<ConfigurationModel>{
	public interface Display extends View {
		public String getConfigCollectionName();
		public String getConfigName();
		public void setData(Object o);
	}

	public ConfigurationController(ConfigurationModel configurationModel) {
		super(configurationModel);
		//bind();
	}
	
	@Override
	protected void setModel(ConfigurationModel model) {
		this.model.removePropertyChangeListener(this);
		this.model = model;
		model.addPropertyChangeListener(this);
	}

	@Override
	protected ConfigurationModel getModel() {
		return this.model;
	}
	
	public void saveConfiguration(Display display) {
		//display.getConfigName();
	}

}
