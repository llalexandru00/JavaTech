package ro.uaic.info.mt7.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ro.uaic.info.mt7.model.User;
import ro.uaic.info.mt7.repositories.UserRepository;

@Named(value = "auth")
@RequestScoped
public class AuthBean
{
   @Inject
   private SessBean sess;

   /** An internal persistence helper*/
   @EJB
   private UserRepository persistence;
   
   private String username;
   
   private String password;
   
   private String role;
   
   private List<String> roles;
   
   public AuthBean()
   {
      roles = new ArrayList<>();
      roles.add("admin");
      roles.add("author");
      roles.add("reviewer");
   }
   
   public String getUsername()
   {
      return username;
   }
   
   public void setUsername(String username)
   {
      this.username = username;
   }
   
   public String getPassword()
   {
      return password;
   }
   
   public void setPassword(String password)
   {
      this.password = password;
   }
   
   public String getRole()
   {
      return role;
   }

   public void setRole(String role)
   {
      this.role = role;
   }

   public List<String> getRoles()
   {
      return roles;
   }

   public void setRoles(List<String> roles)
   {
      this.roles = roles;
   }

   public SessBean getSess()
   {
      return sess;
   }

   public void setSess(SessBean sess)
   {
      this.sess = sess;
   }

   public String login()
   {
      User user = persistence.getUser(username);
      if (user == null)
      {
         throw new RuntimeException("Invalid password");
      }
      
      if (user.getPassword().equals(password))
      {
         sess.setUser(user);
         return "/index.xhtml";
      }
      
      return "";
   }

   public String register()
   {
      User user = new User();
      user.setUsername(username);
      user.setPassword(password);
      user.setRole(role);
      
      persistence.saveUser(user);
      
      return "/index.xhtml";
   }
}
