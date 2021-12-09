package ro.uaic.info.mt6.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "Exam", uniqueConstraints = 
{
      @UniqueConstraint(columnNames = "eid"),
      @UniqueConstraint(columnNames = "name")
})
@NamedQueries({
   @NamedQuery(name = "Exam.findAll", query = "SELECT e FROM Exam e"),
   @NamedQuery(name = "Exam.findByName", query = "SELECT e FROM Exam e WHERE e.name like ?1"),
})
@DiscriminatorColumn(name="dtype", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("null")
public class Exam
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "eid", unique = true, nullable = false)
   private int eid;

   @Column(name = "name", unique = true, nullable = false, length = 50)
   private String name;

   @Column(name = "date", unique = false, nullable = false)
   private Date date;

   @Column(name = "duration", unique = false, nullable = false)
   private int duration;
   
   @ManyToMany(mappedBy = "exams")
   private Set<Student> students = new HashSet<>();
   
   public int getEid()
   {
      return eid;
   }

   public void setEid(int eid)
   {
      this.eid = eid;
   }
   
   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Date getDate()
   {
      return date;
   }

   public void setDate(Date date)
   {
      this.date = date;
   }

   public int getDuration()
   {
      return duration;
   }

   public void setDuration(int duration)
   {
      this.duration = duration;
   }

   public Set<Student> getStudents()
   {
      return students;
   }

   public void setDuration(Set<Student> students)
   {
      this.students = students;
   }
   
   @Override
   public boolean equals(Object o)
   {
      if (!(o instanceof Exam)) return false;
      return this.eid == ((Exam) o).eid;
   }
   
   @Override
   public int hashCode()
   {
      return this.name.hashCode();
   }
}
