package SQL;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;

public class BaseDAO {

    public Connection getConnection() throws IOException, SQLException {

        Properties props = new Properties();
        props.load(new FileInputStream("config/database.properties"));

        String url = props.getProperty("dburl");
        String name = props.getProperty("user");
        String password = props.getProperty("password");

        Connection con = DriverManager.getConnection(url, name, password);
        System.out.println("\nConnection successfull");
        return con;
    }

    public void closeConnection(Connection con) throws SQLException {
        con.close();
    }

}