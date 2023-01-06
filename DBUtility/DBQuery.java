package DBUtility;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Creates Prepared Statement to send messages to the SQL database.
 * Time Conversion methods used to covert UTC from database to Local time and vice versa.
 * @author Livan Martinez
 */
public class DBQuery {


    private static Statement ps;

    /**
     * @param connection
     * @throws SQLException
     */
    public static void setPreparedStatement(Connection connection) throws SQLException
    {
        ps = connection.createStatement();
    }

    /**
     * @return
     */
    public static Statement getStatement()
    {
        return ps;
    }

    /**
     * Converts time data from server to Local time and then converts to zoned date time UTC.
     * Then time is converted to zoned time of system default and finally converted into a Timestamp.
     * @param UTC
     * @return
     */
    public static Timestamp UTCtoLocal(Timestamp UTC){
        LocalDateTime ldtUTC = UTC.toLocalDateTime();
        ZonedDateTime zdtUTC = ldtUTC.atZone(ZoneId.of("UTC"));
        ZonedDateTime zdtLocal = zdtUTC.withZoneSameInstant(ZoneId.systemDefault());
        LocalDateTime ldtLocal = zdtLocal.toLocalDateTime();
        Timestamp localTime = Timestamp.valueOf(ldtLocal);
        return localTime;
    }

    /**
     * Converts time data from server to Local time and then converts to zoned date time of system default.
     * Then time is converted to zoned time of UTC, and finally converted into a Timestamp.
     * @param UTC
     * @return
     */
    public static Timestamp LocaltoUTC(Timestamp UTC){
        LocalDateTime ldtUTC = UTC.toLocalDateTime();
        ZonedDateTime zdtUTC = ldtUTC.atZone(ZoneId.systemDefault());
        ZonedDateTime zdtLocal = zdtUTC.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtLocal = zdtLocal.toLocalDateTime();
        Timestamp localTime = Timestamp.valueOf(ldtLocal);
        return localTime;
    }
}
