/**
     AdminInterface.java

     Provides administrative-level access to the system
*/
package com.eteam.studentregistration;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

public class AdminInterface extends UserInterface
{
     Account account;

     public AdminInterface(Account account)
     {
          this.account = account;
     }

     /**
          Lists the commands available to the user
          through this interface
     */
     private void printUsage()
     {
          System.out.println("-------------------------------");
          System.out.println("          Admin Usage");
          System.out.println("-------------------------------");
          System.out.println("help           Help (show this screen)");
          System.out.println("accounts       List all students");
          System.out.println("courses        List all courses");
          System.out.println("newcourse      Create new course");
          System.out.println("newsession     Create new course session");
          System.out.println("sample         Load sample course, student and registration data");
          System.out.println("exit           Exit to Guest interface");
          System.out.println("quit           Quit");
          System.out.println("-------------------------------");
     }

     /**
          Creates a bunch of sample Courses, Course Sessions, and Student Accounts
     */
     private void loadSampleData()
     {
          SampleDataLoader.loadSampleData(this.courseManager,
                                          this.accountManager,
                                          this.registrationService);
     }

     /**
          mini dialog to create a new course
          prompts for course info, then creates the course
     */
     private void createCourse()
     {
          System.out.println("----- creating new course -----");

          String courseId = getInput("Enter course identification number> ");
          Course course = this.courseManager.getCourse(courseId);
          if (course != null)
          {
               System.out.println("A course with that ID already exists");
               return;
          }

          String title = getInput("Enter course title> ");
          String desc = getInput("Enter course description> ");

          try
          {
               this.courseManager.createCourse(title, desc, courseId);
          }
          catch (CourseAlreadyExistsException e)
          {
               System.out.println("A course with that ID already exists.");
          }
     }

     /**
          Mini dialog to create a new session for a course
          Prompts for info, then creates one
     */
     private void createSession()
     {
          System.out.println("----- creating new course session -----");
          String courseId = getInput("Enter course id> ");

          Course course = this.courseManager.getCourse(courseId);
          if (course == null)
          {
               System.out.println("Invalid course specified");
               return;
          }

          String code = getInput("Enter new session code> ");
          int capacity = getIntInput("Enter new session student capacity> ");
          Date startDate = getDateInput("Enter session start date> ");

          this.courseManager.addSessionToCourse(courseId, code, startDate, capacity);
     }

     /**
          Prints course list, showing sessions and the names of students
          enrolled in each session
     */
     private void printCourseList()
     {
          System.out.println("----- Course List -------");
          List<Course> courseList= this.courseManager.getCourseList();
          for (Course course : courseList)
          {
               System.out.println(course);
               System.out.println(course.sessionListToString(this.registrationService));
          }
     }

     /**
          Prints out all account information
     */
     private void printAccounts()
     {
          for (Account account: this.accountManager.getAccountList())
          {
               System.out.println(account);
          }
     }

     /**
          The main loop of the interface
          Prompts user for an action, and then performs it
     */
     public void run() throws ExitProgramException, InterfaceTransitionException
     {
          System.out.println("Launching Admin Interface");
          printUsage();
          while(true)
          {
               System.out.print("(ADMIN) Command> ");
               String command = getNextCommand();
               if(command.equals("quit"))
               {
                    throw new ExitProgramException("");
               }
               if(command.equals("exit"))
               {
                    // using a null for the Account argument
                    // will cause transition to GuestInterface
                    throw new InterfaceTransitionException(null);
               }
               else if(command.equals("newcourse"))
               {
                    createCourse();
               }
               else if(command.equals("sample"))
               {
                    loadSampleData();
               }
               else if(command.equals("newsession"))
               {
                    createSession();
               }
               else if(command.equals("courses"))
               {
                    printCourseList();
               }
               else if(command.equals("accounts"))
               {
                    printAccounts();
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
