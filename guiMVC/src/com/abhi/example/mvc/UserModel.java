package com.abhi.example.mvc;

import java.util.List;

public class UserModel
{
	private String id;
	private String firstName;
	private String lastName;
	private List<String> optedSubjects;
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public List<String> getOptedSubjects()
	{
		return optedSubjects;
	}
	
	public void setOptedSubjects(List<String> optedSubjects)
	{
		this.optedSubjects = optedSubjects;
	}
	
}
