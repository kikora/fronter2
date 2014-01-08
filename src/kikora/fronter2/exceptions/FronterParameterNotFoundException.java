package kikora.fronter2.exceptions;

public class FronterParameterNotFoundException extends Exception 
{
	private static final long serialVersionUID = 1L;
	
	public FronterParameterNotFoundException(String message)
	{
		super(message);
	}
	
	public FronterParameterNotFoundException(Throwable t)
	{
		super(t);
	}
}
