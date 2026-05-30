import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/mydatabase";
        String userName = "root";
        String password = "Dua@2002";
        String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?;";
        String depositQuery = " UPDATE accounts SET balance = balance + ? WHERE account_number = ?;";

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
            con.setAutoCommit(false); // I won't automatically commit anything , i can do every thing manually commit / Manual transactions

            System.out.print("Enter Withdrawal Account Number : ");
            String withdrawalAccount = sc.nextLine();

            System.out.print("Enter Deposit Account Number : ");
            String depositAccount = sc.nextLine();

            System.out.print("Enter Amount : ");
            double amount = sc.nextDouble();


            try{
                PreparedStatement withdrawStatement = con.prepareStatement(withdrawQuery);
                PreparedStatement depositStatement = con.prepareStatement(depositQuery);
                //Withdraw Query
                withdrawStatement.setDouble(1,amount);
                withdrawStatement.setString(2,withdrawalAccount);
                //Deposit Query
                depositStatement.setDouble(1,amount);
                depositStatement.setString(2,depositAccount);

               int rowAffectedWithdrawal =  withdrawStatement.executeUpdate();
                int rowAffectedDeposit = depositStatement.executeUpdate();
                if(rowAffectedWithdrawal > 0 && rowAffectedDeposit > 0) {
                    con.commit();
                    System.out.println("Transaction Successful.");
                    System.out.println(
                            "INR " + amount +
                                    " has been debited from A/C " + withdrawalAccount +
                                    " and credited to A/C " + depositAccount +
                                    ". Thank you for banking with us."
                    );
                }else{
                    con.rollback();
                    System.out.println("Transaction Failed!");
                }
            }catch (SQLException e){
                System.err.println(e.getMessage());
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}