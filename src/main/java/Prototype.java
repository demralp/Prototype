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
            System.out.println("Couldn't get Freak instance");
            System.exit(0);
        }
        try {
            Connection con = DatabaseConnector.getConnection();

            System.out.println(ANSI_GREEN + "Database Connected!" + ANSI_RESET);

            PreparedStatement freakStatement = con.prepareStatement("INSERT INTO user (username, password) VALUES (?, ?);");
            System.out.println("Enter Freakname:");
            freakStatement.setString(1, s.nextLine());
            System.out.println("Enter Freakword:");
            char[] freakwordArray = console.readPassword("Enter your secret freakword: ");
            String freakword = new String(freakwordArray);
            freakStatement.setString(2, freakword);
            freakword = null;
            freakStatement.executeUpdate();

            System.out.println("See Database?");
            Scanner in = new Scanner(System.in);
            int uid = 0;
            String selectedFreakname = null;
            String selectedFreakword = null;
            if (in.nextLine().equals("yes")) {
            PreparedStatement getFreaks = con.prepareStatement("select uid, username, password from user;");
            ResultSet getFreaksResultSet = getFreaks.executeQuery();


                while (getFreaksResultSet.next()) {
                uid = getFreaksResultSet.getInt("uid");
                selectedFreakname = (getFreaksResultSet.getString(2));
                selectedFreakword = (getFreaksResultSet.getString(3));
                }

            System.out.println("Freak info:");
            System.out.println("UID: " + uid);
            System.out.println("Freakname: " + selectedFreakname);
            System.out.println("Freakword: " + selectedFreakword);




            }else {
                System.out.println("Exiting");
            }


        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(ANSI_RED+"Duplicate freakname not allowed!"+ANSI_RESET);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
