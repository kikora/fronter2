package kikora.fronter2.api.objects;

public class Group 
{
	private static class GroupLinks
	{
		private Link slef;
		private Link users;
		
		public Link getSlef() 
		{
			return slef;
		}
		
		public Link getUsers() 
		{
			return users;
		}

		@Override
		public String toString() 
		{
			StringBuilder builder = new StringBuilder();
			
			builder.append("GroupLinks [slef=");
			builder.append(slef);
			builder.append(", users=");
			builder.append(users);
			builder.append("]");
			
			return builder.toString();
		}
		
	}
	
	private int id;
	private String title;
	private String type;
	private Access access;
	
	private GroupLinks _links;

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public Access getAccess() {
		return access;
	}

	public Link getSlefLink() 
	{
		return _links.getSlef();
	}

	public Link getUsersLink() 
	{
		return _links.getUsers();
	}

	@Override
	public String toString()
	{
		return "Group [id=" + id + ", title=" + title + ", type=" + type + ", access=" + access + ", _links=" + _links + "]";
	}
}
