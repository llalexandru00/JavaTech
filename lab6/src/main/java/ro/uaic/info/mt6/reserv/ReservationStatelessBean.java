package ro.uaic.info.mt6.reserv;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import ro.uaic.info.mt6.model.Resource;

@Stateless
public class ReservationStatelessBean
{
   /** The manager for reservations */
   @EJB
   private ReservationSingletonBean reservSingleton;
   
   /**
    * Check if a resource is available.
    * 
    * @param   resource
    *          The resource to be checked.
    *          
    * @return  {@code true} if the resource is locked, {@code false} otherwise.
    */
   public boolean isReserved(Resource resource)
   {
      return reservSingleton.isReserved(resource);
   }
}
