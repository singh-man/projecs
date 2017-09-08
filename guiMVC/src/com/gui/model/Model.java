package com.gui.model;

import java.beans.PropertyChangeListener;

/**
 * 
 * @author emmhssh
 *
 * If a Component Model is not provided by the UI technology, like TreeTable by Swing,
 * this and its sub-classes can be termed to derive the View's component Model.
 * 
 * In the case Domain model is there in GUI like 'M' in M-V-C
 * This interface can also be used to define the domain models.
 */
public interface Model {
	
	public void addPropertyChangeListener(PropertyChangeListener listener);
	public void removePropertyChangeListener(PropertyChangeListener listener);
	
	/*
	 * If component Model:
	 * takes message from Controller / Presenter / PresentationModel
	 * 
	 * If domain Model:
	 * takes Message from some some external / messaging / event framework
	 */
	public void handleMessage(Object o);
	
}
