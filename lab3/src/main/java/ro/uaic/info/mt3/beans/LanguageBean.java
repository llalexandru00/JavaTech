package ro.uaic.info.mt3.beans;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(name = "lang")
@SessionScoped
public class LanguageBean
{
   /** The current locale. */
   private String locale;
   
   /** A collection of supported locale */
   private Map<String, Locale> supportedLocales = new HashMap<>();
   
   /**
    * Basic constructor. Initialize the supported locale.
    */
   public LanguageBean() 
   {
      supportedLocales.put("English", Locale.ENGLISH);
      supportedLocales.put("Romanian", Locale.forLanguageTag("ro-RO"));
   }

   /**
    * Retrieve the current locale.
    * 
    * @return   The current locale.
    */
   public String getLocale() 
   {
      return locale;
   }

   /**
    * Set the current locale.
    * 
    * @param   locale
    *          The locale which should be set.
    */
   public void setLocale(String locale) 
   {
      this.locale = locale;
   }

   /**
    * Retrieve all supported locale.
    * 
    * @return  A collection of supported locale.
    */
   public Map<String, Locale> getSupportedLocales() 
   {
      return supportedLocales;
   }
   
   /**
    * Listener which is called when the locale should be changed.
    * 
    * @param   e
    *          The event which generated the locale change.
    */
   public void onLocaleChange(ValueChangeEvent e) 
   {
      String newLocaleValue = e.getNewValue().toString();
      System.out.print("Locale changed: " + newLocaleValue);
      
      for (Map.Entry<String, Locale> choice : supportedLocales.entrySet()) 
      {
         if(choice.getValue().toString().equals(newLocaleValue)) 
         {
            FacesContext.getCurrentInstance().getViewRoot().setLocale(choice.getValue());
         }
      }
   }
}
