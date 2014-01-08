package kikora.fronter2.api.objects;

import java.lang.reflect.Type;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class UserDetails extends User
{
	private String initials;
	private String email;
	private String addr;
	private String primaryphone;
	private String alternatephone;
	private String cellphone;
	private String moreinfo;
	private int timezone;
	private String timezone_identifier;
	
	public String getInitials()
	{
		return initials;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getAddr()
	{
		return addr;
	}
	
	public String getPrimaryPhone()
	{
		return primaryphone;
	}
	
	public String getAlternatePhone()
	{
		return alternatephone;
	}
	
	public String getCellphone()
	{
		return cellphone;
	}
	
	public String getMoreinfo()
	{
		return moreinfo;
	}
	
	public int getTimezone()
	{
		return timezone;
	}
	
	public String getTimezone_identifier()
	{
		return timezone_identifier;
	}

	@Override
	public String toString()
	{
		return "UserDetails [initials=" + initials + ", email=" + email + ", addr=" + addr + ", primaryPhone=" + primaryphone + ", alternatePhone="
				+ alternatephone + ", cellphone=" + cellphone + ", moreinfo=" + moreinfo + ", timezone=" + timezone + ", timezone_identifier="
				+ timezone_identifier + ", toString()=" + super.toString() + "]";
	}
	
	/**
	 * Some parts of the Fronter API currently use all lower case, some camelCase, this
	 * adapter fixes that.
	 * @author leon
	 */
	public static class UserDetailsTypeAdapter implements JsonDeserializer<UserDetails>
	{
		@Override
		public UserDetails deserialize(JsonElement json, Type myClassType, JsonDeserializationContext context) throws JsonParseException
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
			
			return new Gson().fromJson(replacementJsonObject, UserDetails.class);
		}
	}
}
