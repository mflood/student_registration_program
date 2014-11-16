/**
     AdminInterface.java

     Provides student-level access to the system
*/
package com.eteam.studentregistration;
import java.util.List;

public class StudentInterface extends UserInterface
{
     Account account;

     public StudentInterface(Account account)
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
          System.out.println("          Student Usage");
          System.out.println("-------------------------------");
          System.out.println("help           Help (show this screen)");
          System.out.println("courses        List all courses");
          System.out.println("listreg        List all registered courses");
          System.out.println("reg            Register for a course");
          System.out.println("unreg          Unregister from a course");
          System.out.println("exit           Exit to Guest interface");
          System.out.println("quit           Quit");
          System.out.println("-------------------------------");
     }

     /**
          Provides a dialog for unregistering from a course/session
     */
     private void unregisterForCourseSession()
     {
          String courseId = getInput("Enter course ID> ");
          String sessionCode = getInput("Enter Session Code> ");

          List<SessionHash> courseList = this.registrationService.getCourseSessions(this.account.getUsername());
          SessionHash sessionHash = new SessionHash(courseId, sessionCode);
          String hashToDelete = sessionHash.getHash();

          boolean found = false;
          for (SessionHash existingSessionHash: courseList)
          {
               if (existingSessionHash.getHash().equals(hashToDelete))
               {
                    found = true;
                    break;
               }
          }
          if(! found)
          {
               System.out.println("** Invalid course/session supplied");
               return;
          }

          this.registrationService.unregister(this.account.getUsername(), sessionHash);
          System.out.println("** You have successfully unregistered from Course " + courseId + ", Session " + sessionCode);
     }

     /**
          Provides a dialog for registering for a Course/Session
     */
     private void registerForCourseSession()
     {
          String courseId = getInput("Enter course ID> ");
          Course course = this.courseManager.getCourse(courseId);

          // Validate the course
          //
          if(course == null)
          {
              System.out.println("** Invalid course ID specified");
              return;
          }

          // Verify that this Course has sessions
          //
          List <CourseSession> sessionList = course.getCourseSessions();
          if(sessionList.size() == 0)
          {
               System.out.println("** That course has no sessions");
               return;
          }

          // Show the user the available sessions
          // so they can pick one
          //
          System.out.println("-- Course Sessions --");
          for (CourseSession availableSession: sessionList)
          {
               System.out.println(availableSession);
          }

          // Pick the course session
          //
          CourseSession courseSession;
          // If there is only one session
          // don't make the user pick it
          if (sessionList.size() == 1)
          {
              courseSession = sessionList.get(0);
          }
          else
          {
               String sessionCode = getInput("Enter Session Code> ");
               courseSession = course.getCourseSession(sessionCode);

               // Validate the session
               //
               if (courseSession == null)
               {
                    System.out.println("** Invalid Session code provided");
                    return;
               }
          }

          List<String> currentStudents = this.registrationService.getStudents(courseId, courseSession.getSessionCode());

          // Make sure we are not already enrolled
          //
          if (currentStudents.contains(this.account.getUsername()))
          {
              System.out.println("** You are already registered for that session");
               return;
          }

          // Make sure the sesion is not already at capacity
          //
          if (currentStudents.size() >= courseSession.getStudentCapacity())
          {
               System.out.println("** That course is full");
               return;
          }

          this.registrationService.register(this.account.getUsername(), courseId, courseSession.getSessionCode());
          System.out.println("** You have successfully registered for Course " + courseId + ", Session " + courseSession.getSessionCode());
     }

     /**
          Prints out all the courses the current Student Account is registered for
     */
     private void showRegisteredCourses()
     {
          System.out.println("----- Courses you are registered for -----");
          List<SessionHash> courseList = this.registrationService.getCourseSessions(this.account.getUsername());
          for (SessionHash sessionHash : courseList)
          {
               String courseId = sessionHash.getCourseId();
               String sessionCode = sessionHash.getSessionCode();

               Course course = courseManager.getCourse(courseId);
               CourseSession courseSession = course.getCourseSession(sessionCode);
               System.out.println(course);
               System.out.println("     " + courseSession);
          }
     }

     /**
          Prints out all Courses and Course Sessions
     */
     private void printCourseList()
     {
          System.out.println("----- Course List -------");
          List<Course> courseList= this.courseManager.getCourseList();
          for (Course course : courseList)
          {
               System.out.println(course);
               // This version of sessionListToString
               // highlights the sessions the current student account is signed up for
               //
               System.out.println(course.sessionListToString(this.registrationService, this.account.getUsername()));
          }
     }

     /**
          The main loop of the interface
          Prompts user for an action, and then performs it
     */
     public void run() throws ExitProgramException, InterfaceTransitionException
     {
          System.out.println("** Launching Student Interface");
          printUsage();
          boolean keepGoing = true;
          while(keepGoing == true)
          {
               System.out.print("(" + this.account.getUsername() + ") Command> ");
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
               else if(command.equals("help"))
               {
                    printUsage();
               }
               else if(command.equals("reg"))
               {
                    registerForCourseSession();
               }
               else if(command.equals("unreg"))
               {
                    unregisterForCourseSession();
               }
               else if(command.equals("listreg"))
               {
                   showRegisteredCourses();
               }
               else if(command.equals("courses"))
               {
                    printCourseList();
               }
               else
               {
                    printUsage();
               }
          }
     }
}
