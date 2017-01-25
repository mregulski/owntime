package com.zespolowe.server.dataFormats;

import com.zespolowe.server.interfaces.DataProvider;

import com.zespolowe.server.interfaces.DataProvider;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

import static jdk.nashorn.internal.objects.Global.println;

/**
 * Created by Igor on 19.12.2016.
 */
public class MariaDBDataProvider implements DataProvider {

    java.sql.Connection connection = null;
    PreparedStatement preparedStatement = null;
    String selectStops = "SELECT * FROM jakDojade.stops";
    String getNames = "SELECT route_id FROM routes";


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
        ArrayList<Connection> result = new ArrayList<>();
        ArrayList route = new ArrayList();
        ArrayList routes = getTripStops();
        ArrayList<String> lineInfo = getAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        int f = 0;
        for(int i=0;i<lineInfo.size();i+=3) {
            for (; f < routes.size(); f += 3) {
                if (routes.get(f).toString().equals(lineInfo.get(i).toString())) {
                    route.add(routes.get(f + 1));
                    route.add(routes.get(f + 2));
                }
                else break;
            }
            if (lineInfo.get(i).toString().charAt(0)!='6'){
                route.clear();
                continue;
            }

            LocalDateTime lastArrival = null;
            String line = lineInfo.get(i + 1);
            Transport transportType;
            if (lineInfo.get(i + 2).toString() == "Normalna tramwajowa")
                transportType = new Transport(TransportType.TRAM, line);
            else
                transportType = new Transport(TransportType.BUS, line);
            for (int a = 0; a < route.size(); a += 2) {
                String input = "26.01.2017 "+route.get(a + 1).toString();
                LocalDateTime arrival = LocalDateTime.parse(input, formatter);
                if(a==0) {
                    lastArrival=arrival;
                    continue;
                }
                if (lastArrival.compareTo(arrival) > 0) {
                    arrival=arrival.plusDays(1);
                    //System.err.println(i + " " + lineInfo.get(i).toString() + " " + lastArrival + " " + arrival + " " + route.get(a - 1) + " " + route.get(a + 1));
                }
                int id = i * 100 + a; //connection id for updating
                result.add(new Connection(id, (int) route.get(a-2), (int) route.get(a), lastArrival, arrival, transportType,
                        Integer.parseInt(lineInfo.get(i).toString().substring(2))));
                lastArrival=arrival;
            }
            route.clear();
        }
        return result;
    }

    @Override
    public ArrayList<Connection> getUpdatedConnections() {
        return null;
    }

///////////////////////////////////////////////////////////////////////////////////////

    private ArrayList getTripStops(){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT stop_times.trip_id, stop_times.stop_id, stop_times.arrival_time FROM stop_times");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList stops = new ArrayList<>();
        try {
            while(rs.next()) {
                stops.add(rs.getString(1));
                stops.add(rs.getInt(2));
                stops.add(rs.getTime(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stops;
    }

    private ArrayList getAllTripsIds() {
        try {
            preparedStatement = connection.prepareStatement("SELECT trip_id FROM trips GROUP BY trip_id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList ids = new ArrayList<>();
        try {
            while(rs.next()) {
                ids.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public ArrayList getAll() {

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT trip_type.trip_id, trip_type.route_short_name, trip_type.route_type2_name FROM trip_type");
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList result = new ArrayList();
        try {
            while(rs.next()) {
                result.add(rs.getString(1));
                result.add(rs.getString(2));
                result.add(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

