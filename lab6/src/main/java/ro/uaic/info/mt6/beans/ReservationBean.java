package ro.uaic.info.mt6.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import ro.uaic.info.mt6.model.Exam;
import ro.uaic.info.mt6.model.Resource;
import ro.uaic.info.mt6.repositories.ResourceRepository;
import ro.uaic.info.mt6.reserv.ReservationStatefulBean;
import ro.uaic.info.mt6.reserv.ReservationStatelessBean;

@Named("reserv")
@RequestScoped
public class ReservationBean
{
   /** The persistence layer responsible for resource management */
   @EJB
   private ResourceRepository persistence;
   
   /** The current reservation bean responsible for storing */
   @EJB
   private ReservationStatefulBean reservation;
   
   /** A reservation availability checker */
   @EJB
   private ReservationStatelessBean availableChecker;
   
   /** The currently selected resources */
   private List<Resource> selectedResource;
   
   /** The currently selected exam */
   private Exam exam;
   
   /** 
    * Retrieve the list of all selected resources
    * 
    * @return   A list of selected resources
    */
   public List<Resource> getSelectedResources() 
   {
      if (selectedResource == null)
      {
         selectedResource = new ArrayList<>();
      }
      return selectedResource;
   }
   
   /**
    * Set the selected resources.
    * 
    * @param   A resource list to represent the currently selected resources.
    */
   public void setSelectedResources(List<Resource> selectedResource) 
   {
      this.selectedResource = selectedResource;
   }
   
   /** 
    * Retrieve all available resources.
    * 
    * @return  A list of available resources.
    */
   public List<Resource> getAllResources() 
   {
      List<Resource> resources = persistence.getAllResources();
      List<Resource> available = new ArrayList<>();
      for (Resource res : resources)
      {
         if (!availableChecker.isReserved(res))
         {
            available.add(res);
         }
      }
      return available;
   }
   
   /**
    * Retrieve the currently selected exam.
    * 
    * @return   The currently selected exam.
    */
   public Exam getExam()
   {
      if (exam == null)
      {
         exam = new Exam();
      }
      return exam;
   }
   
   /**
    * Set the current exam.
    * 
    * @param An exam to be selected.
    */
   public void setExam(Exam exam)
   {
      this.exam = exam;
   }
   
   /**
    * Do the reservation by assigning the selected resources to the exam.
    * 
    * @return   The next page to be visited.
    */
   public String persist()
   {
      reservation.assign(exam, selectedResource);
      return "/index.xhtml";
   }
}
