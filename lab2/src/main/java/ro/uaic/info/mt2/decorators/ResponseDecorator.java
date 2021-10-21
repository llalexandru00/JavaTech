package ro.uaic.info.mt2.decorators;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Servlet Filter implementation class ResponseDecorator
 */
@WebFilter(filterName = "/ResponseDecorator", urlPatterns = {"/result.jsp"}, dispatcherTypes = { DispatcherType.FORWARD })
public class ResponseDecorator implements Filter {


   /**
    * @see Filter#init(FilterConfig)
    */
   public void init(FilterConfig fConfig) throws ServletException {
      // TODO Auto-generated method stub
   }

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
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

class SimpleResponseWrapper
extends HttpServletResponseWrapper 
{
   private final StringWriter output = new StringWriter();

   public SimpleResponseWrapper(HttpServletResponse response)
   {
      super(response);
   }
   
   @Override
   public PrintWriter getWriter() 
   {
      return new PrintWriter(output);
   }
   
   @Override
   public String toString() 
   {
      return output.toString();
   }
   
}