package ro.uaic.info.mt7.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import ro.uaic.info.mt7.model.User;

@Named(value = "sess")
@SessionScoped
public class SessBean
implements Serializable
{
   /** Stores the number of current active sessions. */
   private static int count = 0;
   
   private User user;

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

   public User getUser()
   {
      return user;
   }

   public void setUser(User user)
   {
      this.user = user;
   }
   
   public boolean isAdmin()
   {
      return this.user.getRole().equals("admin");
   }
   
   public boolean isAuthor()
   {
      return this.user.getRole().equals("author");
   }
   
   public boolean isReviewer()
   {
      return this.user.getRole().equals("reviewer");
   }
}
