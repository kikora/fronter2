package kikora.fronter2.api;


public class FronterOauth2Info
{
	private final String clientId;
	private final String clientSecret;
	private final String baseUrl;
	private final String callbackUrl;
	private final String customer;
	
	/**
	 * 
	 * @param clientId Fronter consumer. For example: "kikora"
	 * @param clientSecret Consumer secret. For example: 9934c77ee4326158a6fe96aaaaac5aa2
	 * @param baseUrl base url of the Fronter site. For example: https://fronter.com/kikora
	 * @param callbackUrl Callback URL after successful authorization token retrieval, must be registered with Fronter beforehand. For example: https://example.no/fronter2.ap
	 */
	public FronterOauth2Info(String clientId, String clientSecret, String baseUrl, String callbackUrl)
	{
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.baseUrl = baseUrl;
		this.callbackUrl = callbackUrl;
		
		String temp = baseUrl.replaceAll("/$", "");
		int i = temp.lastIndexOf("/");
		
		if(i > 0 && i < baseUrl.length())
		{
			this.customer = temp.substring(i+1);
		}
		else
		{
			this.customer = baseUrl;
		}
	}
	
	public String getClientId()
	{
		return clientId;
	}

	public String getClientSecret()
	{
		return clientSecret;
	}

	public String getBaseUrl()
	{
		return baseUrl;
	}
	
	public String getCustomer()
	{
		return customer;
	}

	public String getCallbackUrl()
	{
		return callbackUrl;
	}

	@Override
	public String toString()
	{
		return "FronterOauth2Info [clientId=" + clientId + ", clientSecret=" + clientSecret + ", customer=" + customer + ", baseUrl=" + baseUrl + ", callbackUrl=" + callbackUrl
				+ "]";
	}
}
