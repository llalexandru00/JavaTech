package ro.uaic.info.mt2;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ro.uaic.info.mt2.beans.Category;
import ro.uaic.info.mt2.beans.Record;

public class Persistence
{
   public List<Category> getCategories()
   { 
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         return session.createQuery("SELECT c FROM Category c", Category.class).getResultList();
      }
   }

   public Category getCategory(int categoryId)
   {
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         return session.createQuery("SELECT c FROM Category c WHERE id = " + categoryId, Category.class).uniqueResult();
      }
   }

   public List<Record> getRecords()
   {
      try (Session session = HibernateUtil.getSessionFactory().openSession())
      {
         return session.createQuery("SELECT r FROM Record r", Record.class).getResultList();
      }
   }

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
