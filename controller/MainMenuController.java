package controller;

import DBAccess.DBAppointment;
import DBAccess.DBCustomer;
import DBUtility.DBConnection;
import DBUtility.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for the Main Menu form
 * @author Livan Martinez
 */
public class MainMenuController implements Initializable {

    /**
     * Initialize Buttons
     */
    @FXML private Button AppointmentReport;
    @FXML private Button ContactReport;
    @FXML private Button LocationReport;
    @FXML private Button AddCustomer;
    @FXML private Button ModifyCustomer;
    @FXML private Button AddAppointment;
    @FXML private Button DeleteCustomer;
    @FXML private Button DeleteAppointment;
    @FXML private Button ModifyAppointment;

    /**
     * Initialize Radio Buttons
     */
    @FXML private RadioButton allApps;
    @FXML private RadioButton weekApps;
    @FXML private RadioButton monthApps;
    @FXML private ToggleGroup timeApps;

    /**
     * Initialize Label
     */
    @FXML private Label PendingLabel;

    /**
     * Initialize Customer table
     */
    @FXML private TableView<Customer> CustomerTable;
    @FXML private TableColumn<Customer, Integer> CustomerIDColumn;
    @FXML private TableColumn<Customer, String> NameColumn;
    @FXML private TableColumn<Customer, String> AddressColumn;
    @FXML private TableColumn<Customer, String> PostalColumn;
    @FXML private TableColumn<Customer, String> PhoneColumn;
    @FXML private TableColumn<Customer, String> DivisionColumn;
    @FXML private TableColumn<Customer, String> CountryColumn;
    @FXML private TableColumn<Customer, Integer> DivisionIDColumn;

    /**
     * Initialize Appointment table
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
     * Method used throughout program to return to main menu form
     * @param event
     * @throws IOException
     */
    public static void mainMenu(ActionEvent event) throws IOException{
        Parent addPartParent = FXMLLoader.load(MainMenuController.class.getResource("/view/MainMenu.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
    }

    /**
     * Creates a list of all appointments. Create a list of pending appointments.
     * If any appointment starts within the next 15 minutes add appointment to pending appointment list.
     * Use lambda to turn list to string.
     * Since this lambda is being reused in other methods it will save line of code and keep a consistent structure.
     * This will make code cleaner and easier to read.
     * Set massage to display the pending appointments which will be used to change label text.
     * @return message
     */
    public String pendingAppointment() {
        ObservableList<Appointment> allAppointments = DBAppointment.getAllAppointments();
        ObservableList<Appointment> pendingAppointments = FXCollections.observableArrayList();
        LocationReportController.ListToString allPending = (ObservableList S) -> S.toString().substring(1,S.toString().length()-1);

        for (Appointment appointment : allAppointments) {
            LocalDateTime AppointmentTime = appointment.getStart().toLocalDateTime();
            LocalDateTime CurrentTime = Timestamp.from(Instant.now()).toLocalDateTime();

            if (AppointmentTime.isBefore(CurrentTime.plusMinutes(16)) && AppointmentTime.isAfter(CurrentTime)) {
                pendingAppointments.add(appointment);
            }
        }
        String message = allPending.list(pendingAppointments);

        if (pendingAppointments.isEmpty()) {
            message = "No Pending Appointments";
        } else {
            message = "Appointment Soon " + message;
        }

        return message;
    }

    /**
     * Redirects to add customer form
     * @param event
     * @throws IOException
     */
    public void AddCustomer(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene addCustomer = new Scene(root);

        window.setScene(addCustomer);
        window.show();
    }

    /**
     * If no customer is currently selected alert will appear.
     * If customer is selected then redirects to modify customer form.
     * Passes selected customer info to modify customer form
     * @param event
     * @throws IOException
     */
    public void ModifyCustomer(ActionEvent event) throws IOException {
        Customer selectedProduct = CustomerTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select a Customer");
            alert.showAndWait();
        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            Parent tableViewParent = loader.load();
            Scene tableViewScene = new Scene(tableViewParent);

            ModifyCustomerController controller = loader.getController();
            controller.customerData(CustomerTable.getSelectionModel().getSelectedItem());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }

    /**
     * If no customer selected alert will appear.
     * If customer is selected then will check to see if customer id appears in appointments
     * If customer id is not found customer will be deleted.
     * If customer id is found then alert appears warning about associated appointment and ask if you want to delete both customer and appointments.
     * When ok selected both customer and appointments deleted. If cancel selected customer and appointments remain unchanged.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void DeleteCustomer(ActionEvent event) throws IOException, SQLException {
        Customer selectedCustomer = CustomerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select a Customer");
            alert.showAndWait();
        } else {
            int CustomerID = selectedCustomer.getCustomerID();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Do you want to delete customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (DBAppointment.associatedAppointment(CustomerID)) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Appointment Found");
                    alert.setContentText("Associated appointment found. Press OK to Delete appointment and customer");
                    Optional<ButtonType> choice = alert.showAndWait();
                    if (choice.isPresent() && choice.get() == ButtonType.OK) {
                        Connection connection = DBConnection.getConnection();
                        DBQuery.setPreparedStatement(connection);
                        Statement statement = DBQuery.getStatement();
                        String deleteStatement = "Delete FROM customers where Customer_ID= " + CustomerID + "";
                        System.out.println("Deleted customer and associated appointments");
                        statement.execute(deleteStatement);
                        CustomerTable.setItems(DBCustomer.getAllCustomers());
                        AppointmentTable.setItems(DBAppointment.getAllAppointments());
                    }
                } else {
                    Connection connection = DBConnection.getConnection();
                    DBQuery.setPreparedStatement(connection);
                    Statement statement = DBQuery.getStatement();
                    String deleteStatement = "Delete FROM customers where Customer_ID= " + CustomerID + "";
                    System.out.println("Deleted customer successfully");
                    statement.execute(deleteStatement);
                    CustomerTable.setItems(DBCustomer.getAllCustomers());
                }
            }
        }
    }

    /**
     * If no customer is selected then alert appears.
     * If customer is selected then redirects to add appointment form.
     * Customer id is passed to add appointment field.
     * @param event
     * @throws IOException
     */
    public void AddAppointment(ActionEvent event) throws IOException {
        Customer selectedAppointment = CustomerTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select the customer to add an appointment!");
            alert.showAndWait();
        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/AddAppointment.fxml"));
            Parent tableViewParent = loader.load();
            Scene tableViewScene = new Scene(tableViewParent);

            AddAppointmentController controller = loader.getController();
            controller.AppointmentData(CustomerTable.getSelectionModel().getSelectedItem());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }

    /**
     * If no appointment is currently selected alert will appear.
     * If appointment is selected then redirects to modify appointment form.
     * Passes selected appointment info to modify customer form
     * @param event
     * @throws IOException
     */
    public void ModifyAppointment(ActionEvent event) throws IOException {
        Appointment selectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select an Appointment");
            alert.showAndWait();
        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            Parent tableViewParent = loader.load();
            Scene tableViewScene = new Scene(tableViewParent);

            ModifyAppointmentController controller = loader.getController();
            controller.AppointmentData(AppointmentTable.getSelectionModel().getSelectedItem());

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
    }

    /**
     * If no appointment selected alert will appear.
     * If appointment is selected then selected appointment will be deleted.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    public void DeleteAppointment(ActionEvent event) throws IOException, SQLException {
        Appointment selectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();


        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("None Selected");
            alert.setContentText("Select an appointment");
            alert.showAndWait();
        } else {
            int AppointmentID = selectedAppointment.getAppointmentID();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Do you want to delete Appointment # " + selectedAppointment.getAppointmentID() + " of " + selectedAppointment.getType() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    Connection connection = DBConnection.getConnection();
                    DBQuery.setPreparedStatement(connection);
                    Statement statement = DBQuery.getStatement();
                    String deleteStatement = "Delete FROM client_schedule.appointments where Appointment_ID= " + AppointmentID + "";
                    System.out.println("Deleted Successfully " + selectedAppointment);
                    statement.execute(deleteStatement);
                    AppointmentTable.setItems(DBAppointment.getAllAppointments());
                } catch (SQLException throwables) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Deleted");
                    alert.setContentText("Appointment Deletion Unsuccessful");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * Selected by default.
     * Fills appointment table with all appointments
     * @param event
     */
    public void ViewAllApps(ActionEvent event) {
    AppointmentTable.setItems(DBAppointment.getAllAppointments());
    }

    /**
     * If radio button selected fills appointment table with appointments in the next 7 days
     * @param event
     */
    public void ViewMonthlyApps(ActionEvent event) {
    AppointmentTable.setItems(DBAppointment.getMonthlyAppointment());
    }

    /**
     * If radio button selected fills appointment table with appointments in the next 30 days
     * @param event
     */
    public void ViewWeeklyApps(ActionEvent event) {
    AppointmentTable.setItems(DBAppointment.getWeeklyAppointment());
    }

    /**
     * Redirects to contact report
     * @param event
     * @throws IOException
     */
    public void pressContactReport(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ContactReport.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene ContactReport = new Scene(root);

        window.setScene(ContactReport);
        window.show();
    }

    /**
     * Redirects to appointment report
     * @param event
     * @throws IOException
     */
    public void pressAppReport(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AppointmentReport.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene AppointmentReport = new Scene(root);

        window.setScene(AppointmentReport);
        window.show();
    }

    /**
     * Redirects to Location report
     * @param event
     * @throws IOException
     */
    public void pressLocationReport(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LocationReport.fxml"));
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene LocationReport = new Scene(root);

        window.setScene(LocationReport);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Sets text for label based on pending appointments.
         */
        PendingLabel.setText(pendingAppointment());

        /**
         * Sets items for Customer Table
         */
        CustomerTable.setItems(DBCustomer.getAllCustomers());
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        PostalColumn.setCellValueFactory(new PropertyValueFactory<>("Postal"));
        PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        DivisionColumn.setCellValueFactory(new PropertyValueFactory<>("Division"));
        CountryColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));
        DivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("DivisionID"));

        /**
         * Initializes columns for appointment table
         */
        AppointmentTable.setItems(DBAppointment.getAllAppointments());
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
