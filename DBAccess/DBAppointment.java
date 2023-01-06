package DBAccess;

import DBUtility.DBConnection;
import DBUtility.DBQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.AppointmentReport;
import java.sql.*;

/**
 * This class contains methods that will get appointment information from database.
 * @author Livan Martinez
 */
public class DBAppointment {

    /**
     * This class will get all the appointments from the database and use the appointment model to create an observable list that can be used manipulate data.
     * @return Appointment List
     */
    public static ObservableList<Appointment> getAllAppointments() {

        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, appointments.Contact_ID, contacts.Contact_Name, Type, Start, End, Customer_ID, User_ID\n" +
                    "from client_schedule.appointments, contacts\n" +
                    "where appointments.Contact_ID = contacts.Contact_ID;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp start = DBQuery.UTCtoLocal(rs.getTimestamp("Start"));
                Timestamp end = DBQuery.UTCtoLocal(rs.getTimestamp("End"));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appID, title, description, location, contact, type, start, end, customerID, userID, contactID);
                AppointmentList.add(appointment);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return AppointmentList;
    }

    /**
     * This will get all the appointments from the database that are within the next 7 days of current day.
     * @return Appointment List
     */
    public static ObservableList<Appointment> getWeeklyAppointment() {

        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, appointments.Contact_ID, contacts.Contact_Name, Type, Start, End, Customer_ID, User_ID\n" +
                    "from client_schedule.appointments, contacts\n" +
                    "where appointments.Contact_ID = contacts.Contact_ID and Start > curdate() and Start < CURDATE() + interval 7 day;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp start = DBQuery.UTCtoLocal(rs.getTimestamp("Start"));
                Timestamp end = DBQuery.UTCtoLocal(rs.getTimestamp("End"));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                Appointment appointment = new Appointment(appID, title, description, location, contact, type, start, end, customerID, userID, contactID);
                AppointmentList.add(appointment);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return AppointmentList;
    }

    /**
     * This will get all the appointments from the database that are within the next 30 days of current day.
     * @return Appointment List
     */
    public static ObservableList<Appointment> getMonthlyAppointment() {

        ObservableList<Appointment> AppointmentList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, appointments.Contact_ID, contacts.Contact_Name, Type, Start, End, Customer_ID, User_ID\n" +
                    "from client_schedule.appointments, contacts\n" +
                    "where appointments.Contact_ID = contacts.Contact_ID and Start > curdate() and Start < CURDATE() + interval 30 day;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact = rs.getString("Contact_Name");
                String type = rs.getString("Type");
                Timestamp start = DBQuery.UTCtoLocal(rs.getTimestamp("Start"));
                Timestamp end = DBQuery.UTCtoLocal(rs.getTimestamp("End"));
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointment appointment = new Appointment(appID, title, description, location, contact, type, start, end, customerID, userID, contactID);
                AppointmentList.add(appointment);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return AppointmentList;
    }

    /**
     * Will filter appointments by Type and month and give count of each type.
     * @return All Months list
     */
    public static ObservableList<AppointmentReport> getAppointmentReport() {

        ObservableList<AppointmentReport> AllMonths = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Type,count(Title) as Count,MONTH(Start) as Month from client_schedule.appointments group by Type,MONTH(Start) order by Month;";

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);


            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {

                String type = resultSet.getString("Type");
                int count = resultSet.getInt("Count");
                int monthID = resultSet.getInt("Month");

                AppointmentReport report = new AppointmentReport(type, count, monthID);
                AllMonths.add(report);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return AllMonths;
    }

    /**
     * Will search for specified month by its ID example January ID is 1 since it is first month. Will then return list of the desired month.
     * @param monthID
     * @return MonthID List
     */
    public static ObservableList<AppointmentReport> getMonthID(int monthID) {

        ObservableList<AppointmentReport> monthIDS = FXCollections.observableArrayList();

        for (AppointmentReport report : getAppointmentReport()) {
            if (report.getMonth() == monthID) {
                monthIDS.add(report);
            }
        }
        return monthIDS;
    }

    /**
     * Used to search for specified customer ID in a list of appointments then returns list of appointments with matching customer ID.
     * @param customerID
     * @return Customer ID list
     */
    public static ObservableList<Appointment> getCustomerID(int customerID) {

        ObservableList<Appointment> customerIDS = FXCollections.observableArrayList();

        for (Appointment appointments : getAllAppointments()) {
            if (appointments.getCustomerID() == customerID) {
                customerIDS.add(appointments);
            }
        }
        return customerIDS;
    }

    /**
     * Search appointments for specified time. If specified time is between any current appointment times then it will fill list with the list of overlapping appointments.
     * @param start
     * @param end
     * @param customerID
     * @return Overlapping appointment list
     * @throws SQLException
     */
    public static Appointment newOverlap(Timestamp start, Timestamp end, int customerID) throws SQLException {
        Appointment Overlapping = null;

        ObservableList<Appointment> appointmentList = getCustomerID(customerID);

        for (Appointment appointment : appointmentList) {
            if (start.after(appointment.getStart()) && start.before(appointment.getEnd()) || end.after(appointment.getStart()) && end.before(appointment.getEnd()) || start.before(appointment.getStart()) && end.after(appointment.getStart()) || start.equals(appointment.getStart()) && end.equals(appointment.getEnd()) || start.equals(appointment.getStart()) || end.equals(appointment.getStart()) || start.equals(appointment.getStart()) && end.after(appointment.getEnd())) {

                Overlapping = appointment;
            }
        }
        return Overlapping;
    }

    /**
     * Search appointments for specified time. If specified time is between any current appointment times then it will fill list with the list of overlapping appointments.
     * Used to modify appointment. Difference in this one is it will skip current appointment and not count it as an overlapping appointment.
     * This way if any appointment info is changed other than time overlap error will not appear.
     * @param start
     * @param end
     * @param customerID
     * @return
     * @throws SQLException
     */
    public static Appointment modOverlap(Timestamp start, Timestamp end, int customerID) throws SQLException {
        Appointment appointments = null;

        ObservableList<Appointment> appointmentList = getCustomerID(customerID);

        for (Appointment appointment : appointmentList) {
            if (start.after(appointment.getStart()) && start.before(appointment.getEnd()) || end.after(appointment.getStart()) && end.before(appointment.getEnd()) || start.before(appointment.getStart()) && end.after(appointment.getStart()) || end.equals(appointment.getStart()) || start.equals(appointment.getStart()) && end.after(appointment.getEnd())) {

                appointments = appointment;
            }
        }
        return appointments;
    }

    /**
     * Make a list that associates an appointment to specified customer id.
     * Used to check if a specified customer ID has any appointments in appointment list.
     * @param id
     * @return Boolean of Customer ID appearance in Appointment list
     */
    public static boolean associatedAppointment(int id) {
        ObservableList Customer_ID = FXCollections.observableArrayList();

        try {
            String sql = "select Customer_ID from appointments;";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerID = rs.getInt("Customer_ID");
                Customer_ID.add(customerID);
            }
            if (Customer_ID.contains(id)) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Search appointment list for specified contact ID. If specified contact ID found return list of appointments with specified contact ID.
     * @param Contact
     * @return Contact list
     */
    public static ObservableList<Appointment> getContacts(int Contact) {

        ObservableList<Appointment> contactList = FXCollections.observableArrayList();

        for (Appointment appointments : getAllAppointments()) {
            if (appointments.getContactID() == Contact) {
                contactList.add(appointments);
            }
        }
        return contactList;
    }

}
