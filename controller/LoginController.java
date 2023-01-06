package controller;

import DBAccess.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for the Login Screen
 * @author Livan Martinez
 */
public class LoginController implements Initializable {

    /**
     * Initialize Label,Button, and Text fields
     */
    @FXML public Label ZoneIDField;
    @FXML private Button LoginButton;
    @FXML private TextField PasswordField;
    @FXML private TextField UsernameField;

    ZoneId zoneID = ZoneId.systemDefault();

    /**
     * Gets all users and passwords in database and will validate them.
     * If user and password match then login log will be updated accordingly and be redirected to Main menu
     * If user and password do not match or field left empty then login log will be updated accordingly alert will appear.
     * @param event
     * @throws IOException
     */
    public void UserLogin(ActionEvent event) throws IOException {
        ResourceBundle rb = ResourceBundle.getBundle("main/Nat", Locale.getDefault());
        String Username = UsernameField.getText();
        String Password = PasswordField.getText();
        Boolean ValidUser = DBUser.ValidateUser(Username,Password);

        if(ValidUser) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene MainMenuScene = new Scene(root);
            LoginPass(Username);

            window.setScene(MainMenuScene);
            window.show();
        }
        else if (UsernameField.getText().isEmpty() || PasswordField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("Invalid"));
            alert.setContentText(rb.getString("Empty"));
            alert.showAndWait();
            LoginFail(rb.getString("Empty"));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("Invalid"));
            alert.setContentText(rb.getString("Incorrect"));
            alert.showAndWait();
            LoginFail(Username);
        }
    }

    /**
     * Method to update log for a successful login attempt
     * @param Username
     * @throws IOException
     */
    public static void LoginPass(String Username) throws IOException {
        String Filename = "login_activity.txt";
        File file = new File(Filename);
        FileWriter FWriter = new FileWriter(Filename, true);
        PrintWriter PWriter = new PrintWriter(FWriter);

        PWriter.println(Username + " Successful Login: " + Timestamp.valueOf(LocalDateTime.now()));
        PWriter.close();
    }

    /**
     * Method to update log for a unsuccessful login attempt
     * @param Username
     * @throws IOException
     */
    public static void LoginFail(String Username) throws IOException {
        String Filename = "login_activity.txt";
        File file = new File(Filename);
        FileWriter FWriter = new FileWriter(Filename, true);
        PrintWriter PWriter = new PrintWriter(FWriter);

        PWriter.println(Username + " Unsuccessful Login: " + Timestamp.valueOf(LocalDateTime.now()));
        PWriter.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Resource bundle for English and French based on locale default.
         */
        ResourceBundle rb = ResourceBundle.getBundle("main/Nat", Locale.getDefault());

        /**
         * Initialize text for fields based on resource bundle
         */
        ZoneIDField.setText(rb.getString("ZoneID") + zoneID);
        PasswordField.setPromptText(rb.getString("PasswordField"));
        UsernameField.setPromptText(rb.getString("UsernameField"));
        LoginButton.setText(rb.getString("LoginButton"));
    }
}
