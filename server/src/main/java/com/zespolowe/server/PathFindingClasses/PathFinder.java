package com.zespolowe.server.PathFindingClasses;

import com.zespolowe.server.dataFormats.Connection;
import com.zespolowe.server.dataFormats.Path;
import com.zespolowe.server.dataFormats.Request;
import com.zespolowe.server.interfaces.DataProvider;
import com.zespolowe.server.interfaces.RequestProvider;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cirben
 */
public class PathFinder implements Runnable {

    DataProvider dp;
    RequestProvider rp;
    CommunicationMap graph;

    public void setDataProvider(DataProvider dp) {
        this.dp = dp;
    }

    public void setRequestProvider(RequestProvider rp) {
        this.rp = rp;
    }

    public void init() {
		try {
			graph = new CommunicationMap(dp.getStops(), dp.getConnections());
		} catch (MyException ex) {
			Logger.getLogger(PathFinder.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

    @Override
    public void run() {
        while (!rp.shouldStop()) {
            ArrayList<Connection> updatedConnections = dp.getUpdatedConnections();
            if (updatedConnections != null) {
                graph.update(updatedConnections);
            }
            for (int i = 0; i < 3; i++) { 
                Request r = rp.getRequest();
                Path path;
                try {
                    path = graph.findPath(r, 0);
                    System.out.println("request id: " + r.getRequestId());
                    path.print();
                    rp.addPath(path);
                } catch (MyException ex) {
                    System.out.println("EXCEPTION: " + ex.getMessage());
                }
            }
        }
    }
}
