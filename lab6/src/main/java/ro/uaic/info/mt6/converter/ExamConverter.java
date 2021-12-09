package ro.uaic.info.mt6.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ro.uaic.info.mt6.model.Exam;
import ro.uaic.info.mt6.repositories.ExamRepository;

@FacesConverter(value="examconv")
public class ExamConverter
implements Converter
{

   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String name)
   {
      InitialContext jndi;
      try
      {
         jndi = new InitialContext();
         ExamRepository examRepo = (ExamRepository) jndi.lookup("java:module/ExamRepository");
         Exam e = examRepo.getExamByName(name);
         return e;
      } 
      catch (NamingException e)
      {
         e.printStackTrace();
         return null;
      }
   }

   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value)
   {
      if (value instanceof Exam)
      {
         return ((Exam) value).getName();
      }
      
      return "";
   }

}
