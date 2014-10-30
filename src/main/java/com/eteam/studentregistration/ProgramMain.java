/**
     ProgramMain.java

     The entry point for the Course Management System
     It creates all Manager/Service objects,
     Prompts the user for credentials,
     And picks and runs an approriate UserInterface implementation
*/
package com.team.studentregistration;

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

          Account account = null;
          while (true)
          {
               // Create a user interface and run it
               //
               UserInterface userInterface = getUserInterface(account);
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

     /**
          Factory method to return an implementation
          of UserInterface based on properties of the account
          - If account is null: returns GuestInterface
          - If role is admin: returns AdminInterface
          - If role is student: returns StudentInterface
          - If role is neither: returns GuestInterface
     */
     public static UserInterface getUserInterface(Account account)
     {
          if (account == null)
          {
               return new GuestInterface();
          }
          else if (account.hasRole("admin"))
          {
               return new AdminInterface(account);
          }
          else if (account.hasRole("student"))
          {
               return new StudentInterface(account);
          }
          else
          {
               System.out.println("Strange... Your account does not have any privileges");
               return new GuestInterface();
          }
     }
}
