package DBUtility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * This class opens and closes connection with SQL database
 * @author Livan Martinez
 */
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//localhost/";
    private static final String dbName = "client_schedule";
    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress + dbName;
    //driver and connection interface
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";

    /**
     * Starts connection with database. A message will display if connection successful in the terminal.
     * @return
     */
    public static Connection startConnection() {
        System.out.println("Attempting database connection...");
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }


    /**
     * @return
     */
    public static Connection getConnection(){
        return conn;
    }

    /**
     * Closes connection and will display message in the terminal.
     */
    public static void closeConnection () {
        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}