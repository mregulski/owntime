/**
 * 
 */
package additionalInfo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import SQL.BaseDAO;

/**
 * @author Paul
 *
 */
public class Stops {
	/**
	 * Pobiera dane z tabeli stops - (stop_id,stop_code,stop_name,stop_lat,stop_lon)
	 * @return ResultSet w/w //TODO
	 * @throws IOException
	 * @throws SQLException
	 */
	public ResultSet getAllStops() throws IOException, SQLException{
		Connection conn = new BaseDAO().getConnection();
		//Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/jakDojade", "root", "pass");
        Statement stat = conn.createStatement();
        ResultSet result = stat.executeQuery("SELECT * FROM stops");
        conn.close();
        return result;
	}
	
	/*public static void main(String[] args) {
		Stops st = new Stops();
		ResultSet result;
		try {
			result = st.getAllStops();
			java.sql.ResultSetMetaData rsmd = result.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			for(int i = 1; i < columnsNumber; i++) {
				System.out.print(rsmd.getColumnName(i) + ", ");
			}
			System.out.print(rsmd.getColumnName(columnsNumber));
			System.out.println();
			while (result.next()) {
			    for (int i = 1; i <= columnsNumber; i++) {
			        
			        String columnValue = result.getString(i);
			        System.out.print(columnValue);
			        if (i != columnsNumber) System.out.print(",  ");
			    }
			    System.out.println();
			}
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		
	}*/

}
