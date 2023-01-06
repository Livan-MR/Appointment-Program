package DBAccess;

import DBUtility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods that will get Contact information from database.
 * @author Livan Martinez
 */
public class DBContact {

    /**
     * Get a list of all contacts in database.
     * @return All Contacts list
     */
    public static ObservableList<Contact> getAllContacts() {

        ObservableList<Contact> AllContacts = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * from contacts";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int contactID = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact contact = new Contact(contactID, contactName, email);
                AllContacts.add(contact);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return AllContacts;
    }

}
