package ro.uaic.info.mt7.reserv;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import org.jboss.logging.Logger;

@Singleton
@Startup
public class TimerServiceBean
{
   /** Logger */
   private static final Logger LOG = Logger.getLogger(TimerServiceBean.class.getName());
   
   /** Timer service */
   @Resource
   TimerService timerService;

   /** Setup timer */
   @PostConstruct
   public void init() 
   {
      Date timeout = new Date(new Date().getTime() + 10000);
      timerService.createTimer(timeout, "Timer start!");
   }

   /** Timeout handler */
   @Timeout
   public void timeoutHandler(Timer timer)
   {
      LOG.info("Timer event: " + timer.getInfo());
   }

   /** Do simple logging */
   @Schedule(second = "*/10", minute = "*", hour = "*", persistent = false)
   public void doWork()
   {
      LOG.info("Doing work");
   }
}
