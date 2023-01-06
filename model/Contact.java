package model;

/**
 * Model for Contact
 * @author Livan Martinez
 */
public class Contact {

    private int ContactID;
    private String ContactName;
    private String Email;

    public Contact(int contactID, String contactName, String email) {
        this.ContactID = contactID;
        this.ContactName = contactName;
        this.Email = email;
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
     * @return the Contact Name
     */
    public String getContactName() {
        return ContactName;
    }

    /**
     * @param contactName
     */
    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    /**
     * @return the Email
     */
    public String getEmail() {
        return Email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * Makes items in Combo Boxes legible
     * @return the Contact Name
     */
    public String toString() {
        return (this.ContactName );
    }
}
