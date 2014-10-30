/**
     RegistrationService.java

     This class is responsible for managing student registrations
     for CourseSessions
     It creates, stores, and loads the relationships
     and provides an interface to find users signed up for sessions
     and sessions signed up to for a user
*/
package com.team.studentregistration;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.ClassNotFoundException;

public class RegistrationService
{

     String registrationFilePath = null;

     // We use two lookups,
     // one to the sessionss for each student,
     // and one store students for each session
     TreeMap<String, HashSet<String>> studentSessionMap = null;
     TreeMap<String, HashSet<String>> sessionStudentMap = null;

     /**
          Constructor
     */
     public RegistrationService(String registrationFilePath)
     {
          this.registrationFilePath = registrationFilePath;
          this.studentSessionMap = new TreeMap<String, HashSet<String>>();
          this.sessionStudentMap = new TreeMap<String, HashSet<String>>();
     }

     /**
          Deserializes the two treeMaps from a file
     */
     public void load()
     {
          try
          {
               FileInputStream fileIn = new FileInputStream(this.registrationFilePath);
               System.out.println("Loading registration data from " + this.registrationFilePath);
               ObjectInputStream in = new ObjectInputStream(fileIn);
               @SuppressWarnings("unchecked")
               TreeMap<String, HashSet<String>> savedStudentSessionMap = (TreeMap<String, HashSet<String>>) in.readObject();
               @SuppressWarnings("unchecked")
               TreeMap<String, HashSet<String>> savedSessionStudentMap = (TreeMap<String, HashSet<String>>) in.readObject();
               in.close();
               fileIn.close();
               this.studentSessionMap = savedStudentSessionMap;
               this.sessionStudentMap = savedSessionStudentMap;
          }
          catch(java.io.FileNotFoundException e)
          {
               System.out.println("Initializing registration data file: " + this.registrationFilePath);
               save();
          }
          catch(IOException i)
          {
               i.printStackTrace();
               return;
          }
          catch(ClassNotFoundException c)
          {
               System.out.println("HashhMap class not found");
               c.printStackTrace();
               return;
          }
     }

     /**
          Serializes the treeMaps and saves them to a file
     */
     private void save()
     {
          try
          {
               FileOutputStream fileOut =
               new FileOutputStream(this.registrationFilePath);
               ObjectOutputStream out = new ObjectOutputStream(fileOut);
               out.writeObject(this.studentSessionMap);
               out.writeObject(this.sessionStudentMap);
               out.close();
               fileOut.close();
          }
          catch(IOException i)
          {
               i.printStackTrace();
          }
     }

     /**
          Returns a list of all Sessions a user is signed up for
          in the form of SessionHash's
     */
     public List<SessionHash> getCourseSessions(String username)
     {
          List<SessionHash> returnList = new ArrayList<SessionHash>();
          if (studentSessionMap.containsKey(username))
          {
               HashSet<String> sessionHashList =  this.studentSessionMap.get(username);
               for (String hash: sessionHashList)
               {
                    SessionHash sessionHash = new SessionHash(hash);
                    returnList.add(sessionHash);
               }
          }
          return returnList;
     }

     /**
          Returns a list of the usernames of all student acounts
          registered to the course/session
     */
     public List<String> getStudents(String courseId, String sessionCode)
     {
          SessionHash sessionHash = new SessionHash(courseId, sessionCode);
          String hash = sessionHash.getHash();
          if (sessionStudentMap.containsKey(hash))
          {
               HashSet<String> students =  this.sessionStudentMap.get(hash);
               List<String> returnList = new ArrayList<String>(students);
               return returnList;
          }
          else
          {
               List<String> returnList = new ArrayList<String>();
               return returnList;
          }
     }

     /**
          Removes the registration of a user from a course/session
     */
     public void unregister(String username, SessionHash sessionHash)
     {
          if( studentSessionMap.containsKey(username))
          {
               studentSessionMap.get(username).remove(sessionHash.getHash());
          }
          if( sessionStudentMap.containsKey(sessionHash.getHash()))
          {
               sessionStudentMap.get(sessionHash.getHash()).remove(username);
          }
          save();
     }

     /**
          Adds a registration relationship from the user to the course/session
          Note: This does not check against course capacity
          That should be done a level above this
     */
     public void register(String username, String courseId, String sessionCode)
     {
          SessionHash sessionHash = new SessionHash(courseId, sessionCode);
          String key = sessionHash.getHash();

          if( !studentSessionMap.containsKey(username))
          {
               // User is not in the studentSessionMap yet,
               // so add user with empty session list
               HashSet<String> sessions = new HashSet<String>();
               studentSessionMap.put(username, sessions);
          }

          if( !sessionStudentMap.containsKey(key))
          {
               // Course Session is not in the sessionStudentMap yet,
               // so add user with empty student list
               HashSet<String> students = new HashSet<String>();
               sessionStudentMap.put(key, students);
          }

          HashSet<String> sessions =  this.studentSessionMap.get(username);
          sessions.add(key);

          HashSet<String> students =  this.sessionStudentMap.get(key);
          students.add(username);

          save();
     }
}
