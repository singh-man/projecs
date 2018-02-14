package com.gui.controller;

/**
 * 1:1 Relation between Domain Model and Contrller
 */
import com.gui.listener.DomainModelPropertyChangeListener;
import com.gui.model.DomainModel;
import com.gui.view.View;

public interface Controller<DM extends DomainModel> extends DomainModelPropertyChangeListener {

	void addView(View view);
	void removeView(View view);
	 
}
