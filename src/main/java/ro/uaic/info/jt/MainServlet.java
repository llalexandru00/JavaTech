package ro.uaic.info.jt;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet 
extends HttpServlet 
{
   private Repository rep = new Repository();
   private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
   private Logger LOG = Logger.getLogger(MainServlet.class.getName());
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
   throws ServletException, IOException 
	{
      log(request);
	   try
	   {
	      String key = request.getParameter("key");
	      int value = Integer.valueOf(request.getParameter("value"));
	      boolean mock = request.getParameter("mock") != null ? request.getParameter("mock").equals("on") : false;
	      boolean sync = request.getParameter("sync") != null ? request.getParameter("sync").equals("on") : false;
	      if (key.contains(" "))
	      {
	         throw new IllegalArgumentException("Key can't contain spaces.");
	      }
	      if (mock) doMock(response);
	      else
	      {
	         doJob(key, value, sync, response);
	      }
	   }
	   catch (IllegalArgumentException e)
	   {
	      
	      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	      response.setContentType("text/plain");
	      try (PrintWriter out = response.getWriter()) {
	         out.println("Bad request: ");
	         out.println(e.getMessage());
	      }
	   }
      
	}
	
	private void log(HttpServletRequest request)
	{
      List<Locale> locales = Collections.list(request.getLocales());
      Map<String, String[]> params = request.getParameterMap();
      String localesStr = locales.stream().map(Locale::toString).collect(Collectors.joining(", "));
      String paramsStr = params.entrySet().stream().map((entry) -> "(" + entry.getKey() + ", " + String.join(", ", entry.getValue()) +  ")")
            .collect(Collectors.joining(", "));
      LOG.info("[GET] " + request.getRemoteAddr() + " with " + request.getHeader("User-Agent") + " supporting " + localesStr + 
            " having parameters: " + paramsStr);
	}
	
	private void doMock(HttpServletResponse response) throws IOException
	{
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      response.setContentType("text/plain");
      try (PrintWriter out = response.getWriter()) {
         out.println("Confirmation");
      }
	}
	
	private void doJob(String key, int value, boolean sync, HttpServletResponse response) throws IOException
	{
	   StringBuilder sb = new StringBuilder();
	   sb.append(sdf.format(new Date())).append(" ");
	   for (int i = 0; i < value; i++) sb.append(key);
	   sb.append("\n");
	   if (sync)
	   {
	      synchronized(rep)
	      {
	         rep.initialize();
	         doJob(sb.toString(), response);
	      }
	   }
	   else
	   {
         rep.initialize();
         doJob(sb.toString(), response);
	   }
	}

   private void doJob(String text, HttpServletResponse response) throws IOException
   {
      rep.write(text);
      response.setStatus(HttpServletResponse.SC_ACCEPTED);
      response.setContentType("text/html");
      String content = rep.getContent();
      String[] lines = content.split("\n");
      Arrays.sort(lines, new KeyComparator());
      try (PrintWriter out = response.getWriter()) {
         for (String line : lines)
         {
            out.println(line);
         }
      }
   }
	
	class KeyComparator implements Comparator<String>
	{
	   private String getKey(String a)
	   {
        String[] chunks = a.split(" ");
        assert chunks.length == 3;
        return chunks[2];
	   }

      @Override
      public int compare(String a, String b)
      {
         return getKey(a).compareTo(getKey(b));
      }
	   
	}
}
