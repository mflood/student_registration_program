/**
     InterfaceFinishedException.java

     This exception is used by GuestInterface
     after an account is created
     to tell the run method to stop
*/
package com.eteam.studentregistration;
import java.lang.Exception;

public class InterfaceFinishedException extends Exception
{
     public InterfaceFinishedException(String message)
     {
          super(message);
     }
}
