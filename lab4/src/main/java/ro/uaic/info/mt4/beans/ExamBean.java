package ro.uaic.info.mt4.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import ro.uaic.info.mt4.Persistence;
import ro.uaic.info.mt4.model.Exam;

@ManagedBean(name = "exam")
@RequestScoped
public class ExamBean
{
   /** The exam low-level model */
   private Exam model;
   
   /** An internal persistence helper*/
   private Persistence persistence = new Persistence();
   
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
