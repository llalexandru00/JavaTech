package ro.uaic.info.mt6.reserv;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import ro.uaic.info.mt6.model.Exam;
import ro.uaic.info.mt6.model.Resource;

@Stateful
public class ReservationStatefulBean
{
   /** Responsible for reservation management */
   @EJB
   private ReservationSingletonBean reservSingleton;
   
   /**
    * Assign a list of resources to an exam.
    * 
    * @param   exam
    *          The exam to which the resources to be assigned.
    * @param   selectedResources
    *          A list of resources to be locked.
    */
   public void assign(Exam exam, List<Resource> selectedResources)
   {
      reservSingleton.addReservation(exam,  selectedResources);
   }
   
}
