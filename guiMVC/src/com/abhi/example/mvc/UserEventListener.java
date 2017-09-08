package com.abhi.example.mvc;

/* Any View who is interested in subscribing to User Related Events should 
 * implement this 
 */
public interface UserEventListener
{
	public void updateUserView(UserModel userModel /* Can we re-use UserAddEvent kind */);
}
