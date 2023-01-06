package DBAccess;

import DBUtility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods that will get Customer information from database.
 * @author Livan Martinez
 */
public class DBCustomer {

    /**
     * Get a list of all customer data in the database
     * @return Customer List
     */
    public static ObservableList<Customer> getAllCustomers() {

        ObservableList<Customer> CustomerList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Division, first_level_divisions.Country_ID, Country\n" +
            "from customers, first_level_divisions\n" +
            "join countries on first_level_divisions.Country_ID = countries.country_id\n" +
            "where customers.Division_ID = first_level_divisions.Division_ID;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int custID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_code");
                String phone = rs.getString("Phone");
                String division = rs.getString("Division");
                String country = rs.getString("Country");
                int divID = rs.getInt("Division_ID");
                Customer customers = new Customer(custID, name, address, postal, phone, division, country, divID);
                CustomerList.add(customers);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

            return CustomerList;
        }

    /**
     * Look for a specified country id in a list of customers. if specified customer id found return list of customers with specified country id
     * @param countryID
     * @return Country list
     */
    public static ObservableList<Customer> getCountry(int countryID) {

        ObservableList<Customer> CountryList = FXCollections.observableArrayList();

        for (Customer customer : getAllCustomers()) {
            if (countryID == 1) {
                if (customer.getDivisionID() <= 54) {
                    CountryList.add(customer);
                }
            }
            if (countryID == 2) {
                if (customer.getDivisionID() <= 104 && customer.getDivisionID() > 100) {
                    CountryList.add(customer);
                }
            }
            if (countryID == 3) {
                if (customer.getDivisionID() < 72 && customer.getDivisionID() > 59) {
                    CountryList.add(customer);
                }
            }
        }
        return CountryList;
    }

    /**
     * Look for a specified division id in a list of customers. if specified division id found return list of customers with specified country id
     * @param divisionID
     * @return Division list
     */
    public static ObservableList<Customer> getDivision(int divisionID) {

        ObservableList<Customer> DivisionList = FXCollections.observableArrayList();

        for (Customer customer : getAllCustomers()) {
            if (customer.getDivisionID() == divisionID) {
                DivisionList.add(customer);
            }
        }
        return DivisionList;
    }
}
