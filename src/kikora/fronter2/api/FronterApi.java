package kikora.fronter2.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kikora.fronter2.api.objects.Group;
import kikora.fronter2.api.objects.GroupList;
import kikora.fronter2.api.objects.Room;
import kikora.fronter2.api.objects.UserDetails;
import kikora.fronter2.api.objects.UserList;
import kikora.fronter2.api.objects.complex.FGroup;
import kikora.fronter2.api.objects.complex.FRoom;
import kikora.fronter2.api.objects.complex.FUser;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the high level interface for Fronter's API. It uses the FronterApiJson class
 * for low level operations and returns more complex objects, such as groups with members
 * in them, that can be the result of several low level calls.
 * @author leon
 */
public class FronterApi
{
	private static final Logger log = LoggerFactory.getLogger(FronterApi.class); 
	
	/**
	 * Get the currently logged in user.
	 * @param access
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static FUser getLoggedInUser(FronterAccessToken access) throws ClientProtocolException, IOException
	{
		UserDetails ud = FronterApiJson.getLoggedInUser(access);
		
		return new FUser(ud);
	}
	
	/**
	 * Retrieve room
	 * @param roomId
	 * @param access
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static FRoom getRoom(int roomId, FronterAccessToken access) throws ClientProtocolException, IOException
	{
		try
		{
			Room room = FronterApiJson.getRoom(roomId, access);
			
			return new FRoom(room);
		}
		catch(HttpResponseException e)
		{
			log.debug("Fronter API: Error when getting room groups: {}", e);
			
			if(e.getStatusCode() != HttpStatus.SC_NOT_FOUND)
			{
				/* All other errors are code errors, so throw them further out */
				throw e;
			}
		}
		
		return null;
	}
	
	/**
	 * Get all groups in a room with complete student lists, provided that the group has less than sizeLimit members.
	 * Currently does not support paginaition, so the max page size supported by Fronter is the ultimate sizeLimit.
	 * @param roomId
	 * @param sizeLimit Groups with more members than this will not be included.
	 * @param access Access tooken for API calls.
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static List<FGroup> getRoomGroups(int roomId, int sizeLimit, FronterAccessToken access) throws ClientProtocolException, IOException
	{
		GroupList gl;
	
		try
		{
			gl = FronterApiJson.getRoomMembers(roomId, "type::Group", access);
		}
		catch(HttpResponseException e)
		{
			log.debug("Fronter API: Error when getting room groups: {}", e);
			
			if(e.getStatusCode() == HttpStatus.SC_NOT_FOUND)
			{
				/* Group is empty (or room not found) */
				return new ArrayList<>();
			}
			else
			{
				/* All other errors are code errors, so throw them further out */
				throw e;
			}
		}
		
		ArrayList<FGroup> groups =  new ArrayList<>();
		
		for(Group g : gl.getGroups())
		{
			UserList ul = FronterApiJson.getGroupMembers(g.getId(), access);
			
			if(ul.getTotal() <= sizeLimit)
			{
				groups.add(new FGroup(g,ul.getUsers()));
			}
			else
			{
				log.info("Skipping group {} due to size={} larger than size limit={}", g, ul.getTotal(), sizeLimit);
			}
		}
		
		return groups;
	}
}
