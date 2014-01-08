package kikora.fronter2.api.objects;

import java.util.Date;

public class Room 
{
	public static class RoomLinks
	{
		private Link self;
		private Link corridor;
		private Link room_illustratio;
		private Link profile;
		private Link created_by;
		
		public Link getSelf() {
			return self;
		}
		
		public Link getCorridor() {
			return corridor;
		}
		
		public Link getRoomIllustratio() {
			return room_illustratio;
		}
		
		public Link getProfile() {
			return profile;
		}
		
		public Link getCreatedBy() {
			return created_by;
		}

		@Override
		public String toString() 
		{
			StringBuilder builder = new StringBuilder();
			
			builder.append("RoomLinks [self=");
			builder.append(self);
			builder.append(", corridor=");
			builder.append(corridor);
			builder.append(", room_illustratio=");
			builder.append(room_illustratio);
			builder.append(", profile=");
			builder.append(profile);
			builder.append(", created_by=");
			builder.append(created_by);
			builder.append("]");
			
			return builder.toString();
		}
	}
	
	private int id; 
	private String title; 
	private String roomtype;
	private int created_by; 
	private Date created_datetime; 
	private String purpose;
	private int corridor; 
	private String room_illustration;
	private int profile; 
	private String importsource;
	private int frontpageid; 
	private Access access;
	
	private RoomLinks _links;
	
	public int getId() 
	{
		return id;
	}
	
	public String getTitle() 
	{
		return title;
	}
	
	public String getRoomtype() 
	{
		return roomtype;
	}
	
	public int getCreatedBy() 
	{
		return created_by;
	}
	
	public Date getCreated() 
	{
		return created_datetime;
	}
	
	public String getPurpose() 
	{
		return purpose;
	}
	
	public int getCorridor() 
	{
		return corridor;
	}
	
	public String getRoomIllustration() 
	{
		return room_illustration;
	}
	
	public int getProfile() 
	{
		return profile;
	}
	public String getImportSource() 
	{
		return importsource;
	}
	
	public int getFrontpageId() 
	{
		return frontpageid;
	}
	
	public Access getAccess() 
	{
		return access;
	}
	
	public Link getSelfLink() {
		return _links.getSelf();
	}

	public Link getCorridorLink() {
		return _links.getCorridor();
	}

	public Link getRoomIllustratioLink() {
		return _links.getRoomIllustratio();
	}

	public Link getProfileLink() {
		return _links.getProfile();
	}

	public Link getCreatedByLink() {
		return _links.getCreatedBy();
	}

	@Override
	public String toString() 
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("Room [id=");
		builder.append(id);
		builder.append(", title=");
		builder.append(title);
		builder.append(", roomtype=");
		builder.append(roomtype);
		builder.append(", created_by=");
		builder.append(created_by);
		builder.append(", created_datetime=");
		builder.append(created_datetime);
		builder.append(", purpose=");
		builder.append(purpose);
		builder.append(", corridor=");
		builder.append(corridor);
		builder.append(", room_illustration=");
		builder.append(room_illustration);
		builder.append(", profile=");
		builder.append(profile);
		builder.append(", importsource=");
		builder.append(importsource);
		builder.append(", frontpageid=");
		builder.append(frontpageid);
		builder.append(", access=");
		builder.append(access);
		builder.append(", _links=");
		builder.append(_links);
		builder.append("]");
		
		return builder.toString();
	}
}
