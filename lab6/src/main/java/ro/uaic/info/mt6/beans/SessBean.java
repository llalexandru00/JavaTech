package ro.uaic.info.mt6.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "sess")
@SessionScoped
public class SessBean
{
   /** Stores the number of current active sessions. */
   private static int count = 0;
   
   /** 
    * Increase the number of active sessions. 
    */
   @PostConstruct
   public void increase()
   {
      count++;
   }

   /** 
    * Decrease the number of active sessions. 
    */
   @PreDestroy
   public void decrease()
   {
      count--;
   }
   
   /**
    * Keep alive the current session.
    */
   public void keepAlive()
   {
      // no-op
   }
   
   /**
    * Retrieve the number of active sessions
    * 
    * @return   Retrieve the number of current active sessions.
    */
   public int getCount()
   {
      return count;
   }
}
