package kikora.fronter2.exceptions;

public class FronterAppNotFoundException extends Exception 
{
	private static final long serialVersionUID = 1L;
	
	public FronterAppNotFoundException(String message)
	{
		super(message);
	}
	
	public FronterAppNotFoundException(Throwable t)
	{
		super(t);
	}
}
