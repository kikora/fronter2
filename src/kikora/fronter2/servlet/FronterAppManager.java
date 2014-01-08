package kikora.fronter2.servlet;

import javax.servlet.http.HttpSession;

import kikora.fronter2.api.FronterOauth2Info;
import kikora.fronter2.exceptions.FronterAppNotFoundException;
import kikora.fronter2.exceptions.FronterParameterNotFoundException;

/**
 * Fronter supports multiple sites using the same oauth user/secret. But different 
 * Fronter installations still need different keys. Support can be added here for
 * that, this is just a hard coded example.
 * @author leon
 *
 */
public class FronterAppManager
{
	
	public static FronterOauth2Info getInfo(String appId, String customer, String server) throws FronterAppNotFoundException
	{		
		String oauthUser = "kikora";
		String oauthSecret = "1234567890abcdef1234";
		
		return new FronterOauth2Info(oauthUser, oauthSecret, customer, "https://" + server + "/fronter2.ap");
	}
	
	public static FronterOauth2Info getInfo(HttpSession session, String server) throws Exception
	{
		String appId = (String)session.getAttribute(FronterRequestParams.APPID);
		String customer = (String)session.getAttribute(FronterRequestParams.CUSTOMER);
		
		if(appId == null || customer == null)
		{
			throw new FronterParameterNotFoundException("AppId=" + appId + " customer=" + customer + " must be on the session");
		}
		
		return getInfo(appId, customer, server);
	}
}
