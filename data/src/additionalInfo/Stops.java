package additionalInfo;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import SQL.BaseDAO;


public class Stops {

    /**
     * Pobiera dane z tabeli stops - (stop_id,stop_code,stop_name,stop_lat,stop_lon)
     *
     * @return ResultSet w/w //TODO
     * @throws IOException
     * @throws SQLException
     */
    public ResultSet getAllStops() throws IOException, SQLException {
        Connection conn = new BaseDAO().getConnection();
        Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery("SELECT * FROM stops");
        conn.close();
        return result;
    }

    /**
     * Gets id of a stop
     *
     * @param stopName
     * @return int stop_id
     * @throws IOException
     * @throws SQLException
     */
    public int getStopId(String stopName) throws IOException, SQLException {
        Connection conn = new BaseDAO().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT stop_id FROM stops WHERE stop_name = ?");
            ps.setString(1, stopName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet rs = ps.executeQuery();
        rs.next();
        conn.close();

        return rs.getInt(1);
    }

    /**
     * Gets name of a stop
     *
     * @param stopId
     * @return int stop_id
     * @throws IOException
     * @throws SQLException
     */
    public String getStopName(int stopId) throws IOException, SQLException {
        Connection conn = new BaseDAO().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT stop_name FROM stops WHERE stop_id = ?");
            ps.setInt(1, stopId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet rs = ps.executeQuery();
        rs.next();
        conn.close();

        return rs.getString(1);
    }


    /**
     * Gets all stops for specific trip
     *
     * @param tripID
     * @return String[]
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList getTripStopsByTripId(String tripID) throws SQLException, IOException {
        Connection conn = new BaseDAO().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT stop_times.stop_id, stop_times.arrival_time FROM stop_times\n" +
                    "INNER JOIN trips ON trips.trip_id = stop_times.trip_id \n" +
                    "INNER JOIN stops ON stops.stop_id = stop_times.stop_id\n" +
                    "WHERE stop_times.trip_id = ?");
            ps.setString(1, tripID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs = ps.executeQuery();
        ArrayList stops = new ArrayList();
        while(rs.next()) {
            stops.add(rs.getInt(1));
            stops.add(rs.getTime(2));
        }
        conn.close();

        return stops;
    }

    /**
     * Gets stop coordinates as 'stop_lat, stop_lon' by stop name
     *
     * @param stopName
     * @return String stop coordinates
     * @throws SQLException
     * @throws IOException
     */
    public String getStopCoordinatesByName(String stopName) throws SQLException, IOException {
        Connection conn = new BaseDAO().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT stop_lat, stop_lon FROM stops WHERE stop_name = ?");
            ps.setString(1, stopName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs = ps.executeQuery();
        rs.next();
        conn.close();

        return rs.getString(1) + ", " + rs.getString(2);
    }

    /**
     * Gets stop coordinates as 'stop_lat, stop_lon' by stop id
     *
     * @param stopID
     * @return String stop coordinates
     * @throws SQLException
     * @throws IOException
     */
    public String getStopCoordinatesById(int stopID) throws SQLException, IOException {
        Connection conn = new BaseDAO().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT stop_lat, stop_lon FROM stops WHERE stop_id = ?");
            ps.setInt(1, stopID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs = ps.executeQuery();
        rs.next();
        conn.close();

        return rs.getString(1) + ", " + rs.getString(2);
    }

}
