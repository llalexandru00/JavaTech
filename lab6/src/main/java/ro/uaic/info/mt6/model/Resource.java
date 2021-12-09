package ro.uaic.info.mt6.model;

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
@Table(name = "Resource", uniqueConstraints = 
{
      @UniqueConstraint(columnNames = "rid"),
      @UniqueConstraint(columnNames = "name")
})
@NamedQueries({
   @NamedQuery(name = "Resource.findAll", query = "SELECT r FROM Resource r"),
   @NamedQuery(name = "Resource.findByName", query = "SELECT r FROM Resource r WHERE r.name like ?1"),
})
public class Resource
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "rid", unique = true, nullable = false)
   private int rid;

   @Column(name = "name", unique = true, nullable = false, length = 50)
   private String name;
   
   public Resource()
   {
      
   }

   public int getRid()
   {
      return this.rid;
   }

   public void setRid(int rid)
   {
      this.rid = rid;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(String name)
   {
      this.name = name;
   }
   
   @Override
   public boolean equals(Object o)
   {
      if (!(o instanceof Resource)) return false;
      return this.rid == ((Resource) o).rid;
   }
   
   @Override
   public int hashCode()
   {
      return this.name.hashCode();
   }
   
}
