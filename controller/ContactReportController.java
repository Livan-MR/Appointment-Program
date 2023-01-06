package controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

/**
 * Controller for the Appointment Report
 * @author Livan Martinez
 */
public class ContactReportController implements Initializable {

    /**
     * Initialize combo box
     */
    @FXML private ComboBox<model.Contact> ContactBox;

    /**
     * Initialize button
     */
    @FXML private Button MainMenu;

    /**
     * Initialize appointment table
     */
    @FXML private TableView<Appointment> AppointmentTable;
    @FXML private TableColumn<Appointment, Integer> AppointmentIDColumn;
    @FXML private TableColumn<Appointment, String> TitleColumn;
    @FXML private TableColumn<Appointment, String> DescriptionColumn;
    @FXML private TableColumn<Appointment, String> LocationColumn;
    @FXML private TableColumn<Appointment, String> ContactColumn;
    @FXML private TableColumn<Appointment, String> TypeColumn;
    @FXML private TableColumn<Appointment, ZonedDateTime> StartColumn;
    @FXML private TableColumn<Appointment, ZonedDateTime> EndColumn;
    @FXML private TableColumn<Appointment, Integer> AppCustomerIDColumn;
    @FXML private TableColumn<Appointment, Integer> UserIDColumn;

    /**
     * When contact chosen from combo box the table will be filled with appointments that contain chosen contact's name
     * @param event
     */
    public void ContactChosen(ActionEvent event) {
        String contact = String.valueOf(ContactBox.getValue());
        int contactID = 0;

        if (contact.equals("Anika Costa")) {
            contactID = 1;
        }
        if (contact.equals("Daniel Garcia")) {
            contactID = 2;
        }
        if (contact.equals("Li Lee")) {
            contactID = 3;
        }
        ObservableList<Appointment> appointmentList = DBAppointment.getContacts(contactID);
        AppointmentTable.setItems(appointmentList);
    }

    /**
     * Return to main menu when button pressed
     * @param event
     * @throws IOException
     */
    public void returnMainMenu(ActionEvent event) throws IOException {
        MainMenuController.mainMenu(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**
         * Sets items for combo box
         */
        ContactBox.setItems(DBContact.getAllContacts());

        /**
         * Initialize appointment table columns
         */
        AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        LocationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        ContactColumn.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        StartColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        EndColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        AppCustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        UserIDColumn.setCellValueFactory(new PropertyValueFactory<>("UserID"));
    }
}