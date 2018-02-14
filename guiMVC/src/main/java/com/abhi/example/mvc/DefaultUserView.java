package com.abhi.example.mvc;


public class DefaultUserView implements UserChangeListener
{
	private UserController myUserController;
	
	DefaultUserView(UserController aUserController)
	{
		this.myUserController = aUserController;
		
		/* I must register my self so that the Controller can update me.*/
		myUserController.addUserEventListener(this);
	}
	
	/*
	@Override
	public void updateUserView(UserModel userModel)
	{
		//we should check if view is visible... ??
		System.out.println("Updating the User View ... ");
		
	}*/
	
	public void saveUser()
	{
		//fetch all the data from the view
		//create a dummy model using the info
		//create a SaveUserEvent
		UserModel theUserModel = new UserModel();
		theUserModel.setFirstName("fisrtName");
		theUserModel.setId("1");
		theUserModel.setLastName("lastName");
		
		//Create an Event to be sent to he controller
		//We need a base class for Event ... ?? What exactly should go in there ??
		//UserAddEvent userAddEvent = new UserAddEvent();
		//userAddEvent.setMyUserModel(userModel);
		
		//we need to forward it to the controller now ..
		myUserController.saveUser(theUserModel);
	}

	@Override
	public void userAdded(UserAddEvent userAddEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userDeleted(UserDeletedEvent userDeletedEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void userUpdated(UserUpdateEvent userModifiedEvent) {
		// TODO Auto-generated method stub
		
	}

}
