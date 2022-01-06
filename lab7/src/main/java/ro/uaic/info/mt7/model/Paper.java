package ro.uaic.info.mt7.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Paper", uniqueConstraints = 
{
      @UniqueConstraint(columnNames = "pid"),
      @UniqueConstraint(columnNames = "name")
})
@NamedQueries({
   @NamedQuery(name = "Paper.findAll", query = "SELECT p FROM Paper p"),
})
public class Paper
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "pid", unique = true, nullable = false)
   private int pid;
   
   @Column(name = "ord", unique = true, nullable = false)
   private int ord;

   @Column(name = "name", unique = true, nullable = false, length = 50)
   private String name;
   
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "author")
   private User author;

   @Column(name = "data")
   private byte[] data;

   public int getPid()
   {
      return pid;
   }

   public void setPid(int pid)
   {
      this.pid = pid;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public byte[] getData()
   {
      return data;
   }

   public void setData(byte[] data)
   {
      this.data = data;
   }

   public User getAuthor()
   {
      return author;
   }

   public void setAuthor(User author)
   {
      this.author = author;
   }

   public int getOrd()
   {
      return ord;
   }

   public void setOrd(int ord)
   {
      this.ord = ord;
   }

}
