package ro.uaic.info.mt2.beans;

import javax.persistence.*;

@Entity
@Table(name = "Record", uniqueConstraints = 
{
      @UniqueConstraint(columnNames = "id"),
      @UniqueConstraint(columnNames = "key"),
})
public class Record
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", unique = true, nullable = false)
   private int id;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "category", unique = false, nullable = false)
   private Category category;

   @Column(name = "key", unique = true, nullable = false, length = 50)
   private String key;

   @Column(name = "value", unique = true, nullable = false, length = 1000)
   private String value;
   
   public Record() { }
   
   public Record(Category category, String key, String value)
   {
      this.category = category;
      this.key = key;
      this.value = value;
   }
   
   public int getId()
   {
      return id;
   }

   public Category getCategory()
   {
      return category;
   }

   public void setCategory(Category category)
   {
      this.category = category;
   }

   public String getKey()
   {
      return key;
   }

   public void setKey(String key)
   {
      this.key = key;
   }

   public String getValue()
   {
      return value;
   }

   public void setValue(String value)
   {
      this.value = value;
   }
}
