/**
     CourseSession.java

     Represents a session of a Course.
*/
package com.team.studentregistration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;

public class CourseSession implements java.io.Serializable, Comparable<CourseSession>
{
     String sessionCode = null;
     Date startDate = null;
     int studentCapacity;

     /**
          Constructor
     */
     public CourseSession(String sessionCode, Date startDate, int studentCapacity)
     {
          this.sessionCode = sessionCode;
          this.startDate = startDate;
          this.studentCapacity = studentCapacity;
     }

     /**
          accessor for studentCapacity
     */
     public int getStudentCapacity()
     {
          return this.studentCapacity;
     }

     /**
          accessor for sessionCode
     */
     public String getSessionCode()
     {
          return this.sessionCode;
     }

     /**
          accessor for startDate
     */
     public Date getStartDate()
     {
          return this.startDate;
     }

     /**
          This override allows the session to be printed out
     */
     @Override
     public String toString()
     {
          SimpleDateFormat ft = new SimpleDateFormat("MMMMM d, yyyy");
          String returnValue = "Session: "
                              + this.sessionCode
                              + " Starts: "
                              + ft.format(this.startDate)
                              + " Capacity: "
                              + this.studentCapacity;
          return returnValue;
     }

     /**
          This allows the sessions to be ordered by Date
     */
     @Override
     public int compareTo(CourseSession anotherCourseSession) {
         return anotherCourseSession.getStartDate().compareTo(this.startDate);
    }
}
