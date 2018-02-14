package com.abhi.example.mvc;

public interface UserChangeListener
{
	public void userAdded(UserAddEvent userAddEvent);
	public void userDeleted(UserDeletedEvent userDeletedEvent);
	public void userUpdated(UserUpdateEvent userModifiedEvent);
}
