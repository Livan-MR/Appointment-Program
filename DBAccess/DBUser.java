package DBAccess;

import DBUtility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods that will get User information from database.
 * @author Livan Martinez
 */
public class DBUser {

    /**
     * Get a list of all user data in database
     * @return
     * @throws SQLException
     */
    public static ObservableList<User> getAllUsers() throws SQLException {

        ObservableList<User> AllUsers = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from users";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int userID = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String userPassword = rs.getString("Password");
                User user = new User(userID, userName, userPassword);
                AllUsers.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return AllUsers;
    }

    /**
     * Will obtain all data from Users.
     * If specified Username and Password match then return true.
     * If specified Username and Password do not match then return false.
     * @param username
     * @param password
     * @return boolean for matching Username and Password
     */
    public static boolean ValidateUser(String username, String password) {

        try {

            String sql = "SELECT * from users";

            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (resultSet.getString("User_Name").equals(username) && resultSet.getString("Password").equals(password))

                    return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }
}
