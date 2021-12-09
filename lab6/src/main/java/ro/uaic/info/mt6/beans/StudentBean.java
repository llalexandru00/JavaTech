package ro.uaic.info.mt6.beans;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ro.uaic.info.mt6.model.Student;
import ro.uaic.info.mt6.repositories.StudentRepository;

@Named("stud")
@RequestScoped
public class StudentBean
{
   /** The student low-level model */
   private Student model;
   
   /** A persistence helper */
   @EJB
   private StudentRepository persistence;
   
   /**
    * Basic constructor.
    */
   public StudentBean() 
   {
   }
   
   /**
    * Retrieve the current student model
    *  
    * @return   The student model associated with the bean.
    */
   public Student getModel()
   {
      if (model == null)
      {
         model = new Student();
      }
      return model;
   }
   
   /**
    * Set the current student model.
    * 
    * @param   model
    *          The model which should be associated with the bean.
    */
   public void setModel(Student model)
   {
      this.model = model;
   }
   
   /**
    * Retrieve all students from the database.
    * 
    * @return   A collection of students which are stored in the database.
    */
   public List<Student> getAllModels()
   {
      return persistence.getAllStudents();
   }
   
   /**
    * Helper method which can provide a string containing all exams registered to a specific student.
    * 
    * @param   student
    *          The student for which the exams should be formatted.
    *        
    * @return  A string representing a list of exams associated with the provided student.
    */
   public String formatExams(Student student)
   {
      return student.getExams().stream().map((exam) -> exam.getName()).collect(Collectors.joining(", "));
   }
   
   /**
    * Store the current student model into the database.
    * 
    * @return   A path to navigate after persisting the student model.
    */
   public String persist()
   {
      if (model != null)
      {
         persistence.saveStudent(model); 
      }
      
      return "/allstuds.xhtml";
   }
}
