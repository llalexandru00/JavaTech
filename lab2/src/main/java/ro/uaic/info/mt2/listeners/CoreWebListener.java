package ro.uaic.info.mt2.listeners;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.*;

/**
 * Application Lifecycle Listener implementation class WebListener
 *
 */
@WebListener()
public class CoreWebListener 
implements ServletContextListener 
{
     /** Basic logger */
     private final Logger LOG = Logger.getLogger(CoreWebListener.class.getName());
   
     public void contextInitialized(ServletContextEvent sce)  
     { 
        String defaultCategory = sce.getServletContext().getInitParameter("default-category");
        sce.getServletContext().setAttribute("default-category", defaultCategory);
        LOG.info("Default category set to: " + defaultCategory);
     }

}
