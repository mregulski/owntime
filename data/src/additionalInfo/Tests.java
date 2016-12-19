package additionalInfo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class to test results
 */
public class Tests {

    public static void main(String[] args) {
        Stops st = new Stops();
        ResultSet result;

        try {
//			result = st.getAllStops();
            ArrayList<Integer> stops = st.getTripStopsByTripId("3_4550497");
            for (int i = 0; i < stops.size(); i=i+2) {
                System.out.println(stops.get(i));
                System.out.println("Arrival " + stops.get(i+1));
                System.out.println(st.getStopCoordinatesById(stops.get(i)));
            }
            ArrayList test = new Routes().getRouteShortNameByStopId(2073);
            test = new Routes().getAllRoutesShortNames();
//			java.sql.ResultSetMetaData rsmd = result.getMetaData();
//			int columnsNumber = rsmd.getColumnCount();
//			for(int i = 1; i < columnsNumber; i++) {
//				System.out.print(rsmd.getColumnName(i) + ", ");
//			}
//			System.out.print(rsmd.getColumnName(columnsNumber));
//			System.out.println();
//			while (result.next()) {
//			    for (int i = 1; i <= columnsNumber; i++) {
//
//			        String columnValue = result.getString(i);
//			        System.out.print(columnValue);
//			        if (i != columnsNumber) System.out.print(",  ");
//			    }
//			    System.out.println();
//			}
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }
}
