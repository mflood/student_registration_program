/**
     CourseManager.java
     o
     This class is responsible for managing Course objects
     It creates, stores, and loads all Courses
     and provides an interface to find Courses by CourseId
*/
package com.eteam.studentregistration;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.lang.ClassNotFoundException;
import java.util.Collections;

public class CourseManager
{

     String courseFilePath = null;
     TreeMap<String, Course> courseMap = null;

     /**
          Constructor
     */
     public CourseManager(String courseFilePath)
     {
          this.courseFilePath = courseFilePath;
          this.courseMap = new TreeMap<String, Course>();
     }

     /**
          Loads a HashMap of Courses from a file
          and assigns the value to this.courseMap
     */
     public void load()
     {
          try
          {
               FileInputStream fileIn = new FileInputStream(this.courseFilePath);
               System.out.println("Loading course data from " + this.courseFilePath);
               ObjectInputStream in = new ObjectInputStream(fileIn);
               @SuppressWarnings("unchecked")
               TreeMap<String, Course> savedCourseMap = (TreeMap<String, Course>) in.readObject();
               in.close();
               fileIn.close();
               this.courseMap = savedCourseMap;
          }
          catch(java.io.FileNotFoundException e)
          {
               System.out.println("Initializing course data file: " + this.courseFilePath);
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
          Serializes this.courseMap and saves it in a file
     */
     private void save()
     {
          try
          {
               FileOutputStream fileOut =
               new FileOutputStream(this.courseFilePath);
               ObjectOutputStream out = new ObjectOutputStream(fileOut);
               out.writeObject(this.courseMap);
               out.close();
               fileOut.close();
          }
          catch(IOException i)
          {
               i.printStackTrace();
          }
     }

     /**
          Returns all Courses in the system, sorted by Course Title
          This sort depends on Course::compareTo()
     */
     public List<Course> getCourseList()
     {
          List<Course> courseList = new ArrayList<Course>(this.courseMap.values());
          Collections.sort(courseList);
          return courseList;
     }

     /**
          Creates a new course and adds it to the system

          Raises CourseAlreadyExistsException if the course id is
          already being used
     */
     public void createCourse(String title, String description, String id) throws CourseAlreadyExistsException
     {
          if (getCourse(id) == null)
          {
               Course course = new Course(title, description, id);
               this.courseMap.put(id, course);
               save();
          }
          else
          {
               throw new CourseAlreadyExistsException("Course already exists");
          }
     }

     /**
          Creates a new session and adds it to the course identified by courseId
          If it fails, it prints a message to the screen explaining the failure
     */
     public void addSessionToCourse(String courseId, String sessionCode, Date startDate, int studentCapacity)
     {
          // find the course and make sure it exists
          Course course = this.getCourse(courseId);
          if (course == null)
          {
               System.out.println("Could not add session: Invalid course code specified");
               return;
          }

          // Create the courseSession
          // And add it to the course
          //
          CourseSession courseSession= new CourseSession(sessionCode, startDate, studentCapacity);
          try
          {
               course.addCourseSession(courseSession);
               save();
          }
          catch (CourseSessionAlreadyExistsException e)
          {
               System.out.println(e);
          }
     }

     /**
          Returns the course whose courseId matches the provided id
     */
     public Course getCourse(String id)
     {
          Course course = this.courseMap.get(id);
          return course;
     }
}
