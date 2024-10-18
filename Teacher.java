import java.io.*;
import java.util.*;

public class Teacher extends User{
    
    public Teacher(String name, String username, String password) {
        super(name, username, password);
    }

    public static void enrollInCourse(String username) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter course title to enroll: ");
        String courseTitle = input.nextLine();
        System.out.print("Enter course number: ");
        String courseNumber = input.nextLine();
    
        if (FileHandler.courseExists(courseTitle, courseNumber, "courses.csv")) {
            if (!FileHandler.isCourseEnrolled(courseTitle, courseNumber, username)) {
                try (FileWriter writer = new FileWriter("teacher_course_enrollment.csv", true)) {
                    writer.append(courseTitle + "," + courseNumber + "," + username + "\n");
                    System.out.println("Enrolled successfully.");
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            } else {
                System.out.println("You are already enrolled in this course.");
            }
        } else {
            System.out.println("Course not found.");
        }
    }
    
    public static void unenrollFromCourse(String username) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter course number to drop: ");
        String courseNumber = input.nextLine();

        if (FileHandler.checkEnrollmentExists(courseNumber, username)) {
            try {
                File inputFile = new File("teacher_course_enrollment.csv");
                File tempFile = new File("temp.csv");

                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                String currentLine;

                while ((currentLine = reader.readLine()) != null) {
                    String[] parts = currentLine.split(",");
                    if (!parts[0].equals(courseNumber) || !parts[1].equals(username)) {
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
    
    public static void enrollStudent(String courseTitle, String courseNumber, String studentUsername, String teacherUsername) {
        if (FileHandler.isTeacherEnrolled(courseTitle, courseNumber, teacherUsername)) {
            try (FileWriter writer = new FileWriter("Student_course_enrollment.csv", true)) {
                writer.append(courseTitle + "," + courseNumber + "," + studentUsername + "\n");
                System.out.println("Student " + studentUsername + " added to course " + courseTitle + " - " + courseNumber);
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        } else {
            System.out.println("Teacher " + teacherUsername + " is not enrolled in course " + courseTitle + " - " + courseNumber);
        }
    }
    
    public static void unenrollStudent() {
        System.out.println("Remove Student");

        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter course title: ");
            String courseTitle = input.nextLine();
            System.out.print("Enter course number: ");
            String courseNumber = input.nextLine();

            String filename = "Student_course_enrollment.csv";
            File inputFile = new File(filename);
            File tempFile = new File("temp.csv");

            if (!inputFile.exists()) {
                System.out.println("Student file does not exist.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            boolean found = false;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(",");
                if (parts.length > 1 && parts[0].trim().equals(courseTitle) && parts[1].trim().equals(courseNumber)) {
                    found = true;
                    System.out.print("Enter student username to remove: ");
                    String usernameToRemove = input.nextLine();
                    if (parts.length > 2 && parts[2].trim().equals(usernameToRemove)) {
                        continue;
                    }
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }

            if (!found) {
                System.out.println("No students enrolled in course " + courseTitle + " - " + courseNumber + ".");
                writer.close();
                reader.close();
                return;
            }

            writer.close();
            reader.close();

            if (!inputFile.delete()) {
                System.out.println("Failed to delete the original file.");
                return;
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Failed to rename temporary file to original filename. Please ensure the file is not being used by another process.");
                return;
            }

            System.out.println("Student removed successfully from course " + courseTitle + " - " + courseNumber + ".");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public static void assignGrades(String teacherUsername) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter course title to assign grades: ");
            String courseTitle = input.nextLine();
            System.out.print("Enter course number to assign grades: ");
            String courseNumber = input.nextLine();
    
            if (FileHandler.isTeacherEnrolled(courseTitle, courseNumber, teacherUsername)) {
                System.out.print("Enter student username to assign grades: ");
                String studentUsername = input.nextLine();
    
                File inputFile = new File("Student_course_enrollment.csv");
                File tempFile = new File("temp.csv");
    
                if (!inputFile.exists()) {
                    System.out.println("Student enrollment file does not exist.");
                    return;
                }
    
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    
                String currentLine;
                boolean found = false;
    
                while ((currentLine = reader.readLine()) != null) {
                    String[] parts = currentLine.split(",");
                    if (parts.length > 2 && parts[0].equals(courseTitle) && parts[1].equals(courseNumber) && parts[2].equals(studentUsername)) {
                        found = true;
                        System.out.print("Enter grade for student " + studentUsername + ": ");
                        String grade = input.nextLine();
                        currentLine = courseTitle + "," + courseNumber + "," + studentUsername + "," + grade;
                    }
                    writer.write(currentLine + "\n");
                }
    
                writer.close();
                reader.close();
    
                if (!found) {
                    System.out.println("Student not found in course enrollment.");
                    return;
                }
    
                if (!inputFile.delete()) {
                    System.out.println("Failed to delete the original file.");
                    return;
                }
                if (!tempFile.renameTo(inputFile)) {
                    System.out.println("Failed to rename temporary file to original filename. Please ensure the file is not being used by another process.");
                    return;
                }
    
                System.out.println("Grade assigned successfully.");
            } else {
                System.out.println("You are not enrolled in the specified course.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        } catch (SecurityException se) {
            System.out.println("SecurityException occurred while renaming the file: " + se.getMessage());
        } catch (Exception ex) {
            System.out.println("An error occurred: " + ex.getMessage());
        }
    }
    
    
    public static void teacherLogin() {
        int attempts = 5;
        
        System.out.println("\n-----Teacher Login-----");
        while (attempts > 0) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter your username: ");
            String username = input.next();
            System.out.print("Enter your password: ");
            String password = input.next();
            
            boolean isAuthenticated = FileHandler.authentication(username, password, "teachers.csv");
            
            if (isAuthenticated) {
                while (true) {
                    System.out.println("\nChoose one of the following:\n1. Enroll in a course. \n2. Drop a course." +
                            "\n3. Add students to a course. \n4. Remove students from a course. \n5. Assign grades to students in courses. \n6. View all students in a course."+
                            "\n7. View all courses the teacher is enrolled in. \n8. Logout");
                    System.out.print("Enter your choice: ");
                    int choice = input.nextInt();
        
                    if (choice == 1) {
                        enrollInCourse(username);
                    } else if (choice == 2) {
                        unenrollFromCourse(username);
                    } else if (choice == 3) {
                        System.out.print("Enter course title: ");
                        String courseTitle = input.nextLine(); 
                        courseTitle = input.nextLine(); 
                        System.out.print("Enter course number: ");
                        String courseNumber = input.next();
                        System.out.print("Enter student username: ");
                        String studentUsername = input.next();
                        enrollStudent(courseTitle, courseNumber, studentUsername, username);
                    } else if (choice == 4) {
                        unenrollStudent();
                    } else if (choice == 5) {
                        assignGrades(username);
                    } else if (choice == 6) {
                        FileHandler.viewMyStudents();
                    } else if (choice == 7) {
                        FileHandler.viewMyCourses(username);
                    } else if (choice == 8) {
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
