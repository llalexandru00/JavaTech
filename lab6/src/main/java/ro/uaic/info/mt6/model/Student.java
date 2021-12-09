package ro.uaic.info.mt6.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "Student", uniqueConstraints = 
{
      @UniqueConstraint(columnNames = "sid"),
      @UniqueConstraint(columnNames = "name")
})
@NamedQueries({
   @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")
})
public class Student
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "sid", unique = true, nullable = false)
   private int sid;

   @Column(name = "name", unique = true, nullable = false, length = 50)
   private String name;
   
   @ManyToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.EAGER)
   @JoinTable(
       name = "Student_Exam", 
       joinColumns = { @JoinColumn(name = "sid") }, 
       inverseJoinColumns = { @JoinColumn(name = "eid") }
   )
   private Set<Exam> exams = new HashSet<>();

   public int getSid()
   {
      return sid;
   }

   public void setSid(int sid)
   {
      this.sid = sid;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Set<Exam> getExams()
   {
      return exams;
   }

   public void setExams(Set<Exam> exams)
   {
      this.exams = exams;
   }
   
   @Override
   public boolean equals(Object o)
   {
      if (!(o instanceof Student)) return false;
      return this.sid == ((Student) o).sid;
   }
   
}
