package controller;

import DBAccess.DBCountries;
import DBAccess.DBCustomer;
import DBAccess.DBFirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Countries;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the Location Report
 * @author Livan Martinez
 */
public class LocationReportController implements Initializable {

    /**
     * Initialize combo box
     */
    @FXML private ComboBox CountryBox;
    @FXML private ComboBox DivisionBox;

    /**
     * Initialize button
     */
    @FXML private Button MainMenu;

    /**
     * Initialize customer table
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
     * interface for lambda
     * This lambda is used multiple times and by reusing this interface will save lines of code.
     */
    interface ListToString {
        String list(ObservableList s);
    }

    /**
     * return to main menu when button pressed
     * @param event
     * @throws IOException
     */
    public void returnMainMenu(ActionEvent event) throws IOException {
        MainMenuController.mainMenu(event);
    }

    /**
     * Fills table with customers that contain chosen country. Then enables division filter combo box and fills items with divisions based on the country chosen.
     * @param event
     */
    public void ChooseCountry(ActionEvent event) {
        Countries selectedItem = (Countries) CountryBox.getSelectionModel().getSelectedItem();
        String Country = String.valueOf(CountryBox.getValue());
        int countryID = 0;

        if (Country.equals("U.S")) {
            countryID = 1;
        }
        if (Country.equals("UK")) {
            countryID = 2;
        }
        if (Country.equals("Canada")) {
            countryID = 3;
        }

        try {
            DivisionBox.setItems(DBFirstLevelDivision.getAllFirstLevelDivisions().stream().filter(Division -> Division.getCountryID() ==
                    selectedItem.getId()).collect(Collectors.toCollection(FXCollections::observableArrayList))
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<Customer> CountryList = DBCustomer.getCountry(countryID);
        CustomerTable.setItems(CountryList);
        DivisionBox.setDisable(false);
    }

    /**
     * Use lambda to turn Observable list to string with no brackets.
     * Since this lambda is being reused in other methods it will save line of code and keep a consistent structure.
     * This will make code cleaner and easier to read.
     * Check chosen division and sets division id to each division.
     * Fills table with customers that contain division chosen in combo box.
     * @param event
     * @throws SQLException
     */
    public void ChooseDivision(ActionEvent event) throws SQLException {
        String selectedItem = String.valueOf(DivisionBox.getValue());
        ListToString AllDivs = (ObservableList S) ->  S.toString().substring(1,S.toString().length()-1);
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

        ObservableList<Customer> DivisionList = DBCustomer.getDivision(divisionID);
        CustomerTable.setItems(DivisionList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Disables division combo box until a country is chosen
         */
        DivisionBox.setDisable(true);

        /**
         * Initialize customer table columns
         */
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        AddressColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
        PostalColumn.setCellValueFactory(new PropertyValueFactory<>("Postal"));
        PhoneColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
        DivisionColumn.setCellValueFactory(new PropertyValueFactory<>("Division"));
        CountryColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));
        DivisionIDColumn.setCellValueFactory(new PropertyValueFactory<>("DivisionID"));

        /**
         * Sets items in country combo box
         */
        try {
            CountryBox.setItems(DBCountries.getAllCountries());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
