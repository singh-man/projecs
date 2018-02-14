package com.abhi.example.mvc;

import java.util.List;

/* Handles saving and other task on the user model*/
public class UserModelHandler
{
	/* List of Users - We can keep a list of users in a list in memory and do a batch update*/
	/* Optimizations... :P */
	/* Probably not a good idea, as our application is distributed, we will need to keep this list in
	 * sync too .. 
	 */
	
	/* All those who are interested in my updated.. Please Register -- The Controllers.*/
	/* In fact we can simply keep 'a' reference to the UserChangeListener ... ??? */
	private List<UserChangeListener> myUserChangeListeners;
	
	public void addUserChangeListener(UserChangeListener aUserChangeListener)
	{
		myUserChangeListeners.add(aUserChangeListener);
	}
	
	public void removeUserChangeListener(UserChangeListener aUserChangeListener)
	{
		myUserChangeListeners.remove(aUserChangeListener);
	}
	
	public void saveUserModel(UserModel aUserModel)
	{
		System.out.println("My Job is to serialize the UserModel," +
				"I do nothing.. but my Subclass can override me");
		
		//since we have changed the model, we need to inform the interested controllers.
		fireSaveUserModel(aUserModel);
		
	}
	
	public void updateUserModel(UserModel aUserModel)
	{
		//Save a reference to old one.
		UserModel aOldUserModel = getUserModelFromId();
		
		System.out.println("My Job is to update the UserModel");
		
		
		//since we have updated the model, we need to inform the interested controllers
		fireUpdateUserModel(aOldUserModel, aUserModel);
	}
	
	private UserModel getUserModelFromId() {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUserModel(UserModel aUserModel)
	{
		System.out.println("My Job is to delete the UserModel");
		
		//do we need to notify now ??
		fireDeleteUserModel(aUserModel);
		
	}
	
	private void fireSaveUserModel(UserModel aUserModel)
	{
		for(UserChangeListener theUserChangeListener : myUserChangeListeners)
		{
			theUserChangeListener.userAdded(new UserAddEvent(null, aUserModel));
		}
	}
	
	private void fireUpdateUserModel(UserModel aOldUserModel, UserModel aNewUserModel)
	{
		for(UserChangeListener theUserChangeListener : myUserChangeListeners)
		{
			//theUserChangeListener.userUpdated(new UserUpdateEvent(aOldUserModel, aNewUserModel));
		}
	}
	
	private void fireDeleteUserModel(UserModel aUserModel)
	{
		for(UserChangeListener theUserChangeListener : myUserChangeListeners)
		{
			//theUserChangeListener.userDeleted(new UserDeleteEvent(null, aUserModel));
		}
	}
}
