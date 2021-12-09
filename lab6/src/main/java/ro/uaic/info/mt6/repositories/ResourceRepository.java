package ro.uaic.info.mt6.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ro.uaic.info.mt6.model.Resource;

@Stateless
public class ResourceRepository
{
   /** Entity manager */
   @PersistenceContext(unitName = "jtPU")
   private EntityManager em;
   
   /**
    * Retrieve all exams stored persistently.
    * 
    * @return   All exams.
    */
   public List<Resource> getAllResources()
   {
      List<Resource> lst = new ArrayList<>();
      List<Object> source = em.createNamedQuery("Resource.findAll").getResultList();
      for (Object obj : source) lst.add((Resource) obj);
      return lst;
   }

   /**
    * Retrieve a resource by its name.
    * 
    * @param   name
    *          The name of a resource to be retrieved
    *          
    * @return  The resource with the provided name
    */
   public Resource getResourceByName(String name)
   {
      return (Resource) em.createNamedQuery("Resource.findByName").setParameter(1, name).getSingleResult();      
   }
}
