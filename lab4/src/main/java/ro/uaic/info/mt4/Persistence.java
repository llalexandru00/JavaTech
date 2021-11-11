package ro.uaic.info.mt4;

import java.util.List;

import org.hibernate.Session;

import ro.uaic.info.mt4.model.Exam;
import ro.uaic.info.mt4.model.Student;

/**
 * The persistence layer which handles the jobs over Hibernate.
 */
public class Persistence
{

   /**
    * Retrieve all exams stored persistently.
    * 
    * @return   All exams.
    */
   public List<Exam> getAllExams()
   {
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         return session.createQuery("SELECT e FROM Exam e", Exam.class).getResultList();
      }
   }

   /**
    * Retrieve all students stored persistently
    * 
    * @return   All students;
    */
   public List<Student> getAllStudents()
   {
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         return session.createQuery("SELECT s FROM Student s", Student.class).getResultList();
      }
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
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         return session.createQuery("SELECT e FROM Exam e WHERE name like '" + name + "'", Exam.class).uniqueResult();
      }
      
   }

   /**
    * Persistently store a provided student in the database.
    * 
    * @param   student
    *          The student which is meant to be persisted.
    */
   public void saveStudent(Student student)
   {
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         session.beginTransaction();
         session.save(student);
         session.getTransaction().commit();
      }
   }
   
   /**
    * Persistently store a provided exam in the database.
    * 
    * @param   exam
    *          The exam which is meant to be persisted.
    */
   public void saveExam(Exam exam)
   {
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         session.beginTransaction();
         session.save(exam);
         session.getTransaction().commit();
      }
   }
}
