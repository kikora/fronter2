package kikora.fronter2.api.objects;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class User
{
	 private int id;
	 private String firstname;
	 private String lastname;
	 private String username;
	 
	public int getId()
	{
		return id;
	}
	
	public String getFirstName()
	{
		return firstname;
	}
	
	public String getLastName()
	{
		return lastname;
	}
	
	public String getUsername()
	{
		return username;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + ", firstName=" + firstname + ", lastName=" + lastname + ", username=" + username + "]";
	}
	
	/**
	 * Some parts of the Fronter API currently use all lower case, some camelCase, this
	 * adapter fixes that.
	 * @author leon
	 */
	public static class UserTypeAdapter implements JsonDeserializer<User>
	{
		@Override
		public User deserialize(JsonElement json, Type myClassType, JsonDeserializationContext context) throws JsonParseException
		{
			JsonObject originalJsonObject = json.getAsJsonObject();
			JsonObject replacementJsonObject = new JsonObject();
			
			for (Entry<String, JsonElement> elementEntry : originalJsonObject.entrySet())
			{
				String key = elementEntry.getKey();
				JsonElement value = originalJsonObject.get(key);
				key = key.toLowerCase();
				replacementJsonObject.add(key, value);
			}
			
			return new Gson().fromJson(replacementJsonObject, User.class);
		}
	}
}
