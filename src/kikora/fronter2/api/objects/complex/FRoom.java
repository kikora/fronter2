package kikora.fronter2.api.objects.complex;

import java.util.Date;

import kikora.fronter2.api.objects.Access;
import kikora.fronter2.api.objects.Room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FRoom
{
	private static final Logger log = LoggerFactory.getLogger(FRoom.class); 
	
	private final Room room;

	public FRoom(Room room)
	{
		super();
		this.room = room;
	}

	public int getId()
	{
		return room.getId();
	}

	public String getTitle()
	{
		return room.getTitle();
	}

	public String getRoomtype()
	{
		return room.getRoomtype();
	}

	public int getCreatedBy()
	{
		return room.getCreatedBy();
	}

	public Date getCreated()
	{
		return room.getCreated();
	}

	public String getPurpose()
	{
		return room.getPurpose();
	}

	public int getCorridor()
	{
		return room.getCorridor();
	}

	public int getProfile()
	{
		return room.getProfile();
	}

	public int getFrontpageId()
	{
		return room.getFrontpageId();
	}

	public Access getAccess()
	{
		return room.getAccess();
	}

	public boolean hasTeacherAccess()
	{
		if(room.getAccess() != null)
		{
			return room.getAccess().isTeacher();
		}
		else
		{
			log.error("Fronter: Room access null for  {}", this);
		}
		
		return false;
	}
	
	public String toString()
	{
		return room.toString();
	}
}
