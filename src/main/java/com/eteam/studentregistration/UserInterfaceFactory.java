/**
     UserInterfaceFactory.java

     Returns an appropriate user interface
     for an account

     Makes it so the ProgramMain object does
     not need to know about all the different
     types of user interfaces
*/

package com.eteam.studentregistration;

class UserInterfaceFactory
{
     /**
          Factory method to return an implementation
          of UserInterface based on properties of the account
          - If account is null: returns GuestInterface
          - If role is admin: returns AdminInterface
          - If role is student: returns StudentInterface
          - If role is neither: returns GuestInterface
     */
     public static UserInterface getUserInterface(Account account)
     {
          if (account == null)
          {
               return new GuestInterface();
          }
          else if (account.hasRole("admin"))
          {
               return new AdminInterface(account);
          }
          else if (account.hasRole("student"))
          {
               return new StudentInterface(account);
          }
          else
          {
               System.out.println("** Strange... Your account does not have any privileges");
               return new GuestInterface();
          }
     }
}
