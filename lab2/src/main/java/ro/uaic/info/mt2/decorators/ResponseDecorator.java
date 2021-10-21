package ro.uaic.info.mt2.decorators;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * A response decorator for the forward operations towards the result page.
 */
@WebFilter(filterName = "/ResponseDecorator", urlPatterns = {"/result.jsp"}, dispatcherTypes = { DispatcherType.FORWARD })
public class ResponseDecorator 
implements Filter 
{
	/**
	 * The core filter method which will add a header and a footer to the generated html by the chain.
	 */
	public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) 
   throws IOException, ServletException 
	{
	   HttpServletResponse response = (HttpServletResponse) res;
	   SimpleResponseWrapper wrapper = new SimpleResponseWrapper(response);
      wrapper.getWriter().write("<container><h1>All records</h1></container>");
		chain.doFilter(request, wrapper);
      wrapper.getWriter().write("<container><p>List of all records</p></container>");
      response.getWriter().write(wrapper.toString());
	}
}

/**
 * A simple response wrapper used for safely propagation of request through the chain.
 */
class SimpleResponseWrapper
extends HttpServletResponseWrapper 
{
   /** The output of the response wrapped by this class */
   private final StringWriter output = new StringWriter();

   /**
    * Basic constructor.
    * 
    * @param    response
    *           The response which will be wrapper by this class.
    */
   public SimpleResponseWrapper(HttpServletResponse response)
   {
      super(response);
   }
   
   /**
    * Override of the writer getter to provide the local output writer.
    */
   @Override
   public PrintWriter getWriter() 
   {
      return new PrintWriter(output);
   }
   
   /**
    * Convenient to string method.
    */
   @Override
   public String toString() 
   {
      return output.toString();
   }
   
}