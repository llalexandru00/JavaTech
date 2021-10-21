package ro.uaic.info.mt2;

import java.util.List;

import org.hibernate.Session;

import ro.uaic.info.mt2.beans.Category;
import ro.uaic.info.mt2.beans.Record;

/**
 * The persistence layer which handles the jobs over Hibernate.
 */
public class Persistence
{
   /**
    * Retrieves all categories stored in the database.
    * 
    * @return  All categories stored in the database.
    */
   public List<Category> getCategories()
   { 
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         return session.createQuery("SELECT c FROM Category c", Category.class).getResultList();
      }
   }

   /**
    * Retrieves a single category based on its category id.
    * 
    * @param   categoryId
    *          The category id used for look-up.
    *          
    * @return  The category which matches the provided category id.
    */
   public Category getCategory(int categoryId)
   {
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         return session.createQuery("SELECT c FROM Category c WHERE id = " + categoryId, Category.class).uniqueResult();
      }
   }

   /**
    * Retrieves all records stored in the database.
    * 
    * @return  All records stored in the database.
    */
   public List<Record> getRecords()
   {
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         return session.createQuery("SELECT r FROM Record r", Record.class).getResultList();
      }
   }

   /**
    * Persistently store a provided record in the database.
    * 
    * @param   record
    *          The record which is meant to be persisted.
    */
   public void saveRecord(Record record)
   {
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         session.beginTransaction();
         session.save(record);
         session.getTransaction().commit();
      }
   }
}
