package ro.uaic.info.mt2.decorators;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Decorator for each forward operation towards the input page.
 */
@WebFilter(filterName = "/RequestDecorator", urlPatterns = {"/input.jsp"}, dispatcherTypes = { DispatcherType.FORWARD })
public class RequestDecorator 
implements Filter 
{
   /** Basic logger */
   private final Logger LOG = Logger.getLogger(RequestDecorator.class.getName());
   
	/**
	 * The core filter method which logs all requests towards the input page.
	 */
	public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) 
   throws IOException, ServletException 
	{
	   HttpServletRequest request = (HttpServletRequest) req;

      List<Locale> locales = Collections.list(request.getLocales());
      Map<String, String[]> params = request.getParameterMap();
      String localesStr = locales
            .stream()
            .map(Locale::toString)
            .collect(Collectors.joining(", "));
      String paramsStr = params.entrySet()
            .stream()
            .map((entry) -> "(" + entry.getKey() + ", " + String.join(", ", entry.getValue()) +  ")")
            .collect(Collectors.joining(", "));
      LOG.info("[" + request.getMethod() + "] " + request.getRemoteAddr() + " with " + request.getHeader("User-Agent") + " supporting " + 
            localesStr + " having parameters: " + paramsStr);

		chain.doFilter(request, response);
	}

}
