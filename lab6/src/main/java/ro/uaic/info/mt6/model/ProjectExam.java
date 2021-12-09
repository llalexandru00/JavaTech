package ro.uaic.info.mt6.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class ProjectExam
extends Exam
{

   @Column(name = "theme", unique = false, nullable = false)
   private String theme;
   
   public String getTheme()
   {
      return theme;
   }

   public void setTheme(String theme)
   {
      this.theme = theme;
   }
}
