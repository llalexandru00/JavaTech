package ro.uaic.info.mt7.beans;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;

import ro.uaic.info.mt7.model.Paper;
import ro.uaic.info.mt7.repositories.PaperRepository;
import ro.uaic.info.mt7.util.PaperId;

@Named(value = "paper")
@SessionScoped
public class PaperBean
implements Serializable
{
   @Inject 
   @PaperId 
   int paperId;
   
   private Paper model;
   
   @Inject
   private SessBean sess;

   /** An internal persistence helper*/
   @EJB
   private PaperRepository persistence;


   public Paper getModel()
   {
      if (model == null)
      {
         model = new Paper();
      }
      return model;
   }

   public void setModel(Paper paper)
   {
      this.model = paper;
   }
   
   public void upload(FileUploadEvent event)
   {
      UploadedFile file = event.getFile();
      byte[] barray = file.getContent();
      getModel().setData(barray);
   }

   /**
    * Retrieve all exams from the database.
    * 
    * @return   The list of exams in the database.
    */
   public List<Paper> getAllModels()
   {
      return persistence.getAllPapers();
   }
   
   public String persist()
   {
      if (model != null)
      {
         model.setOrd(paperId);
         model.setAuthor(sess.getUser());
         persistence.savePaper(model);
      }
      return "index.xhtml";
   }
   
   public StreamedContent getFile(Paper paper)
   {
      return DefaultStreamedContent.builder()
            .name("paper.pdf")
            .contentType("application/pdf")
            .stream(() -> new ByteArrayInputStream(paper.getData()))
            .build();
   }
}
