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
        //System.err.println("START");
        //long begin = System.currentTimeMillis();
        //zawiera wszystkie polaczenia
        ArrayList<Connection> allConnections = new ArrayList<>();
        //zawiera wszystkie trip id
        ArrayList tripsId;
        //zawiera trase dla danego przejazdu
        ArrayList route = new ArrayList();
        ArrayList routes = new ArrayList();
        routes = getTripStops();
        tripsId = getAllTripsIds();
        ArrayList<String> lineInfo = getAll();
        String input = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        for(int i=0;i<tripsId.size();i++) { //tripsId.size()
            //dodajemy wszystkie przystanki danej trasy
            for(int f=0; f<routes.size();f+=3) {
                if (routes.get(f).toString().equals(tripsId.get(i).toString())) {
                    route.add(routes.get(f + 1));
                    route.add(routes.get(f + 2));
                }
            }
            int e=0;
            while(!lineInfo.get(e).toString().equals(tripsId.get(i).toString())) {
                e += 3;
            }
            Boolean polnoc=false;
            for(int a=0;a<=(route.size()-4);a+=2) {
                String line = lineInfo.get(e+1);
                Boolean b = false;
                if(lineInfo.get(e+2).toString()=="Normalna tramwajowa") b=true;
                Transport boo;
                if(b)
                    boo = new Transport(TransportType.TRAM, line);
                else
                    boo = new Transport(TransportType.BUS, line);
                int idA = (int) route.get(a); // id of departure stop
                int idB = (int) route.get(a+2); // id of arrival stop
                if(Integer.parseInt(route.get(a+1).toString().substring(0,2))>Integer.parseInt(route.get(a+3).toString().substring(0,2))){
                    polnoc = true;
                }
                if(polnoc) {
                    if (route.get(a + 1).toString().substring(0, 2).equals("23")) {
                        input = "20.01.2017 ";
                    }
                    else {
                        input = "21.01.2017 ";
                    }
                }
                else {
                    input = "21.01.2017 ";
                }
                input += route.get(a+1).toString();
                LocalDateTime departure = LocalDateTime.parse(input, formatter);
                if(polnoc) {
                    if (route.get(a + 3).toString().substring(0, 2).equals("23")) {
                        input = "20.01.2017 ";
                    }
                    else {
                        input = "21.01.2017 ";
                    }
                }
                else {
                    input = "21.01.2017 ";
                }
                input += route.get(a+3).toString();
                LocalDateTime arrival = LocalDateTime.parse(input, formatter);
                int id = i*100+a; //connection id for updating
                int result = Integer.parseInt(tripsId.get(i).toString().substring(2));
                Connection foo = new Connection(0, idA, idB, departure, arrival, boo, result);
                allConnections.add(foo);
            }
            //if(i%1000==0)System.err.println(i+" "+(System.currentTimeMillis() - begin));
            route.clear();
        }
        //System.err.println("END "+(System.currentTimeMillis() - begin));
        return allConnections;
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

