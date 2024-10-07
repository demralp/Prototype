import java.sql.*;
import java.util.Scanner;

public class Prototype {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner s = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql:jdbc:mysql://localhost:3307", "root", "1234");

            PreparedStatement userStatement = con.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?, ?);");
            System.out.println("Enter Info:");
            userStatement.setString(1, s.nextLine());
            userStatement.setString(2, s.nextLine());
            userStatement.setString(3, s.nextLine());

            userStatement.executeUpdate();

            PreparedStatement getUsers = con.prepareStatement("select * from user;");
            ResultSet getUsersResultSet = getUsers.executeQuery();
            while (getUsersResultSet.next()) {
                int data_1 = getUsersResultSet.getInt("uid");
                int data_2 = getUsersResultSet.getInt("username");
                int data_3 = getUsersResultSet.getInt("password");
                System.out.println(data_1);
                System.out.println(data_2);
                System.out.println(data_3);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
