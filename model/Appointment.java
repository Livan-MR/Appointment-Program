package model;

import java.sql.Timestamp;


/**
* Model for Appointments
 * @author Livan Martinez
 */
public class Appointment {

    private int AppointmentID;
    private String Title;
    private String Description;
    private String Location;
    private String Contact;
    private String Type;
    private Timestamp Start;
    private Timestamp End;
    public int CustomerID;
    public int UserID;
    public int ContactID;

    public Appointment(int appointmentID, String tittle, String description, String location, String contact, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        this.AppointmentID = appointmentID;
        this.Title = tittle;
        this.Description = description;
        this.Location = location;
        this.Contact = contact;
        this.Type = type;
        this.Start = start;
        this.End = end;
        this.CustomerID = customerID;
        this.UserID = userID;
        this.ContactID = contactID;
    }

    /**
     * @return the Appointment ID
     */
    public int getAppointmentID() {
        return AppointmentID;
    }

    /**
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        AppointmentID = appointmentID;
    }

    /**
     * @return the Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        Title = title;
    }

    /**
     * @return The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * @return the Location
     */
    public String getLocation() {
        return Location;
    }

    /**
     * @param location
     */
    public void setLocation(String location) {
        Location = location;
    }

    /**
     * @return the Contact
     */
    public String getContact() {
        return Contact;
    }

    /**
     * @param contact
     */
    public void setContact(String contact) {
        Contact = contact;
    }

    /**
     * @return the Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        Type = type;
    }

    /**
     * @return the Start
     */
    public Timestamp getStart() {
        return this.Start;
    }

    /**
     * @param start
     */
    public void setStart(Timestamp start) {
        Start = start;
    }

    /**
     * @return the end
     */
    public Timestamp getEnd() {
        return End;
    }

    /**
     * @param end
     */
    public void setEnd(Timestamp end) {
        End = end;
    }

    /**
     * @return the Customer ID
     */
    public int getCustomerID() {
        return CustomerID;
    }

    /**
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    /**
     * @return the User ID
     */
    public int getUserID() {
        return UserID;
    }

    /**
     * @param userID
     */
    public void setUserID(int userID) {
        UserID = userID;
    }

    /**
     * @return the Contact ID
     */
    public int getContactID() {
        return ContactID;
    }

    /**
     * @param contactID
     */
    public void setContactID(int contactID) {
        ContactID = contactID;
    }

    /**
     * Makes items in Combo boxes legible
     * @return AppointmentID and Start
     */
    public String toString() {
        return ("ID: " + this.AppointmentID + " ")  + (" Time: " + this.Start) + (" Type: " + this.Type);
    }
}
