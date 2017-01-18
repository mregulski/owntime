package additionalInfo;

import SQL.BaseDAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Trips {

    /**
     * Gets all ids for specific trip
     * @return ArrayList ids
     * @throws SQLException
     * @throws IOException
     */
    public ArrayList getAllTripsIds() throws SQLException, IOException {
        Connection conn = new BaseDAO().getConnection();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("SELECT trip_id FROM trips GROUP BY trip_id");

        ArrayList ids = new ArrayList<>();
        while(rs.next()) {
            ids.add(rs.getString(1));
        }
        conn.close();

        return ids;
    }


}
