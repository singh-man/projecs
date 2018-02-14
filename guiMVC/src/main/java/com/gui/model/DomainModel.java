package com.gui.model;


/**
 * Should not have any UI specific objects
 * @author emmhssh
 *
 */
public interface DomainModel extends Model {

	/*
	 * Domain Model:
	 * takes Message from some some external / messaging / event framework
	 */
	public void handleMessage(Object o);
	
	String validate();
	
	boolean isDirty();
	
	String doSave();
	

}
