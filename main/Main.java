package main;

import DBUtility.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Class that creates program
 * @author Livan Martinez
 */
public class Main extends Application {

    /**
     * This method will call for the Login View when the program is launched
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        primaryStage.setTitle("Appointment Application");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This will open a connection to the Database and launch the Login View by using previous method.
     * When program is closed the connection to the database will also close.
     * @param args
     */
    public static void main(String[] args) {
    //Locale.setDefault(new Locale("FR"));
    DBConnection.startConnection();
    launch(args);
    DBConnection.closeConnection();
    }
}
