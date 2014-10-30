/**
     ExitProgramException.java

     This exception is used by a UserInterface
     to switch to tell the main run loop to exit
     the application

*/
import java.lang.Exception;

public class ExitProgramException extends Exception
{
     public ExitProgramException(String message)
     {
          super(message);
     }
}
