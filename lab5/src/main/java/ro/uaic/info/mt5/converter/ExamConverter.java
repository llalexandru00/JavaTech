package ro.uaic.info.mt5.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ro.uaic.info.mt5.PersistenceLayer;
import ro.uaic.info.mt5.model.Exam;

@FacesConverter(value = "examconv", forClass = Exam.class)
public class ExamConverter
implements Converter
{

   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String name)
   {
      return new PersistenceLayer().getExamByName(name);
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
