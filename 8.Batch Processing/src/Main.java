import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String userName = "root";
        String password = "Dua@2002";

        Scanner sc = new Scanner(System.in);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Load Successfully...");
        }catch (ClassNotFoundException e){
            System.err.println(e.getMessage());
        }

        try{
            Connection con = DriverManager.getConnection(url,userName,password);
            System.out.println("Connection Established Successfully...");
            con.setAutoCommit(false);

            /*
            Statement statement = con.createStatement();
            statement.addBatch("INSERT INTO employees(name,job_title,salary) VALUES('Vishal','Devops Engineer',10000.0)");
            statement.addBatch("INSERT INTO employees(name,job_title,salary) VALUES('Rahul','Cyber Security Analyst',15000.0)");
            statement.addBatch("INSERT INTO employees(name,job_title,salary) VALUES('Rajesh','Web Developer',15000.0)");
            int[] batchResult = statement.executeBatch();
            con.commit();
            System.out.println("Batch Executed Successfully.");
            System.out.printf("BatchResult : " + Arrays.toString(batchResult));

             */

            String query = "INSERT INTO employees(name,job_title,salary) VALUES (?,?,?);";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            while (true){
                System.out.print("Name : ");
                String name = sc.nextLine();
                System.out.print("Job Title : ");
                String job_title = sc.nextLine();
                System.out.print("Salary : ");
                double salary = sc.nextDouble();
                sc.nextLine();

                preparedStatement.setString(1,name);
                preparedStatement.setString(2,job_title);
                preparedStatement.setDouble(3,salary);
                preparedStatement.addBatch();
                System.out.print("Add More Values (Y/N) : ");
                String decision = sc.nextLine();
                if(decision.toUpperCase().equals("N")){
                    break;
                }
            }
            int[] batchResult = preparedStatement.executeBatch();
            con.commit();
            System.out.println("Batch Executed Successfully..");
            System.out.println("Result : " + Arrays.toString(batchResult));

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}