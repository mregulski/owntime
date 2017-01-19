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
        //zawiera wszystkie polaczenia
        ArrayList<Connection> allConnections = new ArrayList<>();
        //zawiera wszystkie trip id
        ArrayList tripsId;
        //zawiera trase dla danego przejazdu
        ArrayList route = new ArrayList();
        tripsId = getAllTripsIds();
        for(int i=0;i<1;i++) { //tripsId.size()
            //dodajemy wszystkie przystanki danej trasy
            route = getTripStopsByTripId(tripsId.get(i).toString());
            for(int a=0;a<route.size()/2-2;a++) {
                ArrayList<String> lineInfo = getLineNameAndTypeByTripId(tripsId.get(i).toString());
                String line = lineInfo.get(0);
                Boolean b = false;
                if(lineInfo.get(1).toString()=="Normalna tramwajowa") b=true;
                Transport boo;
                if(b)
                    boo = new Transport(TransportType.TRAM, line);
                else
                    boo = new Transport(TransportType.BUS, line);
                int idA = (int) route.get(2*a); // id of departure stop
                int idB = (int) route.get(2*a+2); // id of arrival stop
                String input = "20.01.2017 " + route.get(2*a+1).toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                LocalDateTime departure = LocalDateTime.parse(input, formatter);
                input = "20.01.2017 " + route.get(2*a+3).toString();
                formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                LocalDateTime arrival = LocalDateTime.parse(input, formatter);
                // wypisuje wszystkie dane
//                System.out.println(departure);
//                System.out.println(arrival);
//                System.out.println(line);
//                System.out.println(b);
//                System.out.println(idA);
//                System.out.println(idB);
//                System.out.println(boo.line);
//                System.out.println(boo.type);

                int id = i*100+a; //connection id for updating
                System.out.println(id);
                Connection foo = new Connection(id, idA, idB, departure, arrival, boo);
                allConnections.add(foo);
            }
        }

        return allConnections;
    }

    @Override
    public ArrayList<Connection> getUpdatedConnections() {
        return null;
    }

///////////////////////////////////////////////////////////////////////////////////////

    private ArrayList getLines(){
        ArrayList lines = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(getNames);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) lines.add(rs.getString(1)); //pobiera wszystkie numery linii
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private ArrayList getTripStopsByTripId(String tripID){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT stop_times.stop_id, stop_times.arrival_time FROM stop_times\n" +
                    "WHERE stop_times.trip_id = ?");
            ps.setString(1, tripID);
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
                stops.add(rs.getInt(1));
                stops.add(rs.getTime(2));
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

    public ArrayList getLineNameAndTypeByTripId(String trip_id) {
        ArrayList stops = new MariaDBDataProvider().getTripStopsByTripId(trip_id);

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT routes.route_short_name, route_types.route_type2_name FROM routes \n" +
                    "INNER JOIN trips ON trips.route_id = routes.route_id\n" +
                    "INNER JOIN stop_times ON trips.trip_id = stop_times.trip_id\n" +
                    "INNER JOIN route_types ON routes.route_type2_id = route_types.route_type2_id\n" +
                    "WHERE stop_times.stop_id = ?\n" +
                    "AND trips.trip_id = ?\n" +
                    "GROUP BY routes.route_short_name, route_types.route_type2_name");
            ps.setString(1, stops.get(0).toString());
            ps.setString(2, trip_id);
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

