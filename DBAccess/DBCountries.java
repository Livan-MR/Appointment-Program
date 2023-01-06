package DBAccess;

import DBUtility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods that will get Country information from database.
 * @author Livan Martinez
 */
public class DBCountries {

    /**
     * Get a list of all county data in database.
     * @return All Countries list
     * @throws SQLException
     */
    public static ObservableList<Countries> getAllCountries() throws SQLException {

        ObservableList<Countries> AllCountries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Countries country = new Countries(countryID, countryName);
                AllCountries.add(country);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return AllCountries;
    }
}
