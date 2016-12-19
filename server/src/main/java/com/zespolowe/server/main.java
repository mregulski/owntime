package com.zespolowe.server;

import com.zespolowe.server.PathFindingClasses.PathFinder;
import com.zespolowe.server.PathFindingClasses.ReallyBadExampleOfFakeDataAndRequestProviderInOneClass;
import com.zespolowe.server.dataFormats.JsonConnection;
import com.zespolowe.server.dataFormats.MariaDBDataProvider;
import com.zespolowe.server.dataFormats.PointService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){

        ReallyBadExampleOfFakeDataAndRequestProviderInOneClass noob = new ReallyBadExampleOfFakeDataAndRequestProviderInOneClass();
        //MariaDBDataProvider nooob = new MariaDBDataProvider();
        PathFinder pf = new PathFinder();
        pf.setDataProvider(noob);
        pf.setRequestProvider(noob);
        pf.init();
        pf.run();

        PointService pointService = new PointService();
        JsonConnection jsonConnection = new JsonConnection(pointService);
        jsonConnection.configureRESTPointHandlers();

//        Spark.get("/hello", (req, res) -> "Hello World");
    }
    
}
