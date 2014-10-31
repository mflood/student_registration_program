/**
     AccountManager.java

     This class is responsible for managing Account objects
     It creates, stores, and loads all Accounts
     and provides an interface to find accounts by username
*/
package com.eteam.studentregistration;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.ClassNotFoundException;


public class AccountManager
{
     String accountFilePath = null;
     HashMap<String, Account> accountMap = null;

     /**
          Constructor
     */
     public AccountManager(String accountFilePath)
     {
          this.accountFilePath = accountFilePath;
          this.accountMap = new HashMap<String, Account>();
     }

     /**
          Loads a HashMap of accounts from a file
          and assigns the value to this.accountMap

          If there is no file, it creates an administrator
          account and creates the file.
     */
     public void load()
     {
          try
          {
               FileInputStream fileIn = new FileInputStream(this.accountFilePath);
               System.out.println("Loading account data from " + this.accountFilePath);
               ObjectInputStream in = new ObjectInputStream(fileIn);
               @SuppressWarnings("unchecked")
               HashMap<String, Account> savedAccountMap = (HashMap<String, Account>) in.readObject();
               in.close();
               fileIn.close();
               this.accountMap = savedAccountMap;
          }
          catch(java.io.FileNotFoundException e)
          {
               System.out.println("Initializing account data file: " + this.accountFilePath);
               if(this.accountMap.size() == 0) {
                    createAdminAccount();
               }
          }
          catch(IOException i)
          {
               i.printStackTrace();
               return;
          }
          catch(ClassNotFoundException c)
          {
               System.out.println("HashMap class not found");
               c.printStackTrace();
               return;
          }

     }

     /**
          Returns a list of all Account objects
          in the system
     */
     public List<Account> getAccountList()
     {
          List<Account> accountList = new ArrayList<Account>(this.accountMap.values());
          return accountList;
     }

     /**
          Private utility method to create the administrative account
     */
     private void createAdminAccount()
     {
          try
          {
               System.out.println("Creating admin account with username 'admin' and password 'password'");
               createAccount("admin", "-", "-", "password", "admin");

               // Create guest account
               // Empty password means you cannot log into the account
               // Presence of account means no one can create an account with username of guest
               System.out.println("Creating guest account with username 'admin' and password ''");
               createAccount("guest", "-", "-", "", "");
          }
          catch (AccountAlreadyExistsException e)
          {
              // If the admin account already exists, then we don't
              // need to create it and can ignore this exception.
          }
     }

     /**
          Serializes this.accountMap and saves it in a file
     */
     private void save()
     {
          try
          {
               FileOutputStream fileOut =
               new FileOutputStream(this.accountFilePath);
               ObjectOutputStream out = new ObjectOutputStream(fileOut);
               out.writeObject(this.accountMap);
               out.close();
               fileOut.close();
          }
          catch(IOException i)
          {
               i.printStackTrace();
          }
     }

     /**
          Creates a new account with the arguments supplied,
          and adds it to the system and re-saves the Account collection,,
          but only if the username does not already exist

          If the user already exists, throws AccountAlreadyExistsException
     */
     public void createAccount(String username, String firstname, String lastname, String password, String role) throws AccountAlreadyExistsException
     {
          // username should be case insensitive
          username = username.toLowerCase();

          if (getAccount(username) == null)
          {
               Account account = new Account(username, firstname, lastname, password, role);
               this.accountMap.put(username, account);
               save();
          }
          else
          {
               throw new AccountAlreadyExistsException("Account already exists");
          }
     }

     /**
          Returns the account matching the username,
          or null if it does not exist
     */
     public Account getAccount(String username)
     {
          // username should be case insensitive
          username = username.toLowerCase();
          Account account = this.accountMap.get(username);
          return account;
     }

     /**
          Returns account only if account exists and password is correct
          otherwise returns null
     */
     public Account getAuthenticatedAccount(String username, String password)
     {
          // username should be case insensitive
          username = username.toLowerCase();
          Account account = this.accountMap.get(username);
          if (account != null)
          {
               if(account.getPassword().equals(password))
               {
                    return account;
               }
               System.out.println("Invalid password provided");
               return null;
          }
          System.out.println("Invalid username provided");
          return null;
     }
}
