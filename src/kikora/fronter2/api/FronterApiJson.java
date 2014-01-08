package kikora.fronter2.api;

import java.io.IOException;

import kikora.fronter2.api.objects.Access;
import kikora.fronter2.api.objects.Access.AccessTypeAdapter;
import kikora.fronter2.api.objects.GroupList;
import kikora.fronter2.api.objects.Room;
import kikora.fronter2.api.objects.RoomList;
import kikora.fronter2.api.objects.User;
import kikora.fronter2.api.objects.User.UserTypeAdapter;
import kikora.fronter2.api.objects.UserDetails;
import kikora.fronter2.api.objects.UserDetails.UserDetailsTypeAdapter;
import kikora.fronter2.api.objects.UserList;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This is the low level API that talks directly to Fronter's REST API using JSON.
 * It uses low level objects that directly corresponds to the objects in the Fronter API.
 * @author leon
 */
public class FronterApiJson
{
	public enum API
	{
		oauthdetails,
		users,
		rooms,
		folders,
		elements,
		groups,
		persons;
		
		@Override
		public String toString()
		{
			return this.name();
		}
	}
	
	private static final Logger log = LoggerFactory.getLogger(FronterApiJson.class); 
	
	private static final Gson gson = new GsonBuilder().registerTypeAdapter(UserDetails.class, new UserDetailsTypeAdapter()).registerTypeAdapter(User.class, new UserTypeAdapter()).registerTypeAdapter(Access.class, new AccessTypeAdapter()).setDateFormat("yyyy-MM-dd hh:mm:ss").create();
	
	/**
	 * Get information on the currently logged in user
	 * @param access
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static UserDetails getLoggedInUser(FronterAccessToken access) throws ClientProtocolException, IOException
	{
		String json = FronterOauth2Api.get(FronterOauth2Api.appendUrl(access.getOpenApiUrl(), API.users.name() + "/me"), access.getAccessToken());
		
		log.debug(json);
		
		UserDetails ud = gson.fromJson(json, UserDetails.class);
		
		return ud;
	}
	
	/**
	 * Get list of rooms for the current user
	 * @param access
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static RoomList getRooms(FronterAccessToken access) throws ClientProtocolException, IOException
	{
		String json = FronterOauth2Api.get(FronterOauth2Api.appendUrl(access.getOpenApiUrl(), API.rooms.name()), access.getAccessToken());
		
		log.debug(json);
		
		RoomList ud = gson.fromJson(json, RoomList.class);
		
		return ud;
	}
	
	/**
	 * Get details on a given room
	 * @param roomId
	 * @param access
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static Room getRoom(int roomId, FronterAccessToken access) throws ClientProtocolException, IOException
	{
		String json = FronterOauth2Api.get(FronterOauth2Api.appendUrl(access.getOpenApiUrl(), API.rooms.name() + "/"+roomId), access.getAccessToken());
		
		log.debug(json);
		
		Room room = gson.fromJson(json, Room.class);
		
		return room;
	}

	public static UserList getUsers(FronterAccessToken access) throws ClientProtocolException, IOException
	{
		String json = FronterOauth2Api.get(FronterOauth2Api.appendUrl(access.getOpenApiUrl(), API.users.name()), access.getAccessToken());
		
		UserList ul = gson.fromJson(json, UserList.class);
		
		return ul;
	}
	
	/**
	 * List of room memebers. Does not support pagination.
	 * @param roomId
	 * @param filter Filter on attributes, for example "type::Group"
	 * @param access Access token
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static GroupList getRoomMembers(int roomId, String filter, FronterAccessToken access) throws ClientProtocolException, IOException
	{
		String requestUrl = FronterOauth2Api.appendUrl(access.getOpenApiUrl(), API.rooms.name() + "/" + roomId + "/members");
		
		if(filter != null)
		{
			requestUrl += "?filter=" + filter;
		}
		
		String json = FronterOauth2Api.get(requestUrl, access.getAccessToken());
		
		GroupList gl = gson.fromJson(json, GroupList.class);
		
		return gl;
	}
	
	/**
	 * Get a list of members for a given group. Does not support pagination.
	 * @param groupId
	 * @param access
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static UserList getGroupMembers(int groupId, FronterAccessToken access) throws ClientProtocolException, IOException
	{
		String json = FronterOauth2Api.get(FronterOauth2Api.appendUrl(access.getOpenApiUrl(), API.groups.name() + "/" + groupId + "/users"), access.getAccessToken());
		
		UserList ul = gson.fromJson(json, UserList.class);
		
		return ul;
	}
}
