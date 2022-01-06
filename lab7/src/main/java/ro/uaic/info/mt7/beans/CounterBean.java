package ro.uaic.info.mt7.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import ro.uaic.info.mt7.util.PaperId;

@ApplicationScoped
public class CounterBean
{
   public static int id = 100;
   
   @Produces @PaperId int getPaperId() 
   {
      return id++;
   }
}
