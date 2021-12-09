package ro.uaic.info.mt6.reserv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.interceptor.Interceptors;

import ro.uaic.info.mt6.interceptor.ProfilingInterceptor;
import ro.uaic.info.mt6.model.Exam;
import ro.uaic.info.mt6.model.Resource;

@Interceptors(ProfilingInterceptor.class)
@Singleton
@Startup
public class ReservationSingletonBean
{
   /** In-memory way to store reservations */
   private Map<Resource, Exam> reservation;
   
   /** Initialize reservation */
   @PostConstruct
   public void init() 
   {
      reservation = new HashMap<>();
   }
   
   /** 
    * Check if a resource is locked. 
    * 
    * @param   resource
    *          The resource to be queried.
    *          
    * @return  {@code true} if the resource is locked, {@code false} otherwise.
    */
   @Lock(LockType.READ)
   public boolean isReserved(Resource resource)
   {
      return reservation.containsKey(resource);
   }
   
   /**
    * Assign a list of resources to an exam by locking them.
    * 
    * @param   exam
    *          The exam to which the resources should be assigned.
    * @param   resources
    *          The resources which should be locked.
    *          
    * @return  {@code true} if the reservations succeeded, {@code false} if
    *          a resource was already locked.
    */
   @Lock(LockType.WRITE)
   public boolean addReservation(Exam exam, List<Resource> resources)
   {
      for (Resource res : resources)
      {
         if (isReserved(res)) 
         {
            return false;
         }
      }
      for (Resource res : resources)
      {
         reservation.put(res, exam);
      }
      return true;
   }
   
}
