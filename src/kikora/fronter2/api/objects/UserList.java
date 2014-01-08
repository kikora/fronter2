package kikora.fronter2.api.objects;

import java.util.List;

public class UserList extends BaseList<User>
{
	public List<User> getUsers()
	{
		return getList("users");
	}
}
