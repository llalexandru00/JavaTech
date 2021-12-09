package ro.uaic.info.mt6.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.uaic.info.mt6.model.Student;

@Stateless
public class StudentRepository
{
   @PersistenceContext(unitName="jtPU")
   private EntityManager em;

   /**
    * Retrieve all students stored persistently
    * 
    * @return   All students;
    */
   public List<Student> getAllStudents()
   {
      List<Student> lst = new ArrayList<>();
      List<Object> source = em.createNamedQuery("Student.findAll").getResultList();
      for (Object obj : source) lst.add((Student) obj);
      return lst;
   }

   /**
    * Persistently store a provided student in the database.
    * 
    * @param   student
    *          The student which is meant to be persisted.
    */
   public void saveStudent(Student student)
   {
      em.persist(student);
   }
}
