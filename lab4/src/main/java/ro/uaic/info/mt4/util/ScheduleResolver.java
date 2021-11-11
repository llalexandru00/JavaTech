package ro.uaic.info.mt4.util;

import java.util.List;

import ro.uaic.info.mt4.model.Exam;

/**
 * A base interface for any schedule problem resolver.
 */
public interface ScheduleResolver
{
   /**
    * Describe that there is a conflict between two exams and they shouldn't be grouped in the same day.
    * 
    * @param   exam
    *          The first exam part of the conflict.
    * @param   exam2
    *          The second exam part of the conflict.
    */
   void addConflict(Exam exam, Exam exam2);
   
   /**
    * Retrieve the solution for the schedule problem based on the already defined conflicts.
    * 
    * @return  A list (representing the days) containing lists of exams.
    */
   List<Exam>[] getModel();
}
