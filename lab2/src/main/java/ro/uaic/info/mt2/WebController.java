package ro.uaic.info.mt2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.uaic.info.mt2.beans.Record;
import ro.uaic.info.mt2.decorators.RequestDecorator;

/**
 * Servlet implementation class Controller
 */
@WebServlet(name = "/WebController", urlPatterns = {"/"} )
public class WebController 
extends HttpServlet 
{
   private final static String COOKIE_NAME = "default-category";
   
   private Persistence persistence = new Persistence();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
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
            category = getServletContext().getAttribute("default-category").toString();
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
	
	private void storeCookie(HttpServletResponse response, int categoryId)
   {
      Cookie cookie = new Cookie(COOKIE_NAME, String.valueOf(categoryId));
      cookie.setComment("This cookie is used for storing the default category preference.");
      cookie.setMaxAge(7 * 24 * 60 * 60);
      response.addCookie(cookie);
   }
	
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
