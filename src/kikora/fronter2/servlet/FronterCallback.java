package kikora.fronter2.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kikora.fronter2.api.FronterAccessToken;
import kikora.fronter2.api.FronterApi;
import kikora.fronter2.api.FronterOauth2Api;
import kikora.fronter2.api.FronterOauth2Info;
import kikora.fronter2.api.objects.complex.FGroup;
import kikora.fronter2.api.objects.complex.FRoom;
import kikora.fronter2.api.objects.complex.FUser;
import kikora.fronter2.utils.ApDebugUtils;
import kikora.fronter2.utils.SessionUtil;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the main Fronter Servlet that handles the login of the user after the callback
 * kicks them back to us. The FronterEntry class handles the initial contact and puts any
 * needed parameters on the session before sending the user off to get the access token.
 * Once they return they end up in this severlet.
 * @author leon
 *
 */
@WebServlet(description = "Access point for Fronter Oauth 2.0", urlPatterns = { "/fronter2.ap" })
public class FronterCallback extends HttpServlet
{
	private static final long serialVersionUID = 6124775721169638107L;
	
	private static final Logger log = LoggerFactory.getLogger(FronterCallback.class); 
	
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		try
		{
			log.info("FronterCallback: {}", ApDebugUtils.getRequestParams(request));
			log.info("FronterCallback: {}",  ApDebugUtils.getSessionParams(request));
			
			FronterOauth2Info info = FronterAppManager.getInfo(request.getSession(), request.getServerName());
		
			FronterAccessToken access = FronterOauth2Api.getAccessToken(info, request);
			
			FUser currentUser = FronterApi.getLoggedInUser(access);
			
			Integer roomId = (Integer)SessionUtil.getAttribute(request, FronterRequestParams.ROOMID);
			
			boolean loggedIn = true;

			/* Do some example API calls */
			if(loggedIn)
			{
				log.info("FronterCallback: roomid={}", roomId);
				
				if(roomId != null && roomId > 0)
				{
					FRoom room = FronterApi.getRoom(roomId, access);
					
					log.info("FronterCallback: room={}", room);
					
					if(room != null)
					{
						if(room.hasTeacherAccess())
						{
							/* Get all groups for the room */
							List<FGroup> groups = FronterApi.getRoomGroups(roomId, 100, access);
							
							log.info("FronterCallback: groups={}", groups);
							
							/* use the groups ... */
						}
					}
					else
					{
						log.error("FronterCallback: room={} not found for user={}", roomId, currentUser.getId());
					}
				}
				
				/* Dispatch to client */
				request.getRequestDispatcher("/c").forward(request, response);
			}
			else
			{
				/* Login failed */
				response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Failled to register new person");
			}
		} 
		catch (Exception e)
		{
			log.error("Exception in fronter callback: session={}" + ApDebugUtils.getSessionParams(request) + 
						" request={}"+ApDebugUtils.getRequestParams(request), e);
			
			response.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Error in Kikora-Fronter Access point");
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
        return "Fronter callback to Kikora";
    }
}
