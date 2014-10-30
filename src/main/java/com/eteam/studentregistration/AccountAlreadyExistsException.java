/**
     AccountAlreadyExistsException.java

     This exception is used by AccountManager
     when someone tries to create an account
     that already exists (username is taken)
*/
package com.team.studentregistration;
import java.lang.Exception;

public class AccountAlreadyExistsException extends Exception
{
     public AccountAlreadyExistsException(String message)
     {
          super(message);
     }
}
