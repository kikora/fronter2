package kikora.fronter2.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ApDebugUtils
{
	public static String getRequestParams(HttpServletRequest request)
	{
		StringBuilder sb = new StringBuilder();
		
		Map<?,?> map = request.getParameterMap();
		
		for(Object param: map.keySet())
		{
			sb.append(param);
			sb.append(" - ");
			sb.append(request.getParameter((String)param));
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	public static String getHeaderData(HttpServletRequest request)
	{
		Enumeration<String> headers = request.getHeaderNames();
		
		StringBuilder sb = new StringBuilder();

		while(headers.hasMoreElements())
		{
			String name = headers.nextElement();
			String data = request.getHeader(name);
			
			sb.append("---------------------------------------------------------------\n---------------------------------------------------------------\n");
			sb.append(name);
			sb.append(" - ");
			sb.append(data);
			sb.append("\n");
//			sb.append("----------------------:");
//			request.getHeaders(arg0)
		}
		
		return sb.toString();
	}
	
//	private static void createHeaderData(StringBuilder sb, S)
	
	public static String getSessionParams(HttpServletRequest request)
	{
		StringBuilder sb = new StringBuilder();
		
		HttpSession session = request.getSession();
		Enumeration<String> names = session.getAttributeNames();
		
		while(names.hasMoreElements())
		{
			String name = names.nextElement();
			
			sb.append(name);
			sb.append(" - ");
			sb.append(session.getAttribute(name));
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * Get the url of the site the user comes from, useful for debugging
	 */
	public static String getReferer(HttpServletRequest request)
	{
		String ref = request.getHeader("Referer");
		
		if(ref == null)
		{
			ref = "";
		}
		else
		{
			try
			{
				ref = URLEncoder.encode(ref, "UTF-8");
			}
			catch(UnsupportedEncodingException e)
			{
				ref = "nosupport";
			}
		}
		
		return ref;
	}
}
