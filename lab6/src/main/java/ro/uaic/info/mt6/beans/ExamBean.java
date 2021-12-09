package ro.uaic.info.mt6.beans;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ro.uaic.info.mt6.model.Exam;
import ro.uaic.info.mt6.repositories.ExamRepository;

@Named(value = "exam")
@RequestScoped
public class ExamBean
implements Serializable
{
   /** The exam low-level model */
   private Exam model;
   
   /** The types of the exam */
   private int[] types = {0, 1, 2};

   /** An internal persistence helper*/
   @EJB
   private ExamRepository persistence;
   
   /**
    * Implicit constructor 
    */
   public ExamBean() 
   {
      
   }
   
   /**
    * Getter for the model.
    * 
    * @return   The exam model associated with this bean.
    */
   public Exam getModel()
   {
      if (model == null)
      {
         model = new Exam();
      }
      return model;
   }
   
   /**
    * Getter for the types.
    * 
    * @return   An array of the available exam types.
    */
   public int[] getTypes()
   {
      return types;
   }
   
   /**
    * Setter for the model.
    * 
    * @param   model
    *          The model which should be associated with this bean.
    */
   public void setModel(Exam model)
   {
      this.model = model;
   }
   
   /**
    * Save the current exam model to the database.
    * 
    * @return   The name of the page which should be opened after this model is persisted.
    */
   public String persist()
   {
      if (model != null)
      {
         persistence.saveExam(model);
      }
      
      return "/allexams.xhtml";
   }
   
   /**
    * Retrieve all exams from the database.
    * 
    * @return   The list of exams in the database.
    */
   public List<Exam> getAllModels()
   {
      return persistence.getAllExams();
   }
}
