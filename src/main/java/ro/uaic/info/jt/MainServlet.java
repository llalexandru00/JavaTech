package ro.uaic.info.jt;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The main Servlet which allows HTTP get methods with key, value, mock and sync parameters.
 * After request, the key is printed value times in the repository and the whole repository sorted by
 * keys will be returned as response. The sync parameter is used to enforce synchronization on repository
 * access, while mock flag is set in order to ping the servlet.
 */
public class MainServlet 
extends HttpServlet 
{
   
   /** Date formatter used when attaching the current time of the request to the repository */
   private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
   
   /** Basic logger */
   private final Logger LOG = Logger.getLogger(MainServlet.class.getName());
   
   /** The main repository used by all clients */
   private Repository rep = new Repository("C:\\tmp\\repository.txt");
   
   /**
    * Get method implementation which allows requests using key, value, sync and mock. This should
    * validate the parameters and return bad request or accepted, together with the sorted repository
    * or with a confirmation message if mock is set.
    */
   @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
   throws ServletException, IOException 
	{
      log(request);
	   try
	   {
	      String key = request.getParameter("key");
	      int value = Integer.valueOf(request.getParameter("value"));
	      String mockStr = request.getParameter("mock");
	      boolean mock = mockStr != null ? mockStr.equals("on") : false;
         String syncStr = request.getParameter("sync");
	      boolean sync = syncStr != null ? syncStr.equals("on") : false;
	      if (key.contains(" "))
	      {
	         throw new IllegalArgumentException("Key can't contain spaces.");
	      }
	      if (mock)
         {
	         doMock(response);
         }
	      else
	      {
	         doJob(key, value, sync, response);
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
	   }
	}
	
	/**
	 * Basic log helper which is formatting the request and appending it to the log.
	 * 
	 * @param   request
	 *          The request which should be logged.
	 */
	private void log(HttpServletRequest request)
	{
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
      LOG.info("[GET] " + request.getRemoteAddr() + " with " + request.getHeader("User-Agent") + " supporting " + 
            localesStr + " having parameters: " + paramsStr);
	}
	
	/**
	 * The resolver in case the mock flag is set.
	 * 
	 * @param   response
	 *          The response in which the confirmation should be appended.
	 */
	private void doMock(HttpServletResponse response) 
   throws IOException
	{
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      response.setContentType("text/plain");
      try (PrintWriter out = response.getWriter()) 
      {
         out.println("Confirmation");
      }
	}
	
	/**
	 * The core job done when the servlet is no used with the mock flag set. This should format the 
	 * current date and together with the key, appended value times, to be sent to the repository.
	 * The repository access is synchronized only if the sync flag is set.
	 * 
	 * @param   key
	 *          The key which should be appended value times and sent to the repository.
	 * @param   value
	 *          The number of times for which the key should be appended.
	 * @param   sync
	 *          A flag to indicate if the access to the repository should be synchronized or not.
	 * @param   response
	 *          The servlet response.
	 */
	private void doJob(String key, int value, boolean sync, HttpServletResponse response) 
   throws IOException
	{
	   StringBuilder sb = new StringBuilder();
	   sb.append(SDF.format(new Date())).append(" ");
	   for (int i = 0; i < value; i++) sb.append(key);
	   sb.append("\n");
	   if (sync)
	   {
	      synchronized(rep)
	      {
	         doJob(sb.toString(), response);
	      }
	   }
	   else
	   {
         doJob(sb.toString(), response);
	   }
	}

	/**
	 * A sub-routine which strictly handles the repository writing, sorting and the response.
	 * At this stage, the status should be accepted as long as there is no IO exception.
	 * 
	 * @param   text
	 *          The already formatted text which should be added to the repository.
	 * @param   response
	 *          The servlet response.
	 * @throws IOException
	 */
   private void doJob(String text, HttpServletResponse response) 
   throws IOException
   {
      rep.write(text);
      String content = rep.getContent();
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      response.setContentType("text/html");
      String[] lines = content.split("\n");
      Arrays.sort(lines, new KeyComparator());
      try (PrintWriter out = response.getWriter()) 
      {
         for (String line : lines)
         {
            out.println(line);
         }
      }
   }
	
   /**
    * Comparator used for sorting the lines in the repository by key.
    */
	class KeyComparator 
	implements Comparator<String>
	{
	   /**
	    * Comparison of the repository lines by key.
	    */
      @Override
      public int compare(String a, String b)
      {
         return getKey(a).compareTo(getKey(b));
      }
      
      /**
       * This splits the repository line and extracts the key.
       * 
       * @param   repoLine
       *          The repository line for which the key is searched for.
       *          
       * @return  The key used when generating this repository line.
       */
      private String getKey(String repoLine)
      {
        String[] chunks = repoLine.split(" ");
        assert chunks.length == 3;
        return chunks[2];
      }
	   
	}
}
