/**
     CourseAlreadyExistsException.java

     This exception is used by CourseManager
     when someone tries to create a course
     that already exists (course id is taken)
*/
import java.lang.Exception;

public class CourseAlreadyExistsException extends Exception
{
     public CourseAlreadyExistsException(String message)
     {
          super(message);
     }
}
