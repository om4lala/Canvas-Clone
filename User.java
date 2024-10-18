import java.util.*;
public class User {
    public String name;
	public String username;
	public String password;

    public User(String name, String username, String password){
        this.name = name;
        this.username = username; 
        this.password = password;
    }

    public String getName(){
        return name;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }


    public static void main(String[] args){
        while(true){
            try{
                System.out.println("Choose one of the following: \n1. Admin\n2. Teacher\n3. Student\n4. Exit");
                Scanner myObj = new Scanner(System.in);
                System.out.print("Enter an option: ");
                int input = myObj.nextInt();

                if(input == 1){
                    Admin.adminLogin();
                    break;
                }else if(input == 2){
                    Teacher.teacherLogin();
                    break;
                }else if(input == 3){
                    Student.studentLogin();
                    break;
                }else if(input == 4){
                    System.exit(0);
                }else
                    System.out.println("Invalid input! Try again.");
            }catch(Exception e){
                System.out.println("Invalid input! Try again.");
            }
        }    
    }
}
