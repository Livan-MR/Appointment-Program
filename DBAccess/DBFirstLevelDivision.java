package DBAccess;

import DBUtility.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods that will get First Level Division information from database.
 * @author Livan Martinez
 */
public class DBFirstLevelDivision {

    /**
     * Get a list of all first level division data in database
     * @return All First Level Division list
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivision> getAllFirstLevelDivisions() throws SQLException {

        ObservableList<FirstLevelDivision> AllFirstLevelDivision = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int divisionID = rs.getInt("Division_ID");
                String divisionName = rs.getString("Division");
                int country_ID = rs.getInt("COUNTRY_ID");
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divisionID, divisionName, country_ID);
                AllFirstLevelDivision.add(firstLevelDivision);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return AllFirstLevelDivision;
    }
}
