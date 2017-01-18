package additionalInfo;

import SQL.BaseDAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;


public class Routes {

    /**
     * Gets all lines
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList getAllRoutesShortNames() throws SQLException, IOException {
        Connection conn = new BaseDAO().getConnection();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT route_short_name FROM routes");

        ArrayList lines = new ArrayList<>();
        while(rs.next()) {
            lines.add(rs.getString(1));
        }
        conn.close();

        return lines;
    }

    /**
     * Gets line names for specific stop, i.e 33, 0L, 0P
     * @param stopId
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList getRouteShortNameByStopId(int stopId) throws SQLException, IOException {
        Connection conn = new BaseDAO().getConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT routes.route_short_name FROM routes \n" +
                    "INNER JOIN trips ON trips.route_id = routes.route_id\n" +
                    "INNER JOIN stop_times ON trips.trip_id = stop_times.trip_id\n" +
                    "WHERE stop_times.stop_id = ?\n" +
                    "GROUP BY routes.route_short_name");
            ps.setInt(1, stopId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs = ps.executeQuery();
        ArrayList lines = new ArrayList<>();
        while(rs.next()) {
            lines.add(rs.getString(1));
        }
        conn.close();

        return lines;
    }

    /**
     * Get line name and type for specific trip_id, i.e: 33, normalna tramwajowa
     * @return ArrayList ids
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList getLineNameAndTypeByTripId(String trip_id) throws SQLException, IOException {
        Connection conn = new BaseDAO().getConnection();
        ArrayList stops = new Stops().getTripStopsByTripId(trip_id);
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("SELECT routes.route_short_name, route_types.route_type2_name FROM routes \n" +
                    "INNER JOIN trips ON trips.route_id = routes.route_id\n" +
                    "INNER JOIN stop_times ON trips.trip_id = stop_times.trip_id\n" +
                    "INNER JOIN route_types ON routes.route_type2_id = route_types.route_type2_id\n" +
                    "WHERE stop_times.stop_id = ?\n +" +
                    "GROUP BY routes.route_short_name, route_types.route_type2_name");
            ps.setString(1, stops.get(1).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs = ps.executeQuery();

        ArrayList result = new ArrayList<>();
        while(rs.next()) {
            result.add(rs.getString(1));
            result.add(rs.getString(1));
        }
        conn.close();

        return result;
    }


}
