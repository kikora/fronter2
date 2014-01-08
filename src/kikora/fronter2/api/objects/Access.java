package kikora.fronter2.api.objects;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;


public enum Access
{
	/**
	 * We map delte and owner rights as beeing teachers. This seems to be correct
	 * for all rooms except "sys:user", which is a special room where even students 
	 * has delete rights.
	 */
	OWNER(true),
	DELETE(true),
	WRITE(false),
	READ(false);
	
	private final boolean teacher;

	private Access(boolean teacher)
	{
		this.teacher = teacher;
	}

	public boolean isTeacher()
	{
		return teacher;
	}

	/**
	 * Some parts of the Fronter API uses all caps, some just the first letter. This adapter allows
	 * GSON to parse it anyway.
	 * @author leon
	 */
	public static class AccessTypeAdapter implements JsonDeserializer<Access>
	{
		@Override
		public Access deserialize(JsonElement json, Type myClassType, JsonDeserializationContext context) throws JsonParseException
		{
			return new Gson().fromJson(json.getAsString().toUpperCase(), Access.class);
		}
	}
}
