package controller;

import DBAccess.DBCountries;
import DBAccess.DBFirstLevelDivision;
import DBUtility.DBConnection;
import DBUtility.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Countries;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the Add Customer form
 * @author Livan Martinez
 */
public class AddCustomerController implements Initializable {

    /**
     * Initialize text fields
     */
    @FXML private TextField Name;
    @FXML private TextField Address;
    @FXML private TextField Postal;
    @FXML private TextField Phone;

    /**
     * Initialize combo boxes
     */
    @FXML private ComboBox Country;
    @FXML private ComboBox State;
    @FXML private TextField DivisionID;

    /**
     * Initialize buttons
     */
    @FXML private Button SaveButton;
    @FXML private Button CancelButton;

    /**
     * Sets items for division combo box based on the Country chosen
     * @param event
     */
    public void selectCountry(ActionEvent event) {

        Countries selectedItem = (Countries) Country.getSelectionModel().getSelectedItem();

        try {
            State.setItems(DBFirstLevelDivision.getAllFirstLevelDivisions().stream().filter(Division -> Division.getCountryID() ==
                    selectedItem.getId()).collect(Collectors.toCollection(FXCollections::observableArrayList))
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        State.getSelectionModel().selectFirst();

    }

    /**
     * Use lambda to turn Observable list to string with no brackets. Lambda justification to save lines of code.
     * Check chosen division and sets division id to each division.
     * Fills table with customers that contain division chosen in combo box.
     * @param event
     * @throws SQLException
     */
    public void selectDivision(ActionEvent event) throws SQLException {
        String selectedItem = String.valueOf(State.getValue());
        LocationReportController.ListToString AllDivs = (ObservableList S) ->  S.toString().substring(1,S.toString().length()-1);
        String list = ", " + AllDivs.list(DBFirstLevelDivision.getAllFirstLevelDivisions());
        String [] Divisions = list.split(", ");
        List<String> division = Arrays.asList(Divisions);
        ArrayList<String> divisionList = new ArrayList<String>(division);
        String temp = selectedItem;
        int divisionID = divisionList.indexOf(temp);
        if (divisionID == 50) {
            divisionID = 52;
        } else if (divisionID == 51) {
            divisionID = 54;
        } else if (divisionID >= 52 && divisionID <= 64 ) {
            divisionID = divisionID + 8;
        } else if (divisionID >= 65) {
            divisionID = divisionID + 36;
        }
        DivisionID.setText(String.valueOf(divisionID));
        }

    /**
     * Check to see if any field in form is empty
     * @return boolean
     */
    public boolean checkEmpty() {
        if (Name.getText().isEmpty() || Address.getText().isEmpty() || Country.getValue() == null || State.getValue() == null || Postal.getText().isEmpty() || Phone.getText().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check to see if any field in form is empty and set alert if empty field found
     * Obtains provided info from the form and creates a new customer. Returns to main menu.
     * @param event
     */
    public void pressSaveButton(ActionEvent event) {
        if (checkEmpty()) {
            Error(1);
        } else {
            try {
                int customerID = 0;
                String name = Name.getText();
                String address = Address.getText();
                String country = String.valueOf(Country.getValue());
                String state = String.valueOf(State.getValue());
                String postal = Postal.getText();
                String phone = Phone.getText();
                int divisionID = Integer.parseInt(DivisionID.getText());
                int userID = 2;


                Customer customer = new Customer(customerID, name, address, postal, phone, state, country, divisionID);


                Connection connection = DBConnection.getConnection();

                DBQuery.setPreparedStatement(connection);
                Statement statement = DBQuery.getStatement();

                String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code , Phone,  Created_By,  Last_Updated_By, Division_ID) " +
                        "VALUES('" + name + "', '" + address + "', '" + postal + "', '" + phone + "', '" + userID + "', '" + userID + "', '" + divisionID + "')";

                statement.execute(insertStatement);

                MainMenuController.mainMenu(event);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return to main menu if button pressed
     * @param event
     * @throws IOException
     */
    public void pressCancelButton(ActionEvent event) throws IOException {
        MainMenuController.mainMenu(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       /**
         * Sets items for country combo box
         */
        try {
            Country.setItems(DBCountries.getAllCountries());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Alert message for failed tests
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
