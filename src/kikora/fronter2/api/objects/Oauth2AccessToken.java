package kikora.fronter2.api.objects;

public class Oauth2AccessToken
{
	private String access_token;
	private long expires_in;
	private String refresh_token;
	private String scope;
	
	public String getAccessToken()
	{
		return access_token;
	}
	
	public long getExpiresIn()
	{
		return expires_in;
	}

	public String getRefreshToken()
	{
		return refresh_token;
	}

	public String getScope()
	{
		return scope;
	}

	@Override
	public String toString()
	{
		return "Oauth2AccessToken [access_token=" + access_token + ", expires_in=" + expires_in + ", refresh_token=" + refresh_token + ", scope="
				+ scope + "]";
	}
}
