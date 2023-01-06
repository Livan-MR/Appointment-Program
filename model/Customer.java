package model;

public class Customer {

    private int CustomerID;
    private String Name;
    private String Address;
    private String Postal;
    private String Phone;
    private String Division;
    private String Country;
    public int DivisionID;

    public Customer(int customerID, String name, String address, String postal, String phone, String division, String country, int divisionID) {
        CustomerID = customerID;
        Name = name;
        Address = address;
        Postal = postal;
        Phone = phone;
        Division = division;
        Country = country;
        DivisionID = divisionID;
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
     * @return the Name
     */
    public String getName() {
        return Name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        Name = name;
    }

    /**
     * @return the Address
     */
    public String getAddress() {
        return Address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        Address = address;
    }

    /**
     * @return the Postal
     */
    public String getPostal() {
        return Postal;
    }

    /**
     * @param postal
     */
    public void setPostal(String postal) {
        Postal = postal;
    }

    /**
     * @return the Phone
     */
    public String getPhone() {
        return Phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        Phone = phone;
    }

    /**
     * @return the Division
     */
    public String getDivision() {
        return Division;
    }

    /**
     * @param division
     */
    public void setDivision(String division) {
        Division = division;
    }

    /**
     * @return the Country
     */
    public String getCountry() {
        return Country;
    }

    /**
     * @param country
     */
    public void setCountry(String country) {
        Country = country;
    }

    /**
     * @return the Division ID
     */
    public int getDivisionID() {
        return DivisionID;
    }

    /**
     * @param divisionID
     */
    public void setDivisionID(int divisionID) {
        DivisionID = divisionID;
    }

    /**
     * Makes items in combo boxed legible
     * @return the Country
     */
    public String toString() {
        return (this.Country );
    }
}