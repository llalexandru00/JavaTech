package ro.uaic.info.mt7.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import ro.uaic.info.mt7.model.Paper;

@Stateless
public class PaperRepository
{
   /** Entity manager */
   @PersistenceContext(unitName = "jtPU")
   private EntityManager em;
   
   public PaperRepository()
   {
      
   }
   
   /**
    * Retrieve all exams stored persistently.
    * 
    * @return   All exams.
    */
   public List<Paper> getAllPapers()
   {
      List<Paper> lst = new ArrayList<>();
      List<Object> source = em.createNamedQuery("Paper.findAll").getResultList();
      for (Object obj : source) lst.add((Paper) obj);
      return lst;
   }
   
   @Transactional
   public void savePaper(Paper paper)
   {
      em.persist(paper);
   }
}
