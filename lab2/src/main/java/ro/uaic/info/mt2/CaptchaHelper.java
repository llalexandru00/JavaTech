package ro.uaic.info.mt2;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.awt.*;
import java.awt.image.*;

/**
 * Servlet responsible for generating captcha images based on a session level string.
 */
@WebServlet(name = "Captcha", urlPatterns = { "/captcha" })
public class CaptchaHelper 
extends HttpServlet 
{
   /** The font to be used when generating the captcha */
   protected static Font font = new Font("Verdana", Font.ITALIC | Font.BOLD, 28);
   
   /** The width of the captcha image */
   private static final int width = 200;
   
   /** The height of the captcha image*/
   private static final int height = 100;
   
   /** A random provider */
   private Random rng = new Random();

   /**
    * Get method used to generate a captcha image and sent it back as response. This will
    * write the text in which each letter has a different color. Also, some ovals will 
    * be drawn over.
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) 
   throws ServletException, IOException 
   {
      String randomStr = request.getSession().getAttribute("target").toString();
      char[] strs = randomStr.toCharArray();
      BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = (Graphics2D) bi.getGraphics();
      AlphaComposite ac3;
      Color color;
      int len = strs.length;
      g.setColor(Color.WHITE);
      g.fillRect(0, 0, width, height);
      for (int i = 0; i < 15; i++)
      {
         color = color(150, 250);
         g.setColor(color);
         g.drawOval(rngint(width), rngint(height), 5 + rngint(10), 5 + rngint(10));
      }
      g.setFont(font);
      int h = height - ((height - font.getSize()) >> 1), w = width / len, size = w - font.getSize() + 1;
      for (int i = 0; i < len; i++)
      {
         ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
         g.setComposite(ac3);

         color = new Color(20 + rngint(110), 30 + rngint(110), 30 + rngint(110));
         g.setColor(color);
         g.drawString(strs[i] + "", (width - (len - i) * w) + size, h - 4);
      }
      response.setContentType("image/png");
      ImageIO.write(bi, "png", response.getOutputStream());
   }

   /**
    * Generate a random color.
    * 
    * @param   fc
    *          The lower bound for each of the red, greed and blue base colors.
    * @param   bc
    *          The upper bound for each of the red, greed and blue base colors.
    *          
    * @return  A random generated color.
    */ 
   protected Color color(int fc, int bc)
   {
      if (fc > 255)
         fc = 255;
      if (bc > 255)
         bc = 255;
      int r = fc + rngint(bc - fc);
      int g = fc + rngint(bc - fc);
      int b = fc + rngint(bc - fc);
      return new Color(r, g, b);
   }

   /**
    * Convenient method for retrieving a random bounded number.
    * 
    * @param   num
    *          The upper bound of the generated integer.
    *          
    * @return  A random integer.
    */
   public int rngint(int num)
   {
      return rng.nextInt(num);
   }
}
