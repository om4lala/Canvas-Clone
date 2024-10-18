import java.io.*;
import java.util.*;

public class Student {
    
    public static void Enroll(String username) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter course title to enroll: ");
        String courseTitle = input.nextLine();
        System.out.print("Enter course number to enroll: ");
        String courseNumber = input.nextLine();
    
        if (FileHandler.checkCourseExists(courseTitle, courseNumber)) {
            if (!isUserEnrolled(username, courseTitle, courseNumber)) {
                try {
                    File file = new File("course_enrollment.csv");
                    boolean fileExists = file.exists();
    
                    FileWriter writer = new FileWriter(file, true);
    
                    if (!fileExists) {
                        writer.write("CourseTitle,courseNumber,Username\n");
                    }
    
                    writer.append(courseTitle + "," + courseNumber + "," + username + "\n");
                    writer.close();
    
                    System.out.println("Enrolled successfully.");
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            } else {
                System.out.println("You are already enrolled in this course.");
            }
        } else {
            System.out.println("Course not found in courses.csv.");
        }
    }
    
    private static boolean isUserEnrolled(String username, String courseTitle, String courseNumber) {
        try (Scanner fileReader = new Scanner(new File("course_enrollment.csv"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                String enrolledUsername = parts[2].trim();
                String enrolledCourseNumber = parts[1].trim(); 
                String enrolledCourseTitle = parts[0].trim(); 
                if (enrolledUsername.equals(username) && enrolledCourseNumber.equals(courseNumber) && enrolledCourseTitle.equals(courseTitle)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return false;
    }
    

    public static void Drop(String username) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter course title to drop: ");
        String courseTitle = input.nextLine();
        System.out.print("Enter course number to drop: ");
        String courseNumber = input.nextLine();
    
        if (FileHandler.checkEnrollmentExists(courseTitle, courseNumber, username)) {
            try {
                File inputFile = new File("course_enrollment.csv");
                File tempFile = new File("temp.csv");
    
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    
                String currentLine;
    
                while ((currentLine = reader.readLine()) != null) {
                    String[] parts = currentLine.split(",");
                    if (!parts[0].equals(courseTitle) || !parts[1].equals(courseNumber) || !parts[2].equals(username)) {
                        writer.write(currentLine + System.getProperty("line.separator"));
                    }
                }
    
                writer.close();
                reader.close();
    
                if (!inputFile.delete()) {
                    System.out.println("Failed to delete the original file.");
                    return;
                }
    
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Failed to rename the temp file.");
                    return;
                }
    
                System.out.println("Course dropped successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } else {
            System.out.println("You are not enrolled in the specified course.");
        }
    }

    public static void CheckGrades(String username) {
        System.out.println("Check grades");
    
        try (Scanner fileReader = new Scanner(new File("Student_course_enrollment.csv"))) {
            boolean found = false;
    
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
    
                if (parts.length >= 4 && parts[2].equals(username)) {
                    found = true;
                    System.out.println("Course Title: " + parts[0] + ", Course Number: " + parts[1] + ", Grade: " + parts[3]);
                }
            }
    
            if (!found) {
                System.out.println("No grades found for the user: " + username);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    public static void studentLogin() {
        int attempts = 5;
        
        System.out.println("\n-----Student Login-----");
        while (attempts > 0) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String username = input.next();
            System.out.print("Enter your password: ");
            String password = input.next();
            boolean isAuthenticated = FileHandler.authentication(username, password, "students.csv");
            if (isAuthenticated) {
                while (true) {
                    System.out.println("\nChoose one of the following\n1. Enroll in a course. \n2. Drop a course." +
                            "\n3. Check grades. \n4. View all courses the student is enrolled in. \n5. Logout");
                    System.out.print("Enter your choice: ");
                    int userInput = input.nextInt();
    
                    if (userInput == 1) {
                        Enroll(username);
                    } else if (userInput == 2) {
                        Drop(username);
                    } else if (userInput == 3) {
                        CheckGrades(username);
                    } else if (userInput == 4) {
                        FileHandler.CourseEnrolled(username);
                    } else if (userInput == 5) {
                        User.main(null);
                        return;
                    } else {
                        System.out.println("Invalid option\n");
                    }
                }
            } else {
                attempts -= 1;
                if (attempts == 0) {
                    System.out.println("Max attempts reached. Returning to main menu.");
                    User.main(null); 
                    return; 
                } else {
                    System.out.println("Invalid username or password. Attempts left: " + attempts);
                }
            }
        }
    }
      
}
