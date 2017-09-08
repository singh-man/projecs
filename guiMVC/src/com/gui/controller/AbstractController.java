package com.gui.controller;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import com.gui.model.DomainModel;
import com.gui.view.View;

public abstract class AbstractController<DM extends DomainModel> implements Controller<DM> {

	private List<View> interestedListOfView;
	protected DM model;
	
	private AbstractController() {
		interestedListOfView = new ArrayList<View>();
	}
	
	public AbstractController(DM model) {
		this();
		this.model = model;
		model.addPropertyChangeListener(this);
	}
	
		
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		for(View view : interestedListOfView) {
			view.update(evt);
		}
	}

	@Override
	public void addView(View view) {
		interestedListOfView.add(view);
	}

	@Override
	public void removeView(View view) {
		interestedListOfView.remove(view);
	}

	protected abstract void setModel(DM model);
	protected abstract DM getModel();
}
