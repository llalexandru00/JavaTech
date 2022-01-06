package ro.uaic.info.mt7.repositories;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ro.uaic.info.mt7.model.User;

@Stateless
public class UserRepository
{
   /** Entity manager */
   @PersistenceContext(unitName = "jtPU")
   private EntityManager em;
   
   public UserRepository()
   {
      
   }

   /**
    * Retrieve a single exam based on its unique name;
    * 
    * @param   name
    *          The name of the exam which should be found.
    *          
    * @return  The exam with the provided name.
    */
   public User getUser(String username)
   {
      try
      {
         return (User) em.createNamedQuery("User.findUser").setParameter(1, username).getSingleResult();   
      }   
      catch (NoResultException e)
      {
         return null;
      }
   }
   
   @Transactional
   public void saveUser(User user)
   {
      em.persist(user);
   }

}