package ro.uaic.info.mt3;

import java.net.URL;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Convenient helper class used for Hibernate operations.
 */
public class HibernateUtil
{
   /** An application level session factory used each time a Hibernate session is needed */
   private static final SessionFactory sessionFactory = buildSessionFactory();
   
   /**
    * Used for initializing Hibernate session factory though a configuration file. 
    * 
    * @return   The session factory generated based on a configuration file.
    */
   private static SessionFactory buildSessionFactory() 
   {
       try 
       {
          URL url = HibernateUtil.class.getClassLoader().getResource("/hibernate.cfg.xml");
          return new Configuration().configure(url).buildSessionFactory();
 
       }
       catch (Throwable ex) 
       {
           System.err.println("Initial SessionFactory creation failed." + ex);
           throw new ExceptionInInitializerError(ex);
       }
   }
 
   /**
    * The session factory wrapped by this class.
    * 
    * @return   The Hibernate session factory used for persistence jobs.
    */
   public static SessionFactory getSessionFactory() 
   {
       return sessionFactory;
   }
 
   /**
    * Close the session factory.
    */
   public static void shutdown() 
   {
       getSessionFactory().close();
   }
}
