/**
     Account.java

     Represents a system user,
     which could be a student or an administrator
*/
package com.eteam.studentregistration;
import java.util.List;
import java.io.Serializable;

public class Account implements java.io.Serializable
{
     String username = null;
     String firstname = null;
     String lastname = null;
     String password = null;
     String role = null;

     /**
          Constructor
     */
     public Account(String username, String firstname, String lastname, String password, String role)
     {
          this.username = username;
          this.firstname = firstname;
          this.lastname = lastname;
          this.password = password;
          this.role = role;
     }

     /**
          Accessor for username
     */
     public String getUsername()
     {
          return this.username;
     }

     /**
          Accessor for password
     */
     public String getPassword()
     {
          return this.password;
     }

     /**
          Checks if this account
          has the role passed in
     */
     public boolean hasRole(String role)
     {
          return (this.role.toLowerCase().equals(role.toLowerCase()));
     }

     /**
          Allows you to print the Account
     */
     @Override
     public String toString()
     {
          String returnValue = "Account Username: "
                              + this.username
                              + " Name: "
                              + this.firstname
                              + " "
                              + this.lastname
                              + " password: "
                              + this.password
                              + " role: "
                              + this.role;
          return returnValue;
     }
}
