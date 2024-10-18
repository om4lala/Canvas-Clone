import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    public static void createFile(String fileName, String header1, String header2, String header3) {
        try {
            File newFile = new File(fileName);
            boolean fileCreated = newFile.createNewFile();
            if (fileCreated) {
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(header1 + ", " + header2 + ", " + header3 + "\n");
                fileWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, String first, String second, String third) {
        try {
            FileWriter file = new FileWriter(fileName, true);
            file.write(first + ", " + second + ", " + third + "\n");
            file.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void createFile(String fileName, String header1, String header2) {
        try {
            File newFile = new File(fileName);
            boolean fileCreated = newFile.createNewFile();
            if (fileCreated) {
                FileWriter fileWriter = new FileWriter(fileName);
                fileWriter.write(header1 + ", " + header2 + "\n");
                fileWriter.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, String first, String second) {
        try {
            FileWriter file = new FileWriter(fileName, true);
            file.write(first + ", " + second + "\n");
            file.close();
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static String[][] readFile(String fileName) {
        List<String[]> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                lines.add(parts);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }

        String[][] result = new String[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            result[i] = lines.get(i);
        }

        return result;
    }

    public static void removeRowFromFile(String fileName, String searchTerm) {
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(searchTerm) && !found) {
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("There are no contents in the file");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file");
            e.printStackTrace();
        }
    }

    public static boolean usernameExists(String username, String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                if (parts.length >= 2 && parts[1].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            
        }
        return false;
    }

    public static boolean authentication(String username, String password, String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                if ((parts.length >= 3 && parts[1].equals(username)) && (parts.length >= 3 && parts[2].equals(password))) {
                    return true;
                }
            }
        } catch (IOException e) {
            
        }
        return false;
    }

    public static boolean courseExists(String title, String number, String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].trim().equals(title) && parts[1].trim().equals(number)) {
                    return true;
                }
            }
        } catch (IOException e) {
            
        }
        return false;
    }

    public static void displayFileContents(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("File Contents:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            
        }
    }

    public static void removeStudentFromCourse(String fileName, String courseInfo, String studentName) {
        List<String> updatedLines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",\\s*");
                if (parts.length >= 3 && parts[0].equals(courseInfo) && parts[2].equals(studentName)) {
                    continue;
                }
                updatedLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String updatedLine : updatedLines) {
                bw.write(updatedLine);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    public static boolean isCourseEnrolled(String courseTitle, String courseNumber, String username) {
        try (Scanner fileReader = new Scanner(new File("teacher_course_enrollment.csv"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                String enrolledCourseTitle = parts[0];
                String enrolledCourseNumber = parts[1];
                String enrolledTeacher = parts[2]; 
                if (enrolledCourseTitle.equals(courseTitle) && enrolledCourseNumber.equals(courseNumber) && enrolledTeacher.equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            
        }
        return false;
    }

    public static boolean checkEnrollmentExists(String courseNumber, String username) {
        try (Scanner fileReader = new Scanner(new File("teacher_course_enrollment.csv"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals(courseNumber) && parts[1].equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            
        }
        return false;
    }

    public static boolean isTeacherEnrolled(String courseNumber) {
        try (Scanner fileReader = new Scanner(new File("teacher_course_enrollment.csv"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                String enrolledUsername = parts[1];
                String enrolledCourse = parts[0];
                if (enrolledUsername.equals(enrolledUsername) && enrolledCourse.equals(courseNumber)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            
        }
        return false;
    }

    public static boolean isTeacherEnrolled(String courseTitle, String courseNumber, String teacherUsername) {
        try (Scanner fileReader = new Scanner(new File("teacher_course_enrollment.csv"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String enrolledCourseTitle = parts[0];
                    String enrolledCourseNumber = parts[1];
                    String enrolledUsername = parts[2];
                    if (enrolledUsername.equals(teacherUsername) && enrolledCourseTitle.equals(courseTitle) && enrolledCourseNumber.equals(courseNumber)) {
                        return true;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            
        }
        return false;
    }
    

    public static boolean isTeacherEnrolled(String courseNumber, String teacherUsername) {
        try (Scanner fileReader = new Scanner(new File("teacher_course_enrollment.csv"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                String enrolledUsername = parts[1];
                String enrolledCourse = parts[0];
                if (enrolledUsername.equals(teacherUsername) && enrolledCourse.equals(courseNumber)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            
        }
        return false;
    }

    public static void viewMyCourses(String username){
        System.out.println("ViewTeacherEnroll");
        System.out.println("Courses enrolled by " + username + ":");
        try (Scanner fileReader = new Scanner(new File("teacher_course_enrollment.csv"))) {
            boolean found = false;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                String enrolledCourse = parts[0];
                String courseNumber = parts[1];
                String enrolledUsername = parts[2];
                if (enrolledUsername.equals(username)) {
                    System.out.println(enrolledCourse + " - " + courseNumber);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No courses found for " + username);
            }
        } catch (FileNotFoundException e) {
    
        }
    }

    public static void viewMyStudents() {
        System.out.println("View Students");
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter course title: ");
            String courseTitle = input.nextLine();
            System.out.print("Enter course number: ");
            String courseNumber = input.nextLine();
            
            File file = new File("Student_course_enrollment.csv");
            if (!file.exists()) {
                System.out.println("No students exist in courses.");
                return;
            }
            
            Scanner fileReader = new Scanner(file);
            boolean found = false;
            
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                if (parts.length > 2 && parts[0].equals(courseTitle) && parts[1].equals(courseNumber)) {
                    found = true;
                    System.out.println("Student enrolled in course " + courseTitle + " - " + courseNumber + ": " + parts[2]);
                }
            }
            
            fileReader.close();
            
            if (!found) {
                System.out.println("No students enrolled in course " + courseTitle + " - " + courseNumber + ".");
            }
            
        } catch (FileNotFoundException e) {
            
        }
    }    

    public static boolean checkCourseExists(String courseTitle, String courseNumber) {
        try (Scanner fileReader = new Scanner(new File("courses.csv"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                String fileCourseTitle = parts[0].trim();
                String fileCourseNumber = parts[1].trim(); 
                if (fileCourseTitle.equalsIgnoreCase(courseTitle) && fileCourseNumber.equals(courseNumber)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            
        }
        return false;
    }

    public static void CourseEnrolled(String username) {
        System.out.println("Enrolled courses");
        System.out.println("Courses enrolled by " + username + ":");
        try (Scanner fileReader = new Scanner(new File("course_enrollment.csv"))) {
            boolean found = false;
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                String enrolledUsername = parts[2].trim();
                String enrolledCourseTitle = parts[0].trim();
                String enrolledCourseNumber = parts[1].trim();
                if (enrolledUsername.equals(username)) {
                    System.out.println("Course Title: " + enrolledCourseTitle + ", Course Number: " + enrolledCourseNumber);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No courses found for " + username);
            }
        } catch (FileNotFoundException e) {
            
        }
    }

    public static boolean checkEnrollmentExists(String courseTitle, String courseNumber, String username) {
        try (Scanner fileReader = new Scanner(new File("course_enrollment.csv"))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals(courseTitle) && parts[1].equals(courseNumber) && parts[2].equals(username)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return false;
    }  
}
