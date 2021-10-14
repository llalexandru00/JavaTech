package ro.uaic.info.jt;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.logging.Logger;

public class Repository
{
   private FileWriter writer;
   private File file;
   
   public Repository() 
   {
   }
   
   public synchronized void initialize() 
   throws IOException
   {
      if (writer == null)
      {
         file  = new File("C:\\tmp\\repository.txt");
         file.createNewFile();
         writer = new FileWriter(file);
      }
   }

   public void write(String text) throws IOException
   {
      writer.write(text);
      writer.flush();
   }
   
   public String getContent() throws IOException
   {
      FileReader reader = new FileReader(file);
      StringBuffer sb = new StringBuffer();
      char[] cb = new char[100];
      int cnt = 100;
      while (cnt == 100)
      {
         cnt = reader.read(cb);
         for (int i = 0; i < cnt; i++)
            sb.append(cb[i]);
      }
      return sb.toString();
   }
   
}
