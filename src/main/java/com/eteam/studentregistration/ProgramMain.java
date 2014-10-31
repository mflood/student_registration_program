/**
     ProgramMain.java

     The entry point for the Course Management System
     It creates all Manager/Service objects,
     Prompts the user for credentials,
     And picks and runs an approriate UserInterface implementation
*/
package com.eteam.studentregistration;

public class ProgramMain
{
     /**
          A few constants that can be customized
     */
     static final String ACCOUNTS_FILEPATH = "./accounts.dat";
     static final String COURSES_FILEPATH = "./courses.dat";
     static final String REGISTRATIONS_FILEPATH = "./registrations.dat";

     /**
          Main entrypoint
     */
     public static void main(String[] args)
     {
          // Create and load an instance of the AccountManager
          //
          AccountManager accountManager = new AccountManager(ACCOUNTS_FILEPATH);
          accountManager.load();

          // Create and load an instance of the CourseManager
          //
          CourseManager courseManager = new CourseManager(COURSES_FILEPATH);
          courseManager.load();

          // Create and load an instance of the RegistrationService
          //
          RegistrationService registrationService = new RegistrationService(REGISTRATIONS_FILEPATH);
          registrationService.load();

          // This represents the person using the system
          //
          Account account = null;

          while (true)
          {
               // Create a user interface, configure it, and run it
               //
               UserInterface userInterface = UserInterfaceFactory.getUserInterface(account);

               // Using these mutators instead of passing these as args to getUserInterface
               // to avoid unneccessary complication of the UserInterfaceFactory
               //
               userInterface.setAccountManager(accountManager);
               userInterface.setCourseManager(courseManager);
               userInterface.setRegistrationService(registrationService);

               try
               {
                    userInterface.run();
               }
               catch (InterfaceTransitionException e)
               {
                    account = e.getAccount();
               }
               catch (ExitProgramException e)
               {
                    break;
               }
          }
     }
}
