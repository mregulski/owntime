package com.zespolowe.server.interfaces;


import com.zespolowe.server.dataFormats.Connection;
import com.zespolowe.server.dataFormats.Point;

import java.util.ArrayList;

/**
 *
 * @author Cirben
 */
public interface DataProvider {

    ArrayList<Point> getStops();

    ArrayList<Connection> getConnections();

    ArrayList<Connection> getUpdatedConnections();

}
