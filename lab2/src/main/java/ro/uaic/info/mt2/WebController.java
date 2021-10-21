package ro.uaic.info.mt2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import ro.uaic.info.mt2.beans.Record;

/**
 * Main controller which handles both get and post methods. The get method will
 * be forwarded to the input page while the post method will be forwarded to the 
 * result page. Get method will also generate a captcha which will be stored
 * at a session level; the post method will store the result persistently.
 */
@WebServlet(name = "/WebController", urlPatterns = {"/"} )
public class WebController 
extends HttpServlet 
{
   /** The name of the cookie which will store the default category */
   private final static String COOKIE_NAME = "default-category";
   
   /** A persistence component which will handle the persistence jobs */
   private Persistence persistence = new Persistence();

	/**
	 * The get method which will set the default category based on the stored cookie,
	 * will retrieve all categories stored in the persistence and generate a captcha.
	 * All these will be sent to the input page.
	 */
   @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
   throws ServletException, IOException 
	{
      Cookie cookie = lookupCookie(request);
      request.setAttribute("defaultCategory", cookie != null ? cookie.getValue() : "1");
      request.setAttribute("allCategories", persistence.getCategories());
      String uuid = UUID.randomUUID().toString();
      request.getSession().setAttribute("target", uuid.substring(0, 5));
      getServletContext().getRequestDispatcher("/input.jsp").forward(request, response);
	}

	/**
	 * The post method will retrieve the parameters (category, key and value) and will
	 * generate a result bean which will be persisted. The category has a default based
	 * on a application based attribute and will be set as a default for the user through
	 * a cookie. Finally, the records will be sent to the result page.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
   throws ServletException, IOException 
	{
	   int categoryId = 0;
	   String key = "", value = "";
	   String captcha;
	   try
	   {
	      String category = request.getParameter("category");
         if (category == null || category.isEmpty())
         {
            category = getServletContext().getAttribute("default-category").toString();
         }
         categoryId = Integer.parseInt(category);
	      key = request.getParameter("key");
         if (key.length() > 50)
         {
            throw new IllegalArgumentException("Key length is greater than 50.");
         }
	      value = request.getParameter("value");
         if (value.length() > 1000)
	      {
            throw new IllegalArgumentException("Value length is greater than 1000.");
	      }
         captcha = request.getParameter("captcha");
         if (captcha == null || !captcha.equals(request.getSession().getAttribute("target")))
         {
            throw new IllegalArgumentException("Invalid CAPTCHA.");
         }
	   }
	   catch (IllegalArgumentException e)
	   {
         response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
         response.setContentType("text/plain");
         try (PrintWriter out = response.getWriter())
         {
            out.println("Bad request: " + e.getMessage());
         }
         return;
	   }
	   
	   storeCookie(response, categoryId);
	   persistence.saveRecord(new Record(persistence.getCategory(categoryId), key, value));
      request.setAttribute("allRecords", persistence.getRecords());
      getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
	}
	
	/**
	 * Helper method which stores a cookie containing a category id which will be used as a
	 * default for next get methods.
	 * 
	 * @param   response
	 *          The HTTP servler response.
	 * @param   categoryId
	 *          The category id which is meant to be stored in the cookie.
	 */
	private void storeCookie(HttpServletResponse response, int categoryId)
   {
      Cookie cookie = new Cookie(COOKIE_NAME, String.valueOf(categoryId));
      cookie.setComment("This cookie is used for storing the default category preference.");
      cookie.setMaxAge(7 * 24 * 60 * 60);
      response.addCookie(cookie);
   }
	
	/**
	 * Convenient method used to find the default category cookie.
	 * 
	 * @param   request
	 *          The HTTP servlet request.
	 *            
	 * @return  The default category cookie or {@code null} if it was not found.
	 */
	private Cookie lookupCookie(HttpServletRequest request)
	{
	   Cookie[] cookies = request.getCookies();
	   if (cookies == null) 
	   {
	      return null;
	   }
	   for (int i = 0; i < cookies.length; i++)
	   {
	      if (cookies[i].getName().equals(COOKIE_NAME))
	      {
	         return cookies[i];
	      }
	   }
	   return null;
	}

}
