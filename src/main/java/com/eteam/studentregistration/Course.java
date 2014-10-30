/**
     Course.java

     Represents a course and the sessions that are available
*/
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Course implements java.io.Serializable, Comparable<Course>
{
     String courseTitle = null;
     String courseDescription = null;
     String courseId = null;
     List<CourseSession> courseSessionList = null;


     /**
          Constructor
     */
     public Course(String courseTitle, String courseDescription, String courseId)
     {
          this.courseTitle = courseTitle;
          this.courseDescription = courseDescription;
          this.courseId = courseId;
          this.courseSessionList = new ArrayList<CourseSession>();
     }

     /**
          Accessor for courseSessionList
     */
     public List<CourseSession> getCourseSessions()
     {
          return this.courseSessionList;
     }

     /**
          Utility method to find a session based on the session code
     */
     public CourseSession getCourseSession(String sessionCode)
     {
          for (CourseSession courseSession: this.courseSessionList)
          {
               if (courseSession.getSessionCode().equals(sessionCode))
               {
                    return courseSession;
               }
          }
          return null;
     }

     /**
          Accessor for title
     */
     public String getTitle()
     {
          return this.courseTitle;
     }

     /**
          Adds a CourseSession to the course,
          raises CourseSessionAlreadyExistsException
          if the session code already exists in this course
          The same start date is allowed, however, as multiple
          session could start on the same date, with different teachers, etc..
     */
     public void addCourseSession(CourseSession courseSession) throws CourseSessionAlreadyExistsException
     {
          for (CourseSession existingSession: this.courseSessionList)
          {
               if (existingSession.getSessionCode().equals(courseSession.getSessionCode()))
               {
                    throw new CourseSessionAlreadyExistsException("Error adding session to course "
                                                                  + this.courseId
                                                                  + ", a session with code "
                                                                  + courseSession.getSessionCode()
                                                                  + " already exists");
               }
          }
          this.courseSessionList.add(courseSession);
     }

     /**
          This Override allows you to print the course
     */
     @Override
     public String toString()
     {
          String returnValue = "----------------------\n"
                              + "  ("
                              + this.courseId
                              + ") "
                              + this.courseTitle.toUpperCase()
                              + "\n   "
                              + this.courseDescription;
          return returnValue;
     }

     /**
          Utility method that returns a representation
          of the sessions that can be printed to the screen
     */
     public String sessionListToString()
     {
          String returnValue = "";
          if(this.courseSessionList.size() == 0)
          {
               returnValue += "\n   (no sessions)";
          }
          else
          {
               for (CourseSession courseSession: this.courseSessionList)
               {
                    returnValue += "\n   " + courseSession;
               }
          }
          return returnValue;
     }

     /**
          Utility method that returns a representation
          of the sessions that can be printed to the screen
          This version, intended for administrators,
          uses the RegistrationService to enumerate the students
          that are registered for the session
     */
     public String sessionListToString(RegistrationService registrationService)
     {
          String returnValue = "";
          if(this.courseSessionList.size() == 0)
          {
               returnValue += "\n   (no sessions)";
          }
          else
          {
               for (CourseSession courseSession: this.courseSessionList)
               {
                    returnValue += "\n   " + courseSession;
                    List<String> studentList = registrationService.getStudents(this.courseId, courseSession.getSessionCode());
                    returnValue += " Enrolled: " + studentList.size();
                    for (String username: studentList)
                    {
                         returnValue += "\n       " + username;
                    }
               }
          }
          return returnValue;
     }

     /**
          Utility method that returns a representation
          of the sessions that can be printed to the screen
          This version, intended for students
          uses the RegistrationService to display the number of students enrolled
     */
     public String sessionListToString(RegistrationService registrationService, String username)
     {
          String returnValue = "";
          if(this.courseSessionList.size() == 0)
          {
               returnValue += "\n   (no sessions)";
          }
          else
          {
               for (CourseSession courseSession: this.courseSessionList)
               {
                    returnValue += "\n    ";
                    List<String> studentList = registrationService.getStudents(this.courseId, courseSession.getSessionCode());
                    int seatsTaken = studentList.size();
                    if (studentList.contains(username))
                    {
                         returnValue += "***";
                    }
                    else
                    {
                         returnValue += "   ";
                    }
                    returnValue += courseSession;

                    if (seatsTaken < courseSession.getStudentCapacity())
                    {
                         returnValue += " Enrolled: " + seatsTaken;
                    }
                    else
                    {
                         returnValue += " FULL";
                    }
               }
          }
          return returnValue;
     }

     /**
          This allows courses to be sorted by Course Title
     */
     @Override
     public int compareTo(Course anotherCourse)
     {
         return this.courseTitle.toUpperCase().compareTo(anotherCourse.getTitle().toUpperCase());
     }
}
