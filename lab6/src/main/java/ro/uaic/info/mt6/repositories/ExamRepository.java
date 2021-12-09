package ro.uaic.info.mt6.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ro.uaic.info.mt6.model.Exam;

@Stateless
public class ExamRepository
{
   /** Entity manager */
   @PersistenceContext(unitName = "jtPU")
   private EntityManager em;
   
   public ExamRepository()
   {
      
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
    * Persistently store a provided exam in the database.
    * 
    * @param   exam
    *          The exam which is meant to be persisted.
    */
   @Transactional
   public void saveExam(Exam exam)
   {
      em.persist(exam);
   }

}
