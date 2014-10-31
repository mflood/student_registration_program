/**
     UserInterface.java

     Abstract class for providing access to the system
*/
package com.eteam.studentregistration;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public abstract class UserInterface
{
     // For reading from stdin
     Scanner scanner = new Scanner(System.in);

     // Interface interacts with these 3 components
     //
     AccountManager accountManager = null;
     CourseManager courseManager = null;
     RegistrationService registrationService = null;

     /**
          Utility method that prompts
          a user for input
          If they don't type anything,
          it prompts them again
     */
     protected String getInput(String prompt)
     {
          String returnValue = null;
          do
          {
               System.out.print(prompt);
               returnValue = getNextCommand();
          }
          while (returnValue.equals(""));

          return returnValue;
     }

     /**
          Utility method that prompts
          for input and converts the
          value to an int
     */
     protected int getIntInput(String prompt)
     {
          System.out.print(prompt);
          String text = getNextCommand();
          int returnValue =  Integer.parseInt(text);
          return returnValue;
     }

     /**
          Utility method that prompts
          for input and converts the
          value to an date
     */
     protected Date getDateInput(String prompt)
     {
          System.out.print(prompt);
          String text = getNextCommand();

          Calendar cal = Calendar.getInstance();
          cal.set(Calendar.YEAR, 2014);
          cal.set(Calendar.MONTH, 10);
          cal.set(Calendar.DAY_OF_MONTH, 1);
          Date date = cal.getTime();

          return date;
     }

     /**
          Reads a line from stdin
     */
     protected String getNextCommand()
     {
          String command = this.scanner.nextLine();
          return command.trim();
     }

     /**
          Mutator for couseManager
     */
     public void setCourseManager(CourseManager courseManager)
     {
          this.courseManager = courseManager;
     }

     /**
          Mutator for accountManager
     */
     public void setAccountManager(AccountManager accountManager)
     {
          this.accountManager = accountManager;
     }

     /**
          Mutator for registrationService
     */
     public void setRegistrationService(RegistrationService registrationService)
     {
          this.registrationService = registrationService;
     }

     /**
          concrete classes need to provide this method
     */
     public abstract void run() throws ExitProgramException, InterfaceTransitionException;
}
