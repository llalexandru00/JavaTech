package ro.uaic.info.mt6.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ro.uaic.info.mt6.model.Resource;
import ro.uaic.info.mt6.repositories.ResourceRepository;

@FacesConverter(value = "resconv")
public class ResConverter
implements Converter
{
   /** 
    * Identify a resource based on its name 
    */
   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String name)
   {
      InitialContext jndi;
      try
      {
         jndi = new InitialContext();
         ResourceRepository examRepo = (ResourceRepository) jndi.lookup("java:module/ResourceRepository");
         Resource r = examRepo.getResourceByName(name);
         return r;
      } 
      catch (NamingException e)
      {
         e.printStackTrace();
         return null;
      }
   }

   /** 
    * Retrieve a string based on a resource value 
    */
   @Override
   public String getAsString(FacesContext context, UIComponent component, Object value)
   {
      if (value instanceof Resource)
      {
         return ((Resource) value).getName();
      }
      
      return "";
   }

}
