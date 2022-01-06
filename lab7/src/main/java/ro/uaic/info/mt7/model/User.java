package ro.uaic.info.mt7.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Mtuser", uniqueConstraints = 
{
      @UniqueConstraint(columnNames = "uid"),
      @UniqueConstraint(columnNames = "username")
})
@NamedQueries({
   @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
   @NamedQuery(name = "User.findUser", query = "SELECT u FROM User u WHERE u.username like ?1")
})
public class User
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "uid", unique = true, nullable = false)
   private int uid;

   @Column(name = "username", unique = true, nullable = false, length = 50)
   private String username;

   @Column(name = "password", nullable = false, length = 50)
   private String password;

   @Column(name = "role", nullable = false, length = 50)
   private String role;
   
   
   public int getUid()
   {
      return uid;
   }

   public void setUid(int uid)
   {
      this.uid = uid;
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
   
}
