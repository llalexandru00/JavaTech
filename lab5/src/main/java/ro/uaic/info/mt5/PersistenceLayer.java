package ro.uaic.info.mt5;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

import ro.uaic.info.mt5.model.Exam;
import ro.uaic.info.mt5.model.Student;

/**
 * The persistence layer which handles the jobs over JPA.
 */
@Stateless
public class PersistenceLayer
{
   /** The global manager which generates entity managers. */
   private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("main-unit");
   
   /** The local entity manager used by this persistence layer. */
   @PersistenceContext(unitName="main-unit")
   private EntityManager em;
   
   /**
    * Basic constructor
    */
   public PersistenceLayer()
   {
      this.em = emf.createEntityManager();
   }

   /**
    * Retrieve all exams stored persistently.
    * 
    * @return   All exams.
    */
   public List<Exam> getAllExams()
   {
      List<Exam> lst = new ArrayList<>();
      List<Object> source = em.createNamedQuery("Exam.findAll").getResultList();
      for (Object obj : source) lst.add((Exam) obj);
      return lst;
   }

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
    * Retrieve a single exam based on its unique name;
    * 
    * @param   name
    *          The name of the exam which should be found.
    *          
    * @return  The exam with the provided name.
    */
   public Exam getExamByName(String name)
   {
      return (Exam) em.createNamedQuery("Exam.findByName").setParameter(1, name).getSingleResult();      
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
   
   /**
    * Persistently store a provided exam in the database.
    * 
    * @param   exam
    *          The exam which is meant to be persisted.
    */
   public void saveExam(Exam exam)
   {
      em.persist(exam);
   }
}
