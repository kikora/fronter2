package kikora.fronter2.api.objects;

import java.util.List;

public class RoomList extends BaseList<Room>
{
	public List<Room> getRooms()
	{
		return getList("rooms");
	}
}
