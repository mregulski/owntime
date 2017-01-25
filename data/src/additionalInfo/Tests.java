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
        Routes routes = new Routes();
        ResultSet result;

        try {
            ArrayList<Integer> lineNamesAndTypes = routes.getLineNameAndTypeByTripId("3_3685186");
            for (int i = 0; i < lineNamesAndTypes.size(); i=i+2) {
                System.out.println(lineNamesAndTypes.get(i));
                System.out.println(lineNamesAndTypes.get(i + 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
