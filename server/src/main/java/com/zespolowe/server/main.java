package com.zespolowe.server;

import com.zespolowe.server.PathFindingClasses.ConsoleRequestProvider;
import com.zespolowe.server.PathFindingClasses.PathFinder;
import com.zespolowe.server.dataFormats.JsonConnection;
import com.zespolowe.server.dataFormats.MariaDBDataProvider;
import com.zespolowe.server.dataFormats.PointService;
import com.zespolowe.server.dataFormats.DonaldTrumpRequestProvider;


public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException{

        //ReallyBadExampleOfFakeDataAndRequestProviderInOneClass noob = new ReallyBadExampleOfFakeDataAndRequestProviderInOneClass();
        ConsoleRequestProvider crp = new ConsoleRequestProvider();
        MariaDBDataProvider dataProvider = new MariaDBDataProvider();
        //DonaldTrumpRequestProvider requestProvider = new DonaldTrumpRequestProvider();
        PathFinder pf = new PathFinder();
        pf.setDataProvider(dataProvider);
        //pf.setRequestProvider(requestProvider);
        pf.setRequestProvider(crp);
//        pf.init();

        //pf.run();
        //pf.wait();

        MariaDBDataProvider provider = new MariaDBDataProvider();
        DonaldTrumpRequestProvider donald = new DonaldTrumpRequestProvider();
        PathFinder pathfinder = new PathFinder();

        JsonConnection jsonConnection = new JsonConnection(provider, donald, pathfinder);
        jsonConnection.configureRESTPointHandlers();

    }
    
}
