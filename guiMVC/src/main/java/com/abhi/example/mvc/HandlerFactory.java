package com.abhi.example.mvc;

/* This class will help us in creating/fetching the Instance of Handlers */
/* But since a Specific Handler can have their own heirarchy, we need something more advance here
 * ... 
 */
public class HandlerFactory
{
	private static UserModelHandler userModelHandler = new UserModelHandler();
	
	HandlerFactory()
	{
		// Assigning a Default (first) controller associated with the Handler
		userModelHandler.addUserChangeListener(ControllerFactory.getMyUserController());
	}
	
	//List of other Handlers goes here ... 
	public static UserModelHandler getUserModelHandler()
	{
		return userModelHandler;
	}
}
