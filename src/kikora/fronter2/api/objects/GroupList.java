package kikora.fronter2.api.objects;

import java.util.List;

public class GroupList extends BaseList<Group>
{
	public List<Group> getGroups()
	{
		return getList("members");
	}
}
