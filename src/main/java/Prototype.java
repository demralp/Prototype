import java.sql.*;
import java.util.Scanner;


public class Prototype {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307", "root", "1234");

            PreparedStatement userStatement = con.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?);");
            System.out.println("Enter Info:");
            userStatement.setString(1, s.nextLine());
            userStatement.setString(2, s.nextLine());


            userStatement.executeUpdate();


            PreparedStatement getUsers = con.prepareStatement("select * from user;");
            ResultSet getUsersResultSet = getUsers.executeQuery();
            while (getUsersResultSet.next()) {
                int data = getUsersResultSet.getInt("uid");
                System.out.println(data);
                System.out.println(getUsersResultSet.getString(2));
                System.out.println(getUsersResultSet.getString(3));


            }


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
