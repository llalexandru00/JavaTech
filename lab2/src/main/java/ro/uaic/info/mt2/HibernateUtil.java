package ro.uaic.info.mt2;

import java.io.File;
import java.net.URL;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil
{
   private static final SessionFactory sessionFactory = buildSessionFactory();
   
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
 
   public static SessionFactory getSessionFactory() 
   {
       return sessionFactory;
   }
 
   public static void shutdown() 
   {
       getSessionFactory().close();
   }
}
