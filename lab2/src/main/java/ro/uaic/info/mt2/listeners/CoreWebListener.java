package ro.uaic.info.mt2.listeners;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.*;

/**
 * A basic web listener which sets the default category application level attribute at sever startup.
 */
@WebListener()
public class CoreWebListener 
implements ServletContextListener 
{
     /** Basic logger */
     private final Logger LOG = Logger.getLogger(CoreWebListener.class.getName());
   
     /**
      * Listener for the server start-up which sets the default-category based on a init parameter.
      */
     public void contextInitialized(ServletContextEvent sce)  
     { 
        String defaultCategory = sce.getServletContext().getInitParameter("default-category");
        sce.getServletContext().setAttribute("default-category", defaultCategory);
        LOG.info("Default category set to: " + defaultCategory);
     }

}
