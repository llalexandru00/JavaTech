package ro.uaic.info.mt3.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import ro.uaic.info.mt3.Persistence;
import ro.uaic.info.mt3.model.Exam;

@FacesConverter(value = "examconv", forClass = Exam.class)
public class ExamConverter
implements Converter
{

   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String name)
   {
      return new Persistence().getExamByName(name);
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
