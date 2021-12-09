package ro.uaic.info.mt6.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("1")
public class WrittenExam
extends Exam
{

   @Column(name = "questionCount", unique = false, nullable = false)
   private int questionCount;
   
   public int getQuestionCount()
   {
      return questionCount;
   }

   public void setQuestionCount(int questionCount)
   {
      this.questionCount = questionCount;
   }
   
   
}