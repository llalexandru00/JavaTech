package ro.uaic.info.mt2.beans;

import javax.persistence.*;

@Entity
@Table(name = "Category", uniqueConstraints = 
{
      @UniqueConstraint(columnNames = "id"),
      @UniqueConstraint(columnNames = "name")
})
public class Category
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", unique = true, nullable = false)
   private int id;
   
   @Column(name = "name", unique = true, nullable = false, length = 50)
   private String name;
   
   public Category() { }
   
   public Category(String name)
   {
      super();
      this.name = name;
   }
   
   public int getId()
   {
      return id;
   }
   
   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

}
