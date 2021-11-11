package ro.uaic.info.mt4.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microsoft.z3.*;

import ro.uaic.info.mt4.model.Exam;

/**
 * An implementation for schedule resolver using the Z3 SMT-solver.
 */
public class Z3ScheduleResolver
implements ScheduleResolver
{
   /** The list of exams which should be scheduled */
   private List<Exam> exams;
   
   /** The Z3 context over which the conflict are defined*/
   private Context ctx;
   
   /** The Z3 solver which can provide a solution to the problem */
   private Solver s;
   
   /** A mapping between exams and Z3 base expressions */
   private Map<Exam, Expr<IntSort>> baseExprs;
   
   /**
    * Basic constructor. This will also build some basic conditions over the exams.
    * @param exams
    */
   public Z3ScheduleResolver(List<Exam> exams)
   {
      this.exams = exams;
      this.ctx = new Context();
      this.s = ctx.mkSolver();
      this.baseExprs = new HashMap<>();
      for (Exam e : exams)
      {
         Expr<IntSort> expr = ctx.mkIntConst("e" + e.getEid());
         baseExprs.put(e, expr);
         s.add(ctx.mkLe(ctx.mkInt(0), expr));
      }
   }

   /**
    * Add a new conflict to the Z3 context. The exams provided shouldn't have equal base values in the SMT formula.
    */
   @Override
   public void addConflict(Exam exam, Exam exam2)
   {
      Expr<IntSort> baseExpr  = baseExprs.get(exam);
      Expr<IntSort> baseExpr2 = baseExprs.get(exam2);
      s.add(ctx.mkNot(ctx.mkEq(baseExpr, baseExpr2)));
   }

   /** 
    * Retrieve the solution for the scheduling problem. This will binary search the optimal value. The smallest bound
    * for which the formula is satisfiable is in fact the answer for this problem.
    */
   @Override
   public List<Exam>[] getModel()
   {
      int l = 1, r = exams.size();
      while (l <= r)
      {
         int mijl = (l + r) >> 1;
         if (check(mijl))
            r = mijl - 1;
         else
            l = mijl + 1;
      }
      if (r >= 1 && check(r))
      {
         return getModel(r);
      }
      return getModel(l);
   }
   
   /**
    * Retrieve the model if the bound is already set. This returns {@code null} if the formula
    * is not satisfiable. Otherwise, a list of days is returned, each having a list of exams scheduled in that day. 
    * 
    * @param   bound
    *          The number of days in which the exams should be scheduled.
    * 
    * @return  A list of days each containing a list of exams or {@code null} if the Z3 formula in not satisfiable.
    */
   private List<Exam>[] getModel(int bound)
   { 
      List<Exam>[] ans = new List[bound];
      s.push();
      try
      {
         for (Exam e : exams)
         {
            s.add(ctx.mkLt(baseExprs.get(e), ctx.mkInt(bound)));
         }
         if (s.check() != Status.SATISFIABLE)
         {
            return null;
         }
         Model model = s.getModel();
         for (Exam e : exams)
         {
            int vInt = Integer.parseInt(model.evaluate(baseExprs.get(e), false).toString());
            if (ans[vInt] == null) ans[vInt] = new ArrayList<>();
            ans[vInt].add(e);
         }
         return ans;
      }
      finally
      {
         s.pop();
      }
      
   }

   /**
    * Verify if the provided number of days is good enough to schedule the exams.
    * 
    * @param   bound
    *          The number of days.
    *          
    * @return  {@code true} if the exams can be scheduled in the provided number of days.
    */
   private boolean check(int bound)
   {
      s.push();
      try
      {
         for (Exam e : exams)
         {
            s.add(ctx.mkLt(baseExprs.get(e), ctx.mkInt(bound)));
         }
         Status status = s.check();
         return status == Status.SATISFIABLE;
      }
      finally
      {
         s.pop();
      }
   }
   
}
