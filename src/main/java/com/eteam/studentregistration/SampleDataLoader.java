/**
     SampleDataLoader.java

     Puts sample data into the system
*/
package com.team.studentregistration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SampleDataLoader
{
     /**
          Creates a bunch of sample Courses, Course Sessions, and Student Accounts
     */
     public static void loadSampleData(CourseManager courseManager, AccountManager accountManager, RegistrationService registrationService)
     {
          try
          {
               // Create a bunch of courses
               //
               courseManager.createCourse("Introduction to Java part 1", "Learn the basics of Java", "JAVA101");
               courseManager.createCourse("Introduction to Java part 2", "Learn more basics of Java", "JAVA102");
               courseManager.createCourse("Introduction to Mac OS X part 1", "Learn how to use Mac OS X", "OSX101");
               courseManager.createCourse("Introduction to Mac OS X part 2", "Learn how to use Mac OS X", "OSX102");
               courseManager.createCourse("Introduction to Cloud Computing", "Learn about using the cloud", "CLOUD101");
               courseManager.createCourse("Water: The Essential Resource", "Using water as the unifying theme, explore ocean and freshwater topics and instructional strategies to integrate environmental content in your teaching practice.", "WAT101");
               courseManager.createCourse("Interactive Computer Graphics with WebGL", "In this course, we will learn how to create three-dimensional interactive applications using WebGL that run within the latest web browsers.", "GL101");
               courseManager.createCourse("Emergence of Life", "Take a look through the 4-billion-year history of life on Earth through the lens of the modern Tree of Life!", "LIFE101");
               courseManager.createCourse("Game Theory II: Advanced Applications", "This advanced course considers how to design interactions between agents in order to achieve good social outcomes.", "GAME102");
               courseManager.createCourse("Game Theory", "The course will provide the basics: representing games and strategies, the extensive form, Bayesian games, repeated and stochastic games, and more.", "GAME101");
               courseManager.createCourse("Child Nutrition and Cooking", "This course will help prepare participants to be the leading health providers, teachers and parents of the present and future.", "NUT101");

               // Create two different start dates
               // One for winter of 2014 and one for
               // spring of 2015
               //
               Calendar cal = Calendar.getInstance();
               cal.set(Calendar.YEAR, 2014);
               cal.set(Calendar.MONTH, 10);
               cal.set(Calendar.DAY_OF_MONTH, 1);
               Date fallStartDate = cal.getTime();

               cal.set(Calendar.YEAR, 2015);
               cal.set(Calendar.MONTH, 02);
               cal.set(Calendar.DAY_OF_MONTH, 1);
               Date springStartDate = cal.getTime();


               // Add Winter 2014 Sessions to some, but not all of the courses
               //
               courseManager.addSessionToCourse("JAVA101", "WIN14", fallStartDate, 30);
               courseManager.addSessionToCourse("OSX101", "WIN14", fallStartDate, 30);
               courseManager.addSessionToCourse("CLOUD101", "WIN14", fallStartDate, 30);
               courseManager.addSessionToCourse("WAT101", "WIN14", fallStartDate, 30);
               courseManager.addSessionToCourse("GL101", "WIN14", fallStartDate, 30);
               courseManager.addSessionToCourse("LIFE101", "WIN14", fallStartDate, 10);
               courseManager.addSessionToCourse("NUT101", "WIN14", fallStartDate, 20);

               // Create a few Spring 2015 sessions, with variations on capacity
               //
               courseManager.addSessionToCourse("GAME102", "SPR15", springStartDate, 10);
               courseManager.addSessionToCourse("OSX102", "SPR15", springStartDate, 5);
               courseManager.addSessionToCourse("JAVA102", "SPR15", springStartDate, 30);
               courseManager.addSessionToCourse("WAT101", "SPR15", springStartDate, 5);
               courseManager.addSessionToCourse("NUT101", "SPR15", springStartDate, 25);


               // Create 50 Students
               //
               String[] names = new String[] {"Alvina Arvie", "Elden Eguia", "Coretta Consiglio", "Cecily Caban", "Jessika Jerman",
               "Alberto Alvardo", "Consuela Catalano", "Edgardo Earwood", "Meaghan Manriquez", "Rene Rome",
               "Angel Asmussen", "Gabriella Gaertner", "Donnell Diebold", "Celeste Casazza", "Suzette Scherer",
               "Halina Hoobler", "Shaquana Southers", "Kirby Kowaleski", "Ethel Ells", "Marietta Maxim",
               "Sylvie Swoboda", "Estelle Englert", "Milly Merten", "Celesta Collman", "Hilario Hardin",
               "Sarina Stolp", "Robert Rosebrook", "Georgia Goris", "Brock Beebe", "Chang Christen",
               "Calvin Coughlan", "Joannie Jeon", "Lavonna Lunde", "Trang Tardy", "Tom Twigg", "Jaime Janis",
               "Eusebio Everitt", "Santa Seaberg", "Kirstie Kennison", "Corrinne Cartledge", "Karry Kueter",
               "Karlyn Kiesel", "Cynthia Cadieux", "Dian Ducker", "Jacinda Jordison", "Tracie Thelen",
               "Claudine Crecelius", "Laronda Leask", "Alane Amesquita", "Virginia Vieira"};

               for (String name: names)
               {
                    String[] name_parts = name.split(" ");
                    String firstname = name_parts[0];
                    String lastname = name_parts[1];
                    String password = "password";
                    String username = firstname.substring(0,1).toLowerCase() + lastname.toLowerCase();

                    // Create the student account
                    //
                    try
                    {
                         accountManager.createAccount(username, firstname, lastname, password, "student");
                    }
                    catch (AccountAlreadyExistsException e)
                    {
                         // don't care
                    }

                    // Register students for some classes
                    //
                    // Fill up the JAVA classes
                    // then put students into Nutrition course
                    //
                    if (registrationService.getStudents("JAVA101", "WIN14").size() < 30)
                    {
                         registrationService.register(username, "JAVA101", "WIN14");
                    }
                    if (registrationService.getStudents("JAVA102", "SPR15").size() < 30)
                    {
                         registrationService.register(username, "JAVA102", "SPR15");
                    }
                    else
                    {
                         if(registrationService.getStudents("NUT101", "WIN14").size() < 19)
                         {
                              registrationService.register(username, "NUT101", "WIN14");
                         }
                    }
               }

          }
          catch (CourseAlreadyExistsException e)
          {
               System.out.println("looks like sample data is already loaded");
          }
     }
}
