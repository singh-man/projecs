package com.abhi.example.mvc;

public class ControllerFactory
{
	private static UserController myUserController = new UserController();
	
	public static UserController getMyUserController()
	{
		return myUserController;
	}
}
