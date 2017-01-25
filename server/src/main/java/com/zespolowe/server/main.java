package com.zespolowe.server;

import com.zespolowe.server.PathFindingClasses.PathFinder;
import com.zespolowe.server.dataFormats.JsonConnection;
import com.zespolowe.server.dataFormats.MariaDBDataProvider;
import com.zespolowe.server.dataFormats.PointService;
import com.zespolowe.server.dataFormats.DonaldTrumpRequestProvider;


public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){

        MariaDBDataProvider dataProvider = new MariaDBDataProvider();
        DonaldTrumpRequestProvider requestProvider = new DonaldTrumpRequestProvider();
        PathFinder pf = new PathFinder();
/*      pf.setDataProvider(dataProvider);
        pf.setRequestProvider(requestProvider);
        pf.init();

        pf.run();
*/
        JsonConnection jsonConnection = new JsonConnection(dataProvider, requestProvider, pf);
        jsonConnection.configureRESTPointHandlers();

    }
    
}
