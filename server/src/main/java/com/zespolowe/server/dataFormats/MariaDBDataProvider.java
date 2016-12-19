package com.zespolowe.server.dataFormats;

import com.zespolowe.server.interfaces.DataProvider;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Igor on 19.12.2016.
 */
public class MariaDBDataProvider implements DataProvider {

    java.sql.Connection connection = null;
    PreparedStatement preparedStatement = null;
    String selectStops = "SELECT * FROM jakDojade.stops";

    public MariaDBDataProvider() {
        try {
            connection = DriverManager.getConnection("jdbc:mariadb://194.181.240.69:3306/jakDojade?user=jakdojade&password=VpHW0V4KInndVWzj8wcgVAIY2H26wIs7");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public ArrayList<Point> getStops() {
        ArrayList<Point> points = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(selectStops);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Integer stop_id = rs.getInt("stop_id");
                String stop_name = rs.getString("stop_name");
                Double stop_lat = rs.getDouble("stop_lat");
                Double stop_lon = rs.getDouble("stop_lon");

                points.add(new Point(stop_id, "stop_name", new Coords(stop_lat,stop_lon)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return points;
    }

    @Override
    public ArrayList<Connection> getConnections() {
        return null;
    }

    @Override
    public ArrayList<Connection> getUpdatedConnections() {
        return null;
    }
}
