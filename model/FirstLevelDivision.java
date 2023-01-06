package model;

public class FirstLevelDivision {

    private int DivisionID;
    private String DivName;
    public int CountryID;

    public FirstLevelDivision(int divisionID, String divName, int countryID) {
        this.DivisionID = divisionID;
        this.DivName = divName;
        this.CountryID = countryID;
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
     * @return the Div Name
     */
    public String getDivName() {
        return DivName;
    }

    /**
     * @param divName
     */
    public void setDivName(String divName) {
        DivName = divName;
    }

    /**
     * @return the Country ID
     */
    public int getCountryID() {
        return CountryID;
    }

    /**
     * @param countryID
     */
    public void setCountryID(int countryID) {
        CountryID = countryID;
    }

    /**
     * Makes items in combo boxes legible.
     * @return the Div Name
     */
    public String toString() {
        return (this.DivName);
    }
}
