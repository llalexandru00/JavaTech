package ro.uaic.info.mt6.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.jboss.logging.Logger;

public class ProfilingInterceptor
{
    /** Logger */
   private static final Logger LOG = Logger.getLogger(ProfilingInterceptor.class.getName());
   
   /** Do interceptor profiling */
   @AroundInvoke
   public Object logCall(InvocationContext context) 
   throws Exception
   {
       long start = System.nanoTime();
       try
       {
          return context.proceed();
       }
       finally
       {
          long finish = System.nanoTime();
          LOG.info("Time to execute " + context.getMethod().getName() + ": " + ((finish - start) / 1e6) + " miliseconds.");
       }
       
   }
}
