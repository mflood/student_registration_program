/**
     CourseSessionAlreadyExistsException.java

     This exception is used by Course
     when someone tries to create a session
     that already exists (session code is taken)
*/
import java.lang.Exception;

public class CourseSessionAlreadyExistsException extends Exception
{
     public CourseSessionAlreadyExistsException(String message)
     {
          super(message);
     }
}
