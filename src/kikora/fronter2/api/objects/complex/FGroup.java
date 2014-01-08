package kikora.fronter2.api.objects.complex;

import java.util.ArrayList;
import java.util.List;

import kikora.fronter2.api.objects.Access;
import kikora.fronter2.api.objects.Group;
import kikora.fronter2.api.objects.User;

public class FGroup 
{
	private final Group group;
	private final List<FUser> members;

	public FGroup(Group group, List<User> members)
	{
		this.group = group;
		this.members = new ArrayList<>(members.size());
		
		for(User u : members)
		{
			FUser member = new FUser(u);
			
			this.members.add(member);
		}
	}
	
	public int getId()
	{
		return group.getId();
	}

	public String getTitle()
	{
		return group.getTitle();
	}

	public String getType()
	{
		return group.getType();
	}

	public Access getAccess()
	{
		return group.getAccess();
	}
	
	public List<FUser> getMembers()
	{
		return this.members;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("FGroup [group=");
		builder.append(group);
		builder.append(", members=");
		builder.append(members);
		builder.append("]");
		
		return builder.toString();
	}
}
