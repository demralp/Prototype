import java.io.Console;
import java.sql.*;
import java.util.Scanner;


public class Prototype {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }
        try {
            Connection con = DatabaseConnector.getConnection();

            System.out.println(ANSI_GREEN + "Database Connected!" + ANSI_RESET);

            PreparedStatement userStatement = con.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?);");
            System.out.println("Enter Username:");
            userStatement.setString(1, s.nextLine());
            System.out.println("Enter Password:");
            char[] passwordArray = console.readPassword("Enter your secret password: ");
            String password = new String(passwordArray);
            userStatement.setString(2, password);
            password = null;
            userStatement.executeUpdate();

            System.out.println("See Database?");
            Scanner in = new Scanner(System.in);
            int uid = 0;
            String selectedUsername = null;
            String selectedPassword = null;
            if (in.nextLine().equals("yes")) {
            PreparedStatement getUsers = con.prepareStatement("select uid, username, password from user;");
            ResultSet getUsersResultSet = getUsers.executeQuery();


                while (getUsersResultSet.next()) {
                uid = getUsersResultSet.getInt("uid");
                selectedUsername = (getUsersResultSet.getString(2));
                selectedPassword = (getUsersResultSet.getString(3));
                }

            System.out.println("User info:");
            System.out.println("UID: " + uid);
            System.out.println("Username: " + selectedUsername);
            System.out.println("Password: " + selectedPassword);




            }else {
                System.out.println("Exiting");
            }


        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(ANSI_RED+"Duplicate username not allowed!"+ANSI_RESET);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
