package kikora.fronter2.api;

import kikora.fronter2.api.objects.Oauth2AccessToken;

public class FronterAccessToken
{
	private static final String API2 = "app/api/v2/";
	
	private final Oauth2AccessToken accessToken;
	private final String apiUrl;

	public FronterAccessToken(String baseUrl, Oauth2AccessToken accessToken)
	{
		this.apiUrl = FronterOauth2Api.appendUrl(baseUrl, API2);
		this.accessToken = accessToken;
	}

	public String getOpenApiUrl()
	{
		return apiUrl;
	}
	
	public Oauth2AccessToken getAccessToken()
	{
		return accessToken;
	}

	@Override
	public String toString()
	{
		return "FronterAccessToken [accessToken=" + accessToken + ", apiUrl=" + apiUrl + "]";
	}
}
