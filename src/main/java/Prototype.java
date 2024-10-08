import java.sql.*;
import java.util.Scanner;


public class Prototype {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void main(String[] args) throws ClassNotFoundException {
        Scanner s = new Scanner(System.in);
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://172.26.114.217:3306/yarkindb", "yarkin", "1234");
            System.out.println(ANSI_GREEN + "Database Connected!" + ANSI_RESET);

            PreparedStatement userStatement = con.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?);");
            System.out.println("Enter Info:");
            userStatement.setString(1, s.nextLine());
            userStatement.setString(2, s.nextLine());

            userStatement.executeUpdate();


            PreparedStatement getUsers = con.prepareStatement("select uid, uid, username, password from user;");
            ResultSet getUsersResultSet = getUsers.executeQuery();
            while (getUsersResultSet.next()) {
                int data = getUsersResultSet.getInt("uid");
                System.out.println(data);
                System.out.println(getUsersResultSet.getString(2));
                System.out.println(getUsersResultSet.getString(3));


            }


        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(ANSI_RED+"Duplicate username not allowed!"+ANSI_RESET);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
