package com.abhi.example.mvc;

import java.util.List;

/* This class will pass on the Events to the UserModelHandler */
public class UserController implements UserChangeListener
{
	private List<UserChangeListener> interestedViews;
	//Do we a actually need a list of registered models... ???
	
	
	public void addUserEventListener(UserChangeListener aUserChangeListener)
	{
		interestedViews.add(aUserChangeListener);
	}
	
	public void saveUser(UserModel aUserModel)
	{
		UserModelHandler userModelHandler = HandlerFactory.getUserModelHandler();
		userModelHandler.saveUserModel(aUserModel);
	}

	@Override
	public void userAdded(UserAddEvent userAddEvent) {
		// TODO Auto-generated method stub
		
		for(UserChangeListener theUserChangeListener : interestedViews)
		{
			theUserChangeListener.userAdded(userAddEvent);
		}
		
	}

	@Override
	public void userDeleted(UserDeletedEvent userDeletedEvent) {
		// TODO Auto-generated method stub
		
		for(UserChangeListener theUserChangeListener : interestedViews)
		{
			theUserChangeListener.userDeleted(userDeletedEvent);
		}
	}

	@Override
	public void userUpdated(UserUpdateEvent userUpdateEvent) {
		// TODO Auto-generated method stub
		
		for(UserChangeListener theUserChangeListener : interestedViews)
		{
			theUserChangeListener.userUpdated(userUpdateEvent);
		}
	}
	
	/* How to Handle something like Show All Users -- ShowUserEvent(Containing List)*/
	
}
