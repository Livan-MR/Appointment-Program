package controller;

import DBAccess.DBAppointment;
import DBAccess.DBContact;
import DBUtility.DBConnection;
import DBUtility.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller for the Add Appointment form
 * @author Livan Martinez
 */
public class AddAppointmentController implements Initializable {

    /**
     * Initializing text fields
     */
    @FXML private TextField AppointmentID;
    @FXML private TextField UserID;
    @FXML private TextField CustomerID;
    @FXML private TextField Title;
    @FXML private TextField Description;
    @FXML private TextField Location;

    /**
     * Initializing combo boxes
     */
    @FXML private ComboBox<model.Contact> Contact;
    @FXML private ComboBox Type;
    @FXML private ComboBox StartHour;
    @FXML private ComboBox StartMinute;
    @FXML private ComboBox EndHour;
    @FXML private ComboBox EndMinute;

    /**
     * Initializing date pickers
     */
    @FXML private DatePicker StartDate;
    @FXML private DatePicker EndDate;

    /**
     * Initializing buttons
     */
    @FXML private Button SaveButton;
    @FXML private Button CancelButton;

    /**
     * Items to fill combo boxes
     */
    ObservableList<Integer> hours = FXCollections.observableArrayList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23);
    ObservableList<Integer> minutes = FXCollections.observableArrayList(0,15,30,45);
    ObservableList<String> typelist = FXCollections.observableArrayList("Planning Session", "De-Briefing");

    /**
     * Obtains customer id from selected customer on main menu screen and sets text to customer id field.
     */
    private Customer selectedCustomer;
    public void AppointmentData (Customer product) {
        selectedCustomer = product;
        CustomerID.setText(String.valueOf(selectedCustomer.getCustomerID()));
        UserID.setText("2");
    }

    /**
     * Check to see if any field in form is empty
     * @return boolean
     */
    public boolean checkEmpty() {
        if (Title.getText().isEmpty() || Description.getText().isEmpty() || Location.getText().isEmpty() || Type.getValue() == null || Contact.getValue() == null || StartDate.getValue() == null || EndDate.getValue() == null|| StartHour.getValue() == null || StartMinute.getValue() == null || EndHour.getValue() == null || EndMinute.getValue() == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Runs a series of checks and then takes the info from form to create new Appointment.
     * First check for empty field and return alert if empty field found.
     * Then take info from form; UserID, CustomerID, title, location, contact, type.
     * For Start time first take info from date picker as Local Date time. Then take info from time combo boxes as strings.
     * if hour or minute info length is less than 2 add 0 to start of string.
     * Then combine hour and minute into Local time and format to 24 hour military time.
     * Then combine Date and Time info as Timestamp. Repeat process to get End time info.
     * Then check appointment list for overlapping appointments using method from DBAppointment class.
     * Using given business hours check to see if Start and End time are within business hours.
     * If all test pass, convert Start and End time to UTC and use sql statement to create new appointment with provided info from form.
     * Return to Main menu Screen.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void pressSaveButton (ActionEvent event) throws IOException, SQLException {
        if (checkEmpty()) {
            Error(1);
        } else {
            int appointmentID = 0;
            int userID = 0;
            int customerID = 0;
            String title = Title.getText();
            String description = Description.getText();
            String location = Location.getText();
            String contact = String.valueOf(Contact.getValue());
            String type = String.valueOf(Type.getValue());
            LocalDate startDate = StartDate.getValue();
            LocalDate endDate = EndDate.getValue();
            String startHour = String.valueOf(StartHour.getValue());
            String startMinute = String.valueOf(StartMinute.getValue());
            String endHour = String.valueOf(EndHour.getValue());
            String endMinute = String.valueOf(EndMinute.getValue());
            if (startHour.length() < 2) startHour = "0" + startHour;
            if (startMinute.length() < 2) startMinute = "0" + startMinute;
            if (endHour.length() < 2) endHour = "0" + endHour;
            if (endMinute.length() < 2) endMinute = "0" + endMinute;
            LocalTime StartTime = LocalTime.parse(startHour + ":" + startMinute, DateTimeFormatter.ISO_TIME);
            Timestamp Start = Timestamp.valueOf(LocalDateTime.of(startDate, StartTime));
            LocalTime EndTime = LocalTime.parse(endHour + ":" + endMinute, DateTimeFormatter.ISO_TIME);
            Timestamp End = Timestamp.valueOf(LocalDateTime.of(endDate, EndTime));
            int contactID = 0;
            userID = Integer.parseInt(UserID.getText());
            customerID = Integer.parseInt(CustomerID.getText());

            ZonedDateTime starBusinessEst = ZonedDateTime.of(startDate.getYear(), startDate.getMonthValue(), startDate.getDayOfMonth(), 8, 0, 0, 0, ZoneId.of("America/New_York"));
            ZonedDateTime endBusinessEst = ZonedDateTime.of(endDate.getYear(), endDate.getMonthValue(), endDate.getDayOfMonth(), 22, 0, 0, 0, ZoneId.of("America/New_York"));
            ZonedDateTime businessStart = starBusinessEst.withZoneSameInstant(ZoneId.systemDefault());
            ZonedDateTime businessEnd = endBusinessEst.withZoneSameInstant(ZoneId.systemDefault());
            String startDay = String.valueOf(startDate.getDayOfWeek());
            String endDay = String.valueOf(endDate.getDayOfWeek());

            Appointment overlap = DBAppointment.newOverlap(Start, End, customerID);
            if (overlap != null) {
                Error(3);
            } else if (StartTime.isBefore(LocalTime.from(businessStart)) || EndTime.isAfter(LocalTime.from(businessEnd)) || startDay == "SATURDAY" || startDay == "SUNDAY" || endDay == "SATURDAY" || endDay == "SUNDAY" ) {
                Error(2);
            } else {
                try {
                    if (contact.equals("Anika Costa")) {
                        contactID = 1;
                    }
                    if (contact.equals("Daniel Garcia")) {
                        contactID = 2;
                    }
                    if (contact.equals("Li Lee")) {
                        contactID = 3;
                    }

                    Appointment newAppointment = new Appointment(appointmentID, title, description, location, contact, type, Start, End, customerID, userID, contactID);

                    Connection connection = DBConnection.getConnection();

                    DBQuery.setPreparedStatement(connection);
                    Statement statement = DBQuery.getStatement();

                    String insertStatement = "INSERT INTO  appointments(Title, Description, Location, Type, Start, End, Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                            "VALUES('" + title + "', '" + description + "', '" + location + "', '" + type + "', '" + DBQuery.LocaltoUTC(Start) + "', '" + DBQuery.LocaltoUTC(End) + "' , '" + userID + "' , '" + userID + "' , '" + customerID + "' , '" + userID + "' , '" + contactID + "')";

                    statement.execute(insertStatement);

                    if (statement.getUpdateCount() > 0)
                        System.out.println("Created Appointment Successfully");
                    else
                        System.out.println("Error Creating Appointment");

                    MainMenuController.mainMenu(event);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Returns to main menu when button pressed
     * @param event
     * @throws IOException
     */
    public void pressCancelButton (ActionEvent event) throws IOException {
        MainMenuController.mainMenu(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Set items in combo boxes
         */
        StartHour.setItems(hours);
        StartMinute.setItems(minutes);
        EndHour.setItems(hours);
        EndMinute.setItems(minutes);
        Type.setItems(typelist);
        Contact.setItems(DBContact.getAllContacts());
    }

    /**
     * Alert messages for failed tests
     * @param Error
     */
    private void Error(int Error) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        switch (Error) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Empty Field");
                alert.setContentText("One or more fields are empty");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Invalid Time");
                alert.setContentText("Appointment outside business hours.");
                alert.showAndWait();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Overlapping");
                alert.setContentText("Found overlapping appointment. Please adjust time.");
                alert.showAndWait();
                break;
        }
    }
}
