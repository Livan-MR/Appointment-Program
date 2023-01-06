package controller;

import DBAccess.DBAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AppointmentReport;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Appointment Report
 * @author Livan Martinez
 */
public class AppointmentReportController implements Initializable {

    /**
     * Initialize combo box
     */
    @FXML private ComboBox MonthBox;

    /**
     * Initialize report table
     */
    @FXML private TableView<AppointmentReport> ReportsTable;
    @FXML private TableColumn<AppointmentReport, String> TypeColumn;
    @FXML private TableColumn<AppointmentReport, Integer> AmountColumn;

    /**
     * items to fill combo box with
     */
    ObservableList<String> Months = FXCollections.observableArrayList("January", "February", "March",
            "April", "May", "June", "July", "August", "September", "October", "November", "December");

    /**
     * Sets month id for each month. Will use month id and display appointments by selected month and show count of each type.
     * @param event
     */
    public void ChooseMonth(ActionEvent event) {
        int monthID = 0;
        String month = String.valueOf(MonthBox.getValue());

        if (month.equals("January")){
            monthID = 1;
        }
        if (month.equals("February")){
            monthID = 2;
        }
        if (month.equals("March")){
            monthID = 3;
        }
        if (month.equals("April")) {
            monthID = 4;
        }
        if (month.equals("May")){
            monthID = 5;
        }
        if (month.equals("June")){
            monthID = 6;
        }
        if (month.equals("July")){
            monthID = 7;
        }
        if (month.equals("August")){
            monthID = 8;
        }
        if (month.equals("September")){
            monthID = 9;
        }
        if (month.equals("October")){
            monthID = 10;
        }
        if (month.equals("November")){
            monthID = 11;
        }
        if (month.equals("December")){
            monthID = 12;
        }
        ObservableList<AppointmentReport> Report = DBAppointment.getMonthID(monthID);
        ReportsTable.setItems(Report);

    }

    /**
     * Returns to main menu when button is pressed
     * @param event
     * @throws IOException
     */
    public void returnMainMenu(ActionEvent event) throws IOException {
        MainMenuController.mainMenu(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Sets items for combo box.
         */
        MonthBox.setItems(Months);

        /**
         * Initializes columns for reports table
         */
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        AmountColumn.setCellValueFactory(new PropertyValueFactory<>("Count"));
    }
}
