package ro.uaic.info.mt5.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.*;

import ro.uaic.info.mt5.model.Exam;
import ro.uaic.info.mt5.model.Student;
import ro.uaic.info.mt5.util.ScheduleResolver;
import ro.uaic.info.mt5.util.Z3ScheduleResolver;

@ManagedBean(name = "schedule")
@SessionScoped
public class ScheduleBean
{
   /**
    * Implicit constructor.
    */
   public ScheduleBean()
   {
   }
   
   /**
    * Retrieve the model for the calendar widget. This also solver the scheduling problem.
    * 
    * @param   studs
    *          The list of students which can generate exam conflicts.
    * @param   allExams
    *          The list of exams which should be scheduled.
    *          
    * @return  A calendar widget model containing all exams in a minimum number of days.
    */
   public ScheduleModel buildEventModel(List<Student> studs, List<Exam> allExams)
   {
      ScheduleModel eventModel = new DefaultScheduleModel();
      
      ScheduleResolver resolver = new Z3ScheduleResolver(allExams);
      
      for (Student stud : studs)
      {
         List<Exam> exams = new ArrayList<>(stud.getExams());
         for (int i = 0; i < exams.size(); i++)
         {
            for (int j = i + 1; j < exams.size(); j++)
            {
               resolver.addConflict(exams.get(i), exams.get(j));
            }
         }
      }
      
      List<Exam>[] model = resolver.getModel();
      
      for (int i = 0; i < model.length; i++)
      {
         for (Exam e : model[i])
         {
            int mins = e.getDuration();
            Date starting = e.getDate();
            int h = starting.getHours();
            int m = starting.getMinutes();
            int m2 = (m + mins) % 60;
            int h2 = h + (m + mins) / 60;
            DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                  .title(e.getName())
                  .startDate(LocalDateTime.now().plusDays(1 + i).withHour(h).withMinute(m))
                  .endDate(LocalDateTime.now().plusDays(1 + i).withHour(h2).withMinute(m2))
                  .build();
            eventModel.addEvent(event);
         }
      }
      
      return eventModel;
   }
}
