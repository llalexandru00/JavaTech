package ro.uaic.info.jt;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A wrapper over a file which is able to write or to read the whole content.
 */
public class Repository
{   
   /** The file over which the file writer is used*/
   private File file;
   
   /**
    * Basic constructor.
    * 
    * @param   path
    *          The path to the file used as repository.
    */
   public Repository(String path) 
   {
      file = new File(path);
   }

   /**
    * Writes a text in the repository.
    * 
    * @param   text
    *          The text which should be appended to the repository.
    */
   public void write(String text) 
   throws IOException
   {
      try (FileWriter writer = new FileWriter(file, true))
      {
         writer.write(text);
         writer.flush();
      }
   }
   
   /**
    *   Retrieve the content in the repository.
    * 
    *   @return  The content in the repository in a single string.
    */
   public String getContent() 
   throws IOException
   {
      try (FileReader reader = new FileReader(file))
      {
         StringBuffer sb = new StringBuffer();
         char[] cb = new char[100];
         int cnt = 100;
         while (cnt == 100)
         {
            cnt = reader.read(cb);
            for (int i = 0; i < cnt; i++)
            {
               sb.append(cb[i]);
            }
         }
         return sb.toString();
      }
   }
   
}
