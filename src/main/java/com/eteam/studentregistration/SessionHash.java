/**
     SessionHash.java

     Utility class to allow you to easily
     go from course/session to the course:::session hash used
     by the registration service
*/
package com.eteam.studentregistration;

public class SessionHash
{
     String courseId;
     String sessionCode;

     /**
          Constructor

          This version takes the course/sessioncode separately
     */
     public SessionHash(String courseId, String sessionCode)
     {
          this.courseId = courseId;
          this.sessionCode = sessionCode;
     }

     /**
          Alternate Construtor

          This version takes the course/session hash
     */
     public SessionHash(String hash)
     {
          String[] tuple = hash.split(":::");
          this.courseId = tuple[0];
          this.sessionCode = tuple[1];
     }

     /**
          Acceessor for courseId
     */
     public String getCourseId()
     {
          return this.courseId;
     }

     /**
          Acceessor for sessionCode
     */
     public String getSessionCode()
     {
          return this.sessionCode;
     }

     /**
          Converts the courseId and sessionId
          into a single string
          representing the distinct hash
          of the course/session
     */
     public String getHash()
     {
          String hash = this.courseId + ":::" + this.sessionCode;
          return hash;
     }
}
