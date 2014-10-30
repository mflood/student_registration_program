/**
     GiuestInterface.java

     Provides guest-level access to the system
*/
import java.util.List;
import java.util.Scanner;

public class GuestInterface extends UserInterface
{
     /**
          Lists the commands available to the user
          through this interface
     */
     private void printUsage()
     {
          System.out.println("-------------------------------");
          System.out.println("          Guest Usage");
          System.out.println("-------------------------------");
          System.out.println("help             Help (show this screen)");
          System.out.println("login            Log into an account");
          System.out.println("courses          List all courses");
          System.out.println("new              Create new student account");
          System.out.println("quit             Quit");
          System.out.println("-------------------------------");
     }

     /**
          Provides a dialog for logging into the system
     */
     private void login() throws InterfaceTransitionException
     {
          // Get username and password and find account
          //
          Scanner scanner = new Scanner(System.in);
          String username;
          do
          {
               System.out.print("Enter your username (or guest)> ");
               username = scanner.nextLine().trim();
          }
          while (username.equals(""));

          // For guest, the account should be null,
          // otherwise, find the account using
          // supplied credentials
          Account account = null;
          if(!username.toLowerCase().equals("guest"))
          {
               String password;
               // Prompt for nonblank password
               do
               {
                    System.out.print("Enter your password> ");
                    password = scanner.nextLine().trim();
               }
               while (password.equals(""));

               account = accountManager.getAuthenticatedAccount(username, password);

               // If the credentials failed, make them the Guest user
               // by leaving account == null
               if (account == null) {
                    System.out.println("Invalid credentials, continuing as user Guest");
               }
               else
               {
                    throw new InterfaceTransitionException(account);
               }
          }
     }

     /**
          Allows the user to create a new account
     */
     private void createAccount() throws InterfaceFinishedException
     {
          System.out.println("----- let's make an account -----");

          String username = getInput("Enter your desired username> ");
          String password = getInput("Enter your desired password> ");
          String firstname = getInput("Enter your first name> ");
          String lastname = getInput("Enter your last name> ");
          try
          {
               this.accountManager.createAccount(username, firstname, lastname, password, "student");
               System.out.println("Your account has been created, please re-run the program and log into your account");

               // This let's us jump out of the run method effectively
               throw new InterfaceFinishedException("Account created");
          }
          catch (AccountAlreadyExistsException e)
          {
               System.out.println("Sorry, an account with that name already exists");
          }
     }

     /**
          Prints out course list
     */
     private void printCourseList()
     {
          List<Course> courseList = this.courseManager.getCourseList();
          System.out.println("\n----- Course List -------");
          for (Course course : courseList)
          {
               System.out.println(course);
               System.out.println(course.sessionListToString());
          }
     }

     /**
          The main loop of the interface
          Prompts user for an action, and then performs it
     */
     public void run() throws ExitProgramException, InterfaceTransitionException
     {
          System.out.println("Launching Guest Interface");
          printUsage();

          while(true)
          {
               System.out.print("(GUEST) Command> ");
               String command = getNextCommand();
               command = command.toLowerCase();
               if(command.equals("quit"))
               {
                    // exit the loop
                    throw new ExitProgramException("");
               }
               else if(command.equals("courses"))
               {
                    printCourseList();
               }
               else if(command.equals("new"))
               {
                    try
                    {
                         createAccount();
                    }
                    catch (InterfaceFinishedException e)
                    {
                         // exit the loop
                         break;
                    }

               }
               else if(command.equals("login"))
               {
                    try
                    {
                         login();
                    }
                    catch (InterfaceTransitionException e)
                    {
                         throw e;
                    }
               }
               else if(command.equals("help"))
               {
                    printUsage();
               }
               else
               {
                    // If they type in invalid command,
                    // show help screen
                    printUsage();
               }
          }
     }
}
