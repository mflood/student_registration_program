/**
     InterfaceTransitionException.java

     This exception is used by a UserInterface
     to switch to tell the main run loop to switch to
     a different user interface

     The main loop gets the Account stored in this
     Exception to determine which interface to provide

*/
import java.lang.Exception;

public class InterfaceTransitionException extends Exception
{
     Account account;
     public InterfaceTransitionException(Account account)
     {
          super("transition exception");
          this.account = account;
     }

     public Account getAccount()
     {
          return this.account;
     }
}
