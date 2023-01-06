package model;

public class User {

    public int UserID;
    public String Username;
    public String Password;

    public User(int userID, String username, String password) {
        this.UserID = userID;
        this.Username = username;
        this.Password = password;
    }

    /**
     * @return the User ID
     */
    public int getUserID() {
        return UserID;
    }

    /**
     * @param userID
     */
    public void setUserID(int userID) {
        UserID = userID;
    }

    /**
     * @return the Username
     */
    public String getUsername() {
        return Username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        Username = username;
    }

    /**
     * @return the Password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        Password = password;
    }
}
