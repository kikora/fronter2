package kikora.fronter2.api.objects.complex;

import kikora.fronter2.api.objects.User;

public class FUser 
{
	private final User user;
	
	public FUser(User user) 
	{
		super();
		this.user = user;
	}

	public int getId() 
	{
		return user.getId();
	}

	public String getFirstName() 
	{
		return user.getFirstName();
	}

	public String getLastName() 
	{
		return user.getLastName();
	}

	public String getUsername() 
	{
		return user.getUsername();
	}

	public String toString() 
	{
		return user.toString();
	}
}
