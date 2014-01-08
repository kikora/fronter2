package kikora.fronter2.api;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import kikora.fronter2.api.objects.Oauth2AccessToken;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Handles the oauth authentication and subsequent communication with Fronter.
 * @author leon
 */
public class FronterOauth2Api
{
	private static final char QUERY_STRING_SEPARATOR = '?';
	private static final String PARAM_SEPARATOR = "&";
	private static final String ACCESS_TOKEN = "access_token";

	private static final String REDIRECT_URI = "redirect_uri";
	private static final String CLIENT_ID = "client_id";
	private static final String CLIENT_SECRET = "client_secret";
	private static final String CODE = "code";
	private static final String RESPONSE_TYPE = "response_type";
	private static final String GRANT_TYPE = "grant_type";
	private static final String AUTHORIZATION_CODE = "authorization_code";
	
	private static final Gson gson = new GsonBuilder().create();
	private static final Logger log = LoggerFactory.getLogger(FronterOauth2Api.class); 
	
	private static final String AUTHORIZE_URL = "app/oauth2/authorization";
	private static final String TOKEN_URL = "app/oauth2/token";
	
	public static String appendUrl(String base, String ext)
	{
		if(base.endsWith("/"))
		{
			return base + ext;
		}
		else
		{
			return base + "/" + ext;
		}
	}
	
	public static String appendQuerryParameters(String url, List<NameValuePair> params)
	{
		String res = url;
		
		res += url.indexOf(QUERY_STRING_SEPARATOR) != -1 ? PARAM_SEPARATOR : QUERY_STRING_SEPARATOR;
		
		return res + URLEncodedUtils.format(params, "utf-8");
	}
	
	public static String appendQuerryParameter(String url, NameValuePair pair)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(pair);
		
		return appendQuerryParameters(url, params);
	}
	
	
	private static String getAccessTokenRequestUrl(FronterOauth2Info info, String authCode)
	{
		List<NameValuePair> autorizeParams = new ArrayList<>(3);
		
		autorizeParams.add(new BasicNameValuePair(CLIENT_ID, info.getClientId()));
		autorizeParams.add(new BasicNameValuePair(CLIENT_SECRET, info.getClientSecret()));
		autorizeParams.add(new BasicNameValuePair(REDIRECT_URI, info.getCallbackUrl()));
		autorizeParams.add(new BasicNameValuePair(CODE, authCode));
		autorizeParams.add(new BasicNameValuePair(GRANT_TYPE, AUTHORIZATION_CODE));
		
		String tokenUrl = appendQuerryParameters(TOKEN_URL, autorizeParams);
		
		return appendUrl(info.getBaseUrl(), tokenUrl);
	}

	public static String getAuthorizationUrl(FronterOauth2Info info)
	{
		List<NameValuePair> autorizeParams = new ArrayList<>(3);
		
		autorizeParams.add(new BasicNameValuePair(CLIENT_ID, info.getClientId()));
		autorizeParams.add(new BasicNameValuePair(REDIRECT_URI, info.getCallbackUrl()));
		autorizeParams.add(new BasicNameValuePair(RESPONSE_TYPE, CODE));
		
		String authUrl = appendQuerryParameters(AUTHORIZE_URL, autorizeParams);
		
		return appendUrl(info.getBaseUrl(), authUrl);
	}
	
	public static FronterAccessToken getAccessToken(FronterOauth2Info info, HttpServletRequest request) throws Exception
	{
		String code = request.getParameter(CODE);
		
		if(code == null)
		{
			throw new Exception("Fronter: code is null when getting access token");
		}
		
		return getAccessToken(info, code);
	}

	public static FronterAccessToken getAccessToken(FronterOauth2Info info, String authCode) throws ClientProtocolException, IOException
	{
		String requestUrl = getAccessTokenRequestUrl(info, authCode);
		
		HttpGet request = new HttpGet(requestUrl);
		
		String json = null;
		
		try
		{
			/* send the request */
			HttpClient httpClient = new DefaultHttpClient();

			json = httpClient.execute(request,new BasicResponseHandler());
		}
		catch(HttpResponseException ex)
		{
			log.error("getAccessToken({},{}) Fronter responded with exception", info, authCode, ex);

			/* Throw the message further up so we can do proper error handling */
			throw new HttpResponseException(ex.getStatusCode(), ex.getMessage());
		}
		
		System.out.println("FronterOauth2Api: " + json);
		
		Oauth2AccessToken token = gson.fromJson(json, Oauth2AccessToken.class);
		
		return new FronterAccessToken(info.getBaseUrl(), token);
	}
	
	public static String get(String url, Oauth2AccessToken token, List<NameValuePair> params) throws ClientProtocolException, IOException
	{
		List<NameValuePair> realParams = new ArrayList<>(params == null ? 1 : params.size() + 1);
		
		if(params != null)
		{
			realParams.addAll(params);
		}
		
		realParams.add(new BasicNameValuePair(ACCESS_TOKEN, token.getAccessToken()));
		
		String requestUrl = appendQuerryParameters(url, realParams);
		
		HttpGet request = new HttpGet(requestUrl);
		
		String json = null;
		
		try
		{
			/* send the request */
			HttpClient httpClient = new DefaultHttpClient();

			json = httpClient.execute(request,new BasicResponseHandler());
		}
		catch(HttpResponseException ex)
		{
			log.error("get({},{}) Fronter responded with exception, requestURL = {}", url, token, requestUrl, ex);

			/* Throw the message further up so we can do proper error handling */
			throw ex;
		}
		
		return json;
	}
	
	public static String get(String url, Oauth2AccessToken token) throws ClientProtocolException, IOException
	{
		return get(url, token, null);
	}
}