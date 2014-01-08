package kikora.fronter2.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionUtil
{
	private static final Logger log = LoggerFactory.getLogger(SessionUtil.class); 
	
	public static String getSessionId(HttpServletRequest request)
	{
		try
		{
			return request.getSession(true).getId();
		}
		catch(IllegalStateException ex)
		{
			log.error("Session was invalidated on getSessionId", ex);
		}
		catch(NullPointerException ex)
		{
			log.error("Session was null", ex);
		}
		
		return null;
	}
	
	public static Object getAttribute(HttpServletRequest request, String name)
	{
		try
		{
			return request.getSession(true).getAttribute(name);
		}
		catch(IllegalStateException ex)
		{
			log.error("Session was invalidated on getAttribute", ex);
		}
		catch(NullPointerException ex)
		{
			log.error("Session was null", ex);
		}
		
		return null;
	}
	
	public static void setAttribute(HttpServletRequest request, String name, Object value)
	{
		try
		{
			request.getSession(true).setAttribute(name, value);
		}
		catch(IllegalStateException ex)
		{
			log.error("Session was invalidated on setAttribute", ex);
		}
		catch(NullPointerException ex)
		{
			log.error("Session was null", ex);
		}
	}
	
	public static void removeAttribute(HttpServletRequest request, String name)
	{
		try
		{
			request.getSession(true).removeAttribute(name);
		}
		catch(IllegalStateException ex)
		{
			log.error("Session was invalidated on removeAttribute", ex);
		}
		catch(NullPointerException ex)
		{
			log.error("Session was null", ex);
		}
	}
	
	public static void invalidate(HttpServletRequest request)
	{
		try
		{
			request.getSession(true).invalidate();
		}
		catch(IllegalStateException ex)
		{
			log.error("Session was allready invalidated", ex);
		}
		catch(NullPointerException ex)
		{
			log.error("Session was null", ex);
		}
	}
	
	public static HttpSession getSession(HttpServletRequest request)
	{
		return request.getSession(true);
	}

}
