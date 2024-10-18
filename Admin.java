import java.io.*;
import java.util.*;

public class Admin extends User{
    public int attempts;

    public Admin(String name, String username, String password, int attempts){
        super(name, username, password);
        this.attempts = attempts;

    }

    public static void adminLogin() {
        int attempts = 5;
    
        System.out.println("\n-----Admin Login-----");
        while (attempts > 0) {
            Scanner myObj = new Scanner(System.in);
            System.out.print("Enter username: ");
            String username = myObj.next();
            System.out.print("Enter password: ");
            String password = myObj.next();
    
            if ((username.equals("admin")) && (password.equals("admin"))) {
                adminDirectory();
                return; 
            } else {
                attempts -= 1;
                if (attempts == 0) {
                    System.out.println("Max attempts reached. Returning to main menu.");
                    User.main(null);
                } else {
                    System.out.println("Wrong credentials! Try again. Attempts left: " + attempts);
                }
            }
        }
    }
    

    public static void addStudentAccount() {
        System.out.println("\n-----Add Student-----");
        FileHandler.createFile("students.csv", "name", "username", "password");
        Scanner inputs = new Scanner(System.in);

        System.out.print("Enter name of student: ");
        String name = inputs.next();
        String username;
        boolean usernameExists;

        do {
            System.out.print("Enter username for student: ");
            username = inputs.next();
            usernameExists = FileHandler.usernameExists(username, "students.csv");
            if (usernameExists) {
                System.out.println("Username already exists. Please choose a different username.");
            }
        } while (usernameExists);

        System.out.print(("Enter password for student: "));
        String password = inputs.next();

        FileHandler.writeToFile("students.csv", name, username, password);
    }
    
    public static void addTeacherAccount(){
        System.out.println("\n-----Add Teacher-----");
        FileHandler.createFile("teachers.csv", "name", "username", "password");
        Scanner inputs = new Scanner(System.in);

        System.out.print("Enter name of teacher: ");
        String name = inputs.next();
        String username;
        boolean usernameExists;

        do {
            System.out.print("Enter username for teacher: ");
            username = inputs.next();
            usernameExists = FileHandler.usernameExists(username, "teachers.csv");
            if (usernameExists) {
                System.out.println("Username already exists. Please choose a different username.");
            }
        } while (usernameExists);

        System.out.print(("Enter password for teacher: "));
        String password = inputs.next();

        FileHandler.writeToFile("teachers.csv", name, username, password);
    }

    public static void createCourse() {
        System.out.println("\n-----Create Course-----");
        FileHandler.createFile("courses.csv", "title", "number");
        Scanner inputs = new Scanner(System.in);

        System.out.print("Enter course title: ");
        String courseTitle = inputs.next();
        System.out.print("Enter course number: ");
        String courseNumber = inputs.next();

        if (!FileHandler.courseExists(courseTitle, courseNumber, "courses.csv")) {
            FileHandler.writeToFile("courses.csv", courseTitle, courseNumber);
        } else {
            System.out.println("Course already exists.");
        }
    }

    public static void removeStudentAccount(){
        System.out.println("\n-----Remove Studet-----");

        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter username of person you want to remove: ");
        String input = myObj.next();

        FileHandler.removeRowFromFile("students.csv", input);
    }
         
    public static void removeTeacherAccount(){
        System.out.println("\n-----Remove Teacher-----");

        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter username of person you want to remove: ");
        String input = myObj.next();

        FileHandler.removeRowFromFile("teachers.csv", input);
    }

    public static void deleteCourse(){
        System.out.println("\n-----Delete Course-----");

        Scanner myObj = new Scanner(System.in);
        System.out.print("Enter course number you would like to remove: ");
        String input = myObj.next();

        FileHandler.removeRowFromFile("courses.csv", input);
    }

    public static void viewCourse(){
        System.out.println("\n-----View Courses-----");
        FileHandler.displayFileContents("courses.csv");
    }


    public static void adminDirectory(){
        while(true){
            try{
                System.out.print("\nChoose one of the following:\n1. Add a student\n2. Remove a student\n3. Add a teacher\n4. Remove a teacher\n5. Create a course\n6. Delete a course\n7. View a course\n8. Logout\n");
                Scanner myObj = new Scanner(System.in);
                System.out.print("Enter your choice: ");
                int input = myObj.nextInt();

                if(input == 1){
                    addStudentAccount();
                }else if(input == 2){
                    removeStudentAccount();
                }else if(input == 3){
                    addTeacherAccount();
                }else if(input == 4){
                    removeTeacherAccount();
                }else if(input == 5){
                    createCourse();
                }else if(input == 6){
                    deleteCourse();
                }else if(input == 7){
                    viewCourse();
                }else if(input == 8){
                    User.main(null);
                    break;
                }else
                    System.out.println("Invalid input! Try again.\n");
            }catch(Exception e){
                System.out.println("Invalid input! Try again.");
            }
        }
    }
}
