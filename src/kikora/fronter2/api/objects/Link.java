package kikora.fronter2.api.objects;

public class Link 
{
	private String href;
	private boolean templated;
	
	public String getHref()
	{
		return href;
	}
	
	public boolean isTemplated()
	{
		return templated;
	}

	@Override
	public String toString() 
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Link [href=");
		builder.append(href);
		builder.append(", templated=");
		builder.append(templated);
		builder.append("]");
		
		return builder.toString();
	}
}
