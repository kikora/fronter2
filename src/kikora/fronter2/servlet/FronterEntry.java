package kikora.fronter2.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kikora.fronter2.api.FronterOauth2Api;
import kikora.fronter2.api.FronterOauth2Info;
import kikora.fronter2.exceptions.FronterAppNotFoundException;
import kikora.fronter2.utils.ApDebugUtils;
import kikora.fronter2.utils.SessionUtil;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the first contact point for Fronter. The user lands here and parameters such as
 * roomid are saved on the session and the user  is then sent off to get a access token.
 * Example url: https://yoururlhere.com/fronterentry.ap?customer=#CUSTOMER_URL#&roomid=#ROOMID#&appid=norway
 * @author leon
 *
 */
@WebServlet(description = "Entry point for Fronter Oauth 2.0", urlPatterns = { "/fronterentry.ap" })
public class FronterEntry extends HttpServlet
{
	private static final long serialVersionUID = 6124775721169638107L;
	
	private static final Logger log = LoggerFactory.getLogger(FronterEntry.class); 
	
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		log.info("FronterEntry request: {}", ApDebugUtils.getRequestParams(request));
		log.info("FronterEntry session: {}", ApDebugUtils.getSessionParams(request));
		
		String customer = request.getParameter(FronterRequestParams.CUSTOMER);
		String appId = request.getParameter(FronterRequestParams.APPID);
		int roomId = getInt(request, FronterRequestParams.ROOMID);
		
		try 
		{
			/* Without customer and appId we can't proceed with the request since we don't know
			 * the correct key and/or which URLs to send API calls to.
			 */
			if(customer != null && appId != null)
			{
				FronterOauth2Info info = FronterAppManager.getInfo(appId, customer, request.getServerName());
				
				SessionUtil.setAttribute(request, FronterRequestParams.APPID, appId);
				SessionUtil.setAttribute(request, FronterRequestParams.CUSTOMER, customer);
				SessionUtil.setAttribute(request, FronterRequestParams.ROOMID, roomId);
				
				response.sendRedirect(FronterOauth2Api.getAuthorizationUrl(info));
			}
			else
			{
				response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Did not get customer=" + customer + " or appid=" + appId);
				log.error("Did not get correct parameters on login request: session={} request={}", 
						ApDebugUtils.getSessionParams(request), ApDebugUtils.getRequestParams(request));
			}
		} 
		catch (FronterAppNotFoundException e) 
		{
			response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Did not find app with id="+appId);
			log.error("Did not find app with id={} session={} request={}", 
					appId, ApDebugUtils.getSessionParams(request), ApDebugUtils.getRequestParams(request));
		}
    }
	
	private static int getInt(HttpServletRequest request, String paramName)
	{
		String tmp = request.getParameter(paramName);
		
		try
		{
			return Integer.parseInt(tmp);
		}
		catch(NumberFormatException e)
		{
			return -1;
		}
	}
	

	
    
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Fronter entrypoint into Kikora";
    }
}
