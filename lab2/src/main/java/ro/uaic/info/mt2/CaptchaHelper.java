package ro.uaic.info.mt2;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.awt.*;
import java.awt.image.*;

@WebServlet(name = "Captcha", urlPatterns = { "/captcha" })
public class CaptchaHelper 
extends HttpServlet 
{
   protected static Font font = new Font("Verdana", Font.ITALIC | Font.BOLD, 28);
   
   private Random rng = new Random();
   private final int width = 200;
   private final int height = 100;

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
         g.drawOval(num(width), num(height), 5 + num(10), 5 + num(10));
      }
      g.setFont(font);
      int h = height - ((height - font.getSize()) >> 1), w = width / len, size = w - font.getSize() + 1;
      for (int i = 0; i < len; i++)
      {
         ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
         g.setComposite(ac3);

         color = new Color(20 + num(110), 30 + num(110), 30 + num(110));
         g.setColor(color);
         g.drawString(strs[i] + "", (width - (len - i) * w) + size, h - 4);
      }
      response.setContentType("image/png");
      ImageIO.write(bi, "png", response.getOutputStream());
   }

   protected Color color(int fc, int bc)
   {
      if (fc > 255)
         fc = 255;
      if (bc > 255)
         bc = 255;
      int r = fc + num(bc - fc);
      int g = fc + num(bc - fc);
      int b = fc + num(bc - fc);
      return new Color(r, g, b);
   }

   public int num(int num)
   {
      return rng.nextInt(num);
   }
}
