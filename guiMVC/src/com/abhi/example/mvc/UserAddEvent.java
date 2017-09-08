package com.abhi.example.mvc;

/* Inspired from PropertyChangeEvent, but we dont need Property Type/Name
 * as our Event is itself quite specific.
 * ... Can we create a Base Class UserEvent  ... ??
 */

public class UserAddEvent
{
	private UserModel myOldUserModel; //for sake of generic-ness
	private UserModel myNewUserModel;

	public UserAddEvent(UserModel aOldUserModel, UserModel aNewUserModel)
	{
		myOldUserModel = aOldUserModel;
		myNewUserModel = aNewUserModel;
	}
	public UserModel getMyUserModel() {
		return myNewUserModel;
	}

	public void setMyUserModel(UserModel myNewUserModel) {
		this.myNewUserModel = myNewUserModel;
	}

	public UserModel getMyOldUserModel() {
		return myOldUserModel;
	}

	public void setMyOldUserModel(UserModel myOldUserModel) {
		this.myOldUserModel = myOldUserModel;
	}
	
	
	
}
