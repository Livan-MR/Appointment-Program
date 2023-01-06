package model;

/**
 * Model for Appointment Report
 * @author Livan Martinez
 */
public class AppointmentReport {

    private String ReportType;
    private int Count;
    private int MonthID;

    public AppointmentReport(String type, int count, int month) {
        this.ReportType = type;
        this.Count = count;
        this.MonthID = month;
    }

    /**
     * @return the Report Type
     */
    public String getType() {
        return ReportType;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        ReportType = type;
    }

    /**
     * @return the Count
     */
    public int getCount() {
        return Count;
    }

    /**
     * @param count
     */
    public void setCount(int count) {
        Count = count;
    }

    /**
     * @return the Month ID
     */
    public int getMonth() {
        return MonthID;
    }

    /**
     * @param month
     */
    public void setMonth(int month) {
        MonthID = month;
    }
}
